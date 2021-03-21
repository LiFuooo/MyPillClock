package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillTimeCompareDataClass(
    val pillName:String,
    val scheduleTime: Long,
    val clockInTime:Long?,
    val isClockIn: Boolean
):Serializable
