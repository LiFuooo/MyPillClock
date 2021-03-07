package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillClockInDataClass(
    val id:Int,
    val pillName:PillInfo,
    val category:String = "Medicine",
    val timeClockIn:Long,
):Serializable

