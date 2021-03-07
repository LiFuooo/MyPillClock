package com.example.mypillclock.Utilities

import com.example.mypillclock.DataClass.PillClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.PillInfoDBHelper

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
                pillClockInEntity.clockInTime,
            )
    }



}