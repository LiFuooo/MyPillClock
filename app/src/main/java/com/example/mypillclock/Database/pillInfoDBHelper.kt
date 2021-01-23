package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class pillInfoDBHelper {

    object DBExposedPillsTable : IntIdTable() {
        val name: Column<String> = varchar("name", 50)
        var duration: Column<Int> = integer("duration")
        val frequency: Column<Int> = integer("frequency")
        val amount: Column<Int> = integer("amount")
        val amountType: Column<String> = varchar("amountType", 50)
        val remindStartDate:Column<String> = varchar("remindStartDate", 20)
        val remindTime: Column<String> = varchar("remindTime", 50)
        val rxNumber: Column<String> = varchar("Rx_Number", 50)
        val doctorNote: Column<String> = varchar("doctorNote", 200)

        override val primaryKey = PrimaryKey(id, name = "PillSaved")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DBExposedPillEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DBExposedPillEntity>(DBExposedPillsTable)
        var name by DBExposedPillsTable.name
        var duration by DBExposedPillsTable.duration
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
        Log.e("insideAddPillFun", "pillInfoString = $pillInfoString")
        val pillInfoJson = Json.decodeFromString(PillInfo.serializer(), pillInfoString)
        Log.e("insideAddPillFun", "pillInfoJson = $pillInfoJson")


        transaction {
            DBExposedPillEntity.new {
                name = pillInfoJson.name
                duration = pillInfoJson.duration
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
                    dbExposedPillInstance.duration,
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
        val pillToBeUpdated = queryOnePill(pillAfterEdit.id) ?: error("The id does not exists")

        transaction {
            pillToBeUpdated.name = afterEditPillInfoJson.name
            pillToBeUpdated.duration = afterEditPillInfoJson.duration
            pillToBeUpdated.frequency = afterEditPillInfoJson.frequency
            pillToBeUpdated.amount = afterEditPillInfoJson.amount
            pillToBeUpdated.amountType = afterEditPillInfoJson.amountType
            pillToBeUpdated.remindStartDate = afterEditPillInfoJson.remindStartDate
            pillToBeUpdated.remindTime = afterEditPillInfoJson.RemindTime
            pillToBeUpdated.rxNumber = afterEditPillInfoJson.rxNumber
            pillToBeUpdated.doctorNote = afterEditPillInfoJson.doctorNote
        }
    }

    private fun queryOnePill(id: Int): DBExposedPillEntity? {
        return transaction {
            DBExposedPillEntity.findById(id)
        }
    }
}