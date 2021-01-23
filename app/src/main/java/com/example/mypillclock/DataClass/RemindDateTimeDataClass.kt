package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class RemindDateTimeDataClass (
    val pillName: String,
    val days: String,
    var hour:Int,
    var minute:Int,
    var remindTime:String,
    var administered: Boolean = false

):Serializable