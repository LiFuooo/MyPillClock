package com.example.mypillclock.Database

import android.util.Log
import android.widget.Toast
import com.example.mypillclock.DataClass.pillClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat

class PillClockInDBHelper {

    object clockInTimeTable : IntIdTable() {
        val name: Column<String> = varchar("name", 50)
        val category: Column<String> = varchar("category", 50)
        var clockInTime: Column<Long> = long("clockInTime")
        var count: Column<Int> = integer("clockInCount")


        override val primaryKey = PrimaryKey(id, name = "clock In Time")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class ClockInTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<ClockInTimeEntity>(clockInTimeTable)
        var name by clockInTimeTable.name
        var category by clockInTimeTable.category
        var clockInTime by clockInTimeTable.clockInTime
        var count by clockInTimeTable.count


    }

    fun addClockInRecord(pillInfo: PillInfo, currentTime: Long) {

        transaction {
            ClockInTimeEntity.new {
                name = pillInfo.name
                clockInTime = currentTime
                category = "Medicine"
                count = 1
            }
        }

    }



    // output data type is MutableList<PillInfo>
    fun getAllClockInListFromDB():MutableList<pillClockInDataClass> {
        return  transaction {
            val query = ClockInTimeEntity.all()
            query.map { clockInTimeInstance: ClockInTimeEntity ->
                pillClockInDataClass(
                    clockInTimeInstance.id.value,
                    clockInTimeInstance.name,
                    clockInTimeInstance.category,
                    clockInTimeInstance.clockInTime,
                    clockInTimeInstance.count
                )
            }.toMutableList()
        }
    }



    fun deleteClockInRecord(pillClockInData: pillClockInDataClass) {

        transaction {
            clockInTimeTable.deleteWhere {
                clockInTimeTable.id eq pillClockInData.id
            }
        }

    }


    fun updateClockInRecord(updatedClockInTimeData: pillClockInDataClass) {

        val updatedClockInTimeString = Json.encodeToString(pillClockInDataClass.serializer(), updatedClockInTimeData)
        val updatedClockInTimeJson = Json.decodeFromString(pillClockInDataClass.serializer(), updatedClockInTimeString)
        val clockInRecordTobeUpdated = queryOnePill(updatedClockInTimeData.id) ?: error("The id does not exists")

        transaction {
            clockInRecordTobeUpdated.name = updatedClockInTimeJson.name
            clockInRecordTobeUpdated.clockInTime = updatedClockInTimeJson.timeClockIn
            clockInRecordTobeUpdated.count = updatedClockInTimeJson.count

        }
    }

    private fun queryOnePill(id: Int): ClockInTimeEntity? {
        return transaction {
            ClockInTimeEntity.findById(id)
        }
    }

    fun findRecordsByDate(dateToMatch:String): MutableList<pillClockInDataClass> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val allRecords = getAllClockInListFromDB()

        return allRecords.filter {
            sdf.format(it.timeClockIn) == dateToMatch
        }.toMutableList()
    }

    fun recordsCount(): Int {
        return getAllClockInListFromDB().size
    }



    fun addFakeDataToDB(){
        val now = System.currentTimeMillis()
        val tomorrow = now + 1000*24*60*60
        val yesterday = now - 1000*24*60*60
        val olderDate = now - 1000*24*60*60*7

        if(recordsCount() == 0){
            transaction {
                ClockInTimeEntity.new {
                    name = "pill_1_record"
                    clockInTime = olderDate
                    category = "Medicine"
                    count = 1
                }

                ClockInTimeEntity.new {
                    name = "pill_2_record"
                    clockInTime = yesterday
                    category = "Medicine"
                    count = 1
                }

                ClockInTimeEntity.new {
                    name = "pill_3_record"
                    clockInTime = now
                    category = "Medicine"
                    count = 1
                }

                ClockInTimeEntity.new {
                    name = "pill_4_record"
                    clockInTime = tomorrow
                    category = "Medicine"
                    count = 1
                }
            }
        }
    }

        fun deleteAllRecords(){
            transaction {
                clockInTimeTable.deleteAll()
            }
        }







}