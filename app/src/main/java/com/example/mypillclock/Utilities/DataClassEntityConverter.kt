package com.example.mypillclock.Utilities

import com.example.mypillclock.DataClass.PillClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.PillScheduleTimeDataClass
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Database.PillScheduleTimeDBHelper
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

//    https://blog.jdriven.com/2019/07/kotlin-exposed-a-lightweight-sql-library/


class DataClassEntityConverter {
    fun pillInfoEntityToDataClass(pillInfoEntity:PillInfoDBHelper.DBExposedPillEntity):PillInfo{
        return PillInfo(
            pillInfoEntity.id.value,
            pillInfoEntity.name,
            pillInfoEntity.duration,
            pillInfoEntity.frequency,
            pillInfoEntity.amount,
            pillInfoEntity.amountType,
            pillInfoEntity.remindStartDate,
            pillInfoEntity.remindTime,
            pillInfoEntity.rxNumber,
            pillInfoEntity.doctorNote)
    }


    fun pillScheduleDataClassToEntity(pillScheduleTimeDataClass: PillScheduleTimeDataClass): PillScheduleTimeDBHelper.pillScheduleTimeEntity {
        val pillScheduleTable = PillScheduleTimeDBHelper.pillScheduleTimeTable
        val pillScheduleId = pillScheduleTable.select {
             pillScheduleTable.scheduleTime eq pillScheduleTimeDataClass.scheduleTime }.map {
            it[pillScheduleTable.id]
        }.first()

        return PillScheduleTimeDBHelper.pillScheduleTimeEntity[pillScheduleId]


    }


    fun pillInfoDataClassToEntity(pillInfo:PillInfo):PillInfoDBHelper.DBExposedPillEntity{

            val pillinfoTable = PillInfoDBHelper.DBExposedPillsTable
            val pillInfoId = pillinfoTable.select { pillinfoTable.name eq pillInfo.name }.map {
                it[pillinfoTable.id]
            }.first()

        return PillInfoDBHelper.DBExposedPillEntity[pillInfoId]

    }


    fun pillClockInEntityToDataClass(pillClockInEntity:PillClockInDBHelper.ClockInTimeEntity):PillClockInDataClass{
       return PillClockInDataClass(
                pillClockInEntity.id.value,
                PillInfo(
                    pillClockInEntity.name.id.value,
                    pillClockInEntity.name.name,
                    pillClockInEntity.name.duration,
                    pillClockInEntity.name.frequency,
                    pillClockInEntity.name.amount,
                    pillClockInEntity.name.amountType,
                    pillClockInEntity.name.remindStartDate,
                    pillClockInEntity.name.remindTime,
                    pillClockInEntity.name.rxNumber,
                    pillClockInEntity.name.doctorNote),
                pillClockInEntity.category,
                pillClockInEntity.clockInTime
            )
    }





}