package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillInfo(
        val id:Int,
        val name:String,
        val quantity:Int,
        val isRepetitive: Boolean,
        val frequency:Int,
        val amount: Int,
        val amountType:String,
        val remindStartDate:String,
        val RemindTime: String,
        val rxNumber: String,
        val doctorNote: String
):Serializable
