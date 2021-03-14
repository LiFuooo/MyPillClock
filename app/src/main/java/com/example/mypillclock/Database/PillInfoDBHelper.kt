package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Utilities.DateTimeFormatConverter
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PillInfoDBHelper {

    object DBExposedPillsTable : IntIdTable() {
        val name: Column<String> = varchar("name", 50)
        var quantity: Column<Int> = integer("quantity")
        var isRepetitive:Column<Boolean> = bool("isRepetitve")

        val frequency: Column<Int> = integer("frequency")
        val amount: Column<Int> = integer("amount")
        val amountType: Column<String> = varchar("amountType", 50)
        val remindStartDate:Column<String> = varchar("remindStartDate", 20)
        val remindTime: Column<String> = varchar("remindTime", 50)
        val rxNumber: Column<String> = varchar("Rx_Number", 50)
        val doctorNote: Column<String> = varchar("doctorNote", 200)
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DBExposedPillEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DBExposedPillEntity>(DBExposedPillsTable)
        var name by DBExposedPillsTable.name
        var quantity by DBExposedPillsTable.quantity
        var isRepetitive by DBExposedPillsTable.isRepetitive
        var frequency by DBExposedPillsTable.frequency
        var amount by DBExposedPillsTable.amount
        var amountType by DBExposedPillsTable.amountType
        var remindStartDate by DBExposedPillsTable.remindStartDate
        var remindTime by DBExposedPillsTable.remindTime
        var rxNumber by DBExposedPillsTable.rxNumber
        var doctorNote by DBExposedPillsTable.doctorNote
    }

    fun addPill(pill: PillInfo) {

        val pillInfoString = Json.encodeToString(PillInfo.serializer(), pill)
        Log.i("insideAddPillFun", "pillInfoString = $pillInfoString")
        val pillInfoJson = Json.decodeFromString(PillInfo.serializer(), pillInfoString)
        Log.i("insideAddPillFun", "pillInfoJson = $pillInfoJson")


        transaction {
            DBExposedPillEntity.new {
                name = pillInfoJson.name
                quantity = pillInfoJson.quantity
                isRepetitive = pillInfoJson.isRepetitive
                frequency = pillInfoJson.frequency
                amount = pillInfoJson.amount
                amountType = pillInfoJson.amountType
                remindStartDate = pillInfoJson.remindStartDate
                remindTime = pillInfoJson.RemindTime
                rxNumber = pillInfoJson.rxNumber
                doctorNote = pillInfoJson.doctorNote
            }

        }
    }



    // output data type is MutableList<PillInfo>
    fun getPillListFromDB():MutableList<PillInfo> {
        return  transaction {
            val query = DBExposedPillEntity.all()
            query.map { dbExposedPillInstance: DBExposedPillEntity ->
                PillInfo(
                    dbExposedPillInstance.id.value,
                    dbExposedPillInstance.name,
                    dbExposedPillInstance.quantity,
                    dbExposedPillInstance.isRepetitive,
                    dbExposedPillInstance.frequency,
                    dbExposedPillInstance.amount,
                    dbExposedPillInstance.amountType,
                    dbExposedPillInstance.remindStartDate,
                    dbExposedPillInstance.remindTime,
                    dbExposedPillInstance.rxNumber,
                    dbExposedPillInstance.doctorNote
                )
            }.toMutableList()
        }
    }



    fun deletePill(pillInfo: PillInfo) {

        transaction {
            DBExposedPillsTable.deleteWhere {
                DBExposedPillsTable.id eq pillInfo.id
            }
        }

    }


    fun updatePill(pillAfterEdit: PillInfo) {

        val afterEditPillInfoString = Json.encodeToString(PillInfo.serializer(), pillAfterEdit)
        val afterEditPillInfoJson = Json.decodeFromString(PillInfo.serializer(), afterEditPillInfoString)
        val pillToBeUpdated = queryOnePillById(pillAfterEdit.id) ?: error("The id does not exists")

        transaction {
            pillToBeUpdated.name = afterEditPillInfoJson.name
            pillToBeUpdated.quantity = afterEditPillInfoJson.quantity
            pillToBeUpdated.frequency = afterEditPillInfoJson.frequency
            pillToBeUpdated.amount = afterEditPillInfoJson.amount
            pillToBeUpdated.amountType = afterEditPillInfoJson.amountType
            pillToBeUpdated.remindStartDate = afterEditPillInfoJson.remindStartDate
            pillToBeUpdated.remindTime = afterEditPillInfoJson.RemindTime
            pillToBeUpdated.rxNumber = afterEditPillInfoJson.rxNumber
            pillToBeUpdated.doctorNote = afterEditPillInfoJson.doctorNote
        }
    }

    fun queryOnePillById(id: Int): DBExposedPillEntity? {
        return transaction {
            DBExposedPillEntity.findById(id)
        }
    }

    fun queryOnePillEntityByDataClass(pill: PillInfo): DBExposedPillEntity? {
        return transaction {
        DBExposedPillEntity.find { DBExposedPillsTable.name eq pill.name }.firstOrNull()
        }


    }


    fun addSampleDataToDB(){
        val now = DateTimeFormatConverter().now
        val date = DateTimeFormatConverter().timeLongToDateString(now)!!
        val time = DateTimeFormatConverter().timeLongToTimeString(now)!!
        for (i in 1..3){
            addPill(PillInfo(
                1,
                "name_$i",
                8 * i,
                true,
                30 + i,
                i+1,
                "pills",
                date,
                time,
                "123-098x",
                "No Food"))
        }
    }
}