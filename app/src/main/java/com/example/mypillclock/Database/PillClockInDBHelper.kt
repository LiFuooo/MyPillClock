package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.PillClockInDataClass
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
        val name = reference("name", PillInfoDBHelper.DBExposedPillsTable)
        val category: Column<String> = varchar("category", 50)
        var clockInTime: Column<Long> = long("clockInTime")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class ClockInTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<ClockInTimeEntity>(clockInTimeTable)
        var name by PillInfoDBHelper.DBExposedPillEntity referencedOn clockInTimeTable.name
        var category by clockInTimeTable.category
        var clockInTime by clockInTimeTable.clockInTime


    }

    fun addClockInRecord(pillClockInData: PillClockInDataClass) {
        val pillClockInString = Json.encodeToString(PillClockInDataClass.serializer(), pillClockInData)
        val pillClockInJson = Json.decodeFromString(PillClockInDataClass.serializer(), pillClockInString)

        transaction {
            Log.i("PillInfo Entity count", "${PillInfoDBHelper.DBExposedPillEntity.count()}")
            Log.i("pillClock.pillName.id", "${pillClockInData.pillName.id}")
            val pillInfoEntity = PillInfoDBHelper.DBExposedPillEntity[pillClockInData.pillName.id]
            Log.i("pillInfoEntity name", "${pillInfoEntity.name}")
            ClockInTimeEntity.new {
                name = pillInfoEntity
                category = "Medicine"
                clockInTime = pillClockInData.timeClockIn
            }
        }

    }



    // output data type is MutableList<PillInfo>
    fun getAllClockInListFromDB():MutableList<PillClockInDataClass> {
        return  transaction {
            val query = ClockInTimeEntity.all()
            query.map { clockInTimeInstance: ClockInTimeEntity ->
                PillClockInDataClass(
                    clockInTimeInstance.id.value,
                    PillInfo(
                        clockInTimeInstance.name.id.value,
                        clockInTimeInstance.name.name,
                    clockInTimeInstance.name.duration,
                        clockInTimeInstance.name.frequency,
                        clockInTimeInstance.name.amount,
                        clockInTimeInstance.name.amountType,
                        clockInTimeInstance.name.remindStartDate,
                        clockInTimeInstance.name.remindTime,
                        clockInTimeInstance.name.rxNumber,
                        clockInTimeInstance.name.doctorNote),
                    clockInTimeInstance.category,
                    clockInTimeInstance.clockInTime,
                )
            }.toMutableList()
        }
    }



    fun deleteClockInRecord(pillClockInData: PillClockInDataClass) {

        transaction {
            clockInTimeTable.deleteWhere {
                clockInTimeTable.id eq pillClockInData.id
            }
        }

    }


//    fun updateClockInRecord(updatedClockInTimeData: PillClockInDataClass) {
//
//        val updatedClockInTimeString = Json.encodeToString(PillClockInDataClass.serializer(), updatedClockInTimeData)
//        val updatedClockInTimeJson = Json.decodeFromString(PillClockInDataClass.serializer(), updatedClockInTimeString)
//        val clockInRecordTobeUpdated = queryOnePill(updatedClockInTimeData.id) ?: error("The id does not exists")
//
//        transaction {
//            clockInRecordTobeUpdated.name = updatedClockInTimeJson.pillName
//            clockInRecordTobeUpdated.clockInTime = updatedClockInTimeJson.timeClockIn
//
//        }
//    }

    private fun queryOnePill(id: Int): ClockInTimeEntity? {
        return transaction {
            ClockInTimeEntity.findById(id)
        }
    }

    fun findRecordsByDate(dateToMatch:String): MutableList<PillClockInDataClass> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val allRecords = getAllClockInListFromDB()

        return allRecords.filter {
            sdf.format(it.timeClockIn) == dateToMatch
        }.toMutableList()
    }

    fun recordsCount(): Int {
        return getAllClockInListFromDB().size
    }



//    fun addFakeDataToDB(){
//        val now = System.currentTimeMillis()
//        val tomorrow = now + 1000*24*60*60
//        val yesterday = now - 1000*24*60*60
//        val olderDate = now - 1000*24*60*60*7
//
//        if(recordsCount() == 0){
//            transaction {
//                ClockInTimeEntity.new {
//                    name = "pill_1_record"
//                    category = "Medicine"
//                    clockInTime = olderDate
//                }
//
//                ClockInTimeEntity.new {
//                    name = "pill_2_record"
//                    category = "Medicine"
//                    clockInTime = yesterday
//                }
//
//                ClockInTimeEntity.new {
//                    name = "pill_3_record"
//                    category = "Medicine"
//                    clockInTime = now
//                }
//
//                ClockInTimeEntity.new {
//                    name = "pill_4_record"
//                    category = "Medicine"
//                    clockInTime = tomorrow
//                }
//            }
//        }
//    }

        fun deleteAllRecords(){
            transaction {
                clockInTimeTable.deleteAll()
            }
        }







}