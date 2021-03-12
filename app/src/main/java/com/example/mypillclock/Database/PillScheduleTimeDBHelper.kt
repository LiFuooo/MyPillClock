package com.example.mypillclock.Database

import com.example.mypillclock.DataClass.DiaryClockInDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.PillScheduleTimeDataClass
import com.example.mypillclock.Utilities.DataClassEntityConverter
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat

class PillScheduleTimeDBHelper {

    object pillScheduleTimeTable : IntIdTable() {
        val name = reference("name", PillInfoDBHelper.DBExposedPillsTable)
        val scheduleTime: Column<Long> = long("scheduleTime")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class pillScheduleTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<pillScheduleTimeEntity>(pillScheduleTimeTable)

        var name by PillInfoDBHelper.DBExposedPillEntity referencedOn pillScheduleTimeTable.name
        var scheduleTime by pillScheduleTimeTable.scheduleTime
    }

    fun addOneRecordWithScheduleTime(scheduleTimeIn:Long, pillInfoEntity: PillInfoDBHelper.DBExposedPillEntity){

        transaction {
            PillScheduleTimeDBHelper.pillScheduleTimeEntity.new{
                name = pillInfoEntity
                scheduleTime = scheduleTimeIn
            }
        }
    }

    fun getAllScheduleListFromDB():MutableList<PillScheduleTimeDataClass> {
        return  transaction {
            val query = pillScheduleTimeEntity.all()
            query.map { pillScheduleTimeInstance: pillScheduleTimeEntity ->
                PillScheduleTimeDataClass(
                    pillScheduleTimeInstance.id.value,
                    DataClassEntityConverter().pillInfoEntityToDataClass(pillScheduleTimeInstance.name),
                    pillScheduleTimeInstance.scheduleTime
                )
            }.toMutableList()
        }
    }





    fun findScheduleListByPillId(pillID:Int): MutableList<PillScheduleTimeDataClass> {
        val allRecords = getAllScheduleListFromDB()

        return allRecords.filter {
            it.name.id == pillID
        }.toMutableList()
    }

}