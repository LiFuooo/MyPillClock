package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class pillClockInDataClass(
    val id:Int,
    val name:String,
    val category:String,
    val timeClockIn:Long,
    var count: Int
):Serializable

