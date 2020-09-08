package com.example.mypillclock.DataClass

@kotlinx.serialization.Serializable
data class PillInfo(
    val name:String,
    val duration:Int,
    val frequency:Int,
    val amount: Int,
    val amountType:String,
    val RemindTime: String,
    val doctorNote: String?
)
