package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillInfo(
        val id:Int,
        val name:String,
        val duration:Int,
        val frequency:Int,
        val amount: Int,
        val amountType:String,
        val RemindTime: String,
        val doctorNote: String
):Serializable
