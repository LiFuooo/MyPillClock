package com.example.mypillclock.DataClass

import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillScheduleTimeDataClass(
    val id:Int,
    val name:PillInfo,
    val scheduleTime: Long
):Serializable

