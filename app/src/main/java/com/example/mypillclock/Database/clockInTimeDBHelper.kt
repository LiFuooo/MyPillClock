package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.ClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class clockInTimeDBHelper {

    object clockInTimeTable : IntIdTable() {
        val name: Column<String> = varchar("name", 50)
        var clockInTime: Column<String> = varchar("clockInTime", 50)
        var count: Column<Int> = integer("clockInCount")


        override val primaryKey = PrimaryKey(id, name = "clock In Time")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class clockInTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<clockInTimeEntity>(clockInTimeTable)
        var name by clockInTimeTable.name
        var clockInTime by clockInTimeTable.clockInTime
        var count by clockInTimeTable.count


    }

    fun addClockInTime(clockInData: ClockInDataClass) {

        val clockInInfoString = Json.encodeToString(ClockInDataClass.serializer(), clockInData)
        Log.e("clockInInfoString", "clockInInfoString = $clockInInfoString")
        val clockInInfoJson = Json.decodeFromString(ClockInDataClass.serializer(), clockInInfoString)
        Log.e("clockInInfoJson", "clockInInfoJson = $clockInInfoJson")


        transaction {
            clockInTimeEntity.new {
                name = clockInInfoJson.name
                clockInTime = clockInInfoJson.timeClockIn
                count = clockInInfoJson.count + 1
            }

        }
    }



    // output data type is MutableList<PillInfo>
    fun getClockInListFromDB():MutableList<ClockInDataClass> {
        return  transaction {
            val query = clockInTimeEntity.all()
            query.map { clockInTimeInstance: clockInTimeEntity ->
                ClockInDataClass(
                    clockInTimeInstance.id.value,
                    clockInTimeInstance.name,
                    clockInTimeInstance.clockInTime,
                    clockInTimeInstance.count,
                )
            }.toMutableList()
        }
    }



    fun deleteClockInRecord(ClockInData: ClockInDataClass) {

        transaction {
            clockInTimeTable.deleteWhere {
                clockInTimeTable.id eq ClockInData.id
            }
        }

    }


    fun updateClockInRecord(updatedClockInTimeData: ClockInDataClass) {

        val updatedClockInTimeString = Json.encodeToString(ClockInDataClass.serializer(), updatedClockInTimeData)
        val updatedClockInTimeJson = Json.decodeFromString(ClockInDataClass.serializer(), updatedClockInTimeString)
        val clockInRecordTobeUpdated = queryOnePill(updatedClockInTimeData.id) ?: error("The id does not exists")

        transaction {
            clockInRecordTobeUpdated.name = updatedClockInTimeJson.name
            clockInRecordTobeUpdated.clockInTime = updatedClockInTimeJson.timeClockIn
            clockInRecordTobeUpdated.count = updatedClockInTimeJson.count

        }
    }

    private fun queryOnePill(id: Int): clockInTimeEntity? {
        return transaction {
            clockInTimeEntity.findById(id)
        }
    }


}