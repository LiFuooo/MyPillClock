package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class DiaryClockInDataClass (
    val id:Int,
    val item:DiaryItemDataClass,
    val clockInTime:Long
):Serializable

