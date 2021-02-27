package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class DiaryItemClockInDataClass (
    val id:Int,
    val category:String,
    val itemName:String,
    val clockInTime:Long
):Serializable

