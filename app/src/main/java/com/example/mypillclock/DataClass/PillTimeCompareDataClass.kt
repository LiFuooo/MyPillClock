package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillTimeCompareDataClass(
    val id:Int,
    val pillInfo:PillInfo,
    val scheduleTime: PillScheduleTimeDataClass,
    val clockInTime:Long,
    val isClockIn: Boolean
):Serializable
