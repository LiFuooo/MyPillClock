package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class ClockInDataClass(
    val id:Int,
    val name:String,
    val timeClockIn:String,
    var count: Int
):Serializable