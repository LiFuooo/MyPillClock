package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class pillClockInDataClass(
    val id:Int,
    val pillName:String,
    val categoryS:String = "Medicine",
    val timeClockIn:Long,
):Serializable

