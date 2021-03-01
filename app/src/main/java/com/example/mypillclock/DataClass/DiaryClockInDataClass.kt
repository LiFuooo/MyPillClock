package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class DiaryClockInDataClass (
    val id:Int,
    val category:String,
    val itemName:String,
    val clockInTime:Long
):Serializable

