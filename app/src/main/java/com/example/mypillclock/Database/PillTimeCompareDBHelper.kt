package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.PillClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Utilities.DataClassEntityConverter
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.text.SimpleDateFormat

class PillTimeCompareDBHelper {
    object pillTimeCompareTable : IntIdTable() {
        val pillInfo = reference("name", PillInfoDBHelper.DBExposedPillsTable)
        val scheduleTime = reference("scheduleTime", PillScheduleTimeDBHelper.pillScheduleTimeTable)
        val clockInTime = long("clockInTime").nullable()
        var isClockIn:Column<Boolean> = bool("isClockIn")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class pillTimeCompareEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<pillTimeCompareEntity>(pillTimeCompareTable)
        var pillInfo by PillInfoDBHelper.DBExposedPillEntity referencedOn pillTimeCompareTable.pillInfo
        var scheduleTime by PillScheduleTimeDBHelper.pillScheduleTimeEntity referencedOn pillTimeCompareTable.scheduleTime
        var clockInTime by pillTimeCompareTable.clockInTime
        var isClockIn by pillTimeCompareTable.isClockIn
    }

    fun addScheduleRecord(scheduleTimeIn:PillScheduleTimeDBHelper.pillScheduleTimeEntity){
        transaction {
            pillTimeCompareEntity.new{
                pillInfo = scheduleTimeIn.name
                scheduleTime = scheduleTimeIn
                clockInTime = null
                isClockIn = false
            }
        }
    }

    fun addClockInTime(pill:PillInfo, time:Long){
        val findPillScheduleDataClass = PillScheduleTimeDBHelper().getAllScheduleListFromDB().filter {
            it.name == pill
        }.firstOrNull()

        if(findPillScheduleDataClass != null){
        val pillScheduleDataClassToEntity = DataClassEntityConverter().pillScheduleDataClassToEntity(findPillScheduleDataClass)
            val PillTimeCompareTableID = PillScheduleTimeDBHelper.pillScheduleTimeTable


            transaction {
                pillTimeCompareTable.update ({ pillTimeCompareTable.id eq PillTimeCompareTableID} ) {
                    it[clockInTime] = time
                }
                }
            }

        }


    }



//    fun addOneRecordWithScheduleTime(scheduleTimeIn:Long, pillInfo: PillInfo){
//
//        transaction {
//
//            PillTimeCompareDBHelper.pillTimeCompareEntity.new{
//                name = DataClassEntityConverter().pillInfoDataClassToEntity(pillInfo)
//                clockInTime = PillClockInDBHelper.ClockInTimeEntity[1]
//                scheduleTime = scheduleTimeIn
//                isClockIn = false
//            }
//        }
//    }



//    fun addClockInRecord(pillClockInData: PillClockInDataClass) {
//        val pillClockInString =
//            Json.encodeToString(PillClockInDataClass.serializer(), pillClockInData)
//        val pillClockInJson =
//            Json.decodeFromString(PillClockInDataClass.serializer(), pillClockInString)
//
//        transaction {
//            Log.i("PillInfo Entity count", "${PillInfoDBHelper.DBExposedPillEntity.count()}")
//            Log.i("pillClock.pillName.id", "${pillClockInData.pillName.id}")
//            val pillInfoEntity = PillInfoDBHelper.DBExposedPillEntity[pillClockInData.pillName.id]
//            Log.i("pillInfoEntity name", "${pillInfoEntity.name}")
//            pillTimeCompareEntity.new {
//                name = pillInfoEntity
//                category = "Medicine"
//                clockInTime = pillClockInData.timeClockIn
//            }
//        }
//
//    }


//    // output data type is MutableList<PillInfo>
//    fun getAllClockInListFromDB(): MutableList<PillClockInDataClass> {
//        return transaction {
//            val query = ClockInTimeEntity.all()
//            query.map { clockInTimeInstance: ClockInTimeEntity ->
//                PillClockInDataClass(
//                    clockInTimeInstance.id.value,
//                    PillInfo(
//                        clockInTimeInstance.name.id.value,
//                        clockInTimeInstance.name.name,
//                        clockInTimeInstance.name.duration,
//                        clockInTimeInstance.name.frequency,
//                        clockInTimeInstance.name.amount,
//                        clockInTimeInstance.name.amountType,
//                        clockInTimeInstance.name.remindStartDate,
//                        clockInTimeInstance.name.remindTime,
//                        clockInTimeInstance.name.rxNumber,
//                        clockInTimeInstance.name.doctorNote
//                    ),
//                    clockInTimeInstance.category,
//                    clockInTimeInstance.clockInTime,
//                )
//            }.toMutableList()
//        }
//    }
//
//
//    fun deleteClockInRecord(pillClockInData: PillClockInDataClass) {
//
//        transaction {
//            clockInTimeTable.deleteWhere {
//                clockInTimeTable.id eq pillClockInData.id
//            }
//        }
//
//    }


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

//    private fun queryOnePill(id: Int): ClockInTimeEntity? {
//        return transaction {
//            ClockInTimeEntity.findById(id)
//        }
//    }
//
//    fun findRecordsByDate(dateToMatch: String): MutableList<PillClockInDataClass> {
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        val allRecords = getAllClockInListFromDB()
//
//        return allRecords.filter {
//            sdf.format(it.timeClockIn) == dateToMatch
//        }.toMutableList()
//    }
//
//    fun recordsCount(): Int {
//        return getAllClockInListFromDB().size
//    }


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

//    fun deleteAllRecords() {
//        transaction {
//            clockInTimeTable.deleteAll()
//        }
//    }

//}

