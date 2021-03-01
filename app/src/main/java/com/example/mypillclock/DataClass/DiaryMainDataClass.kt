package com.example.mypillclock.DataClass
import java.io.Serializable

@kotlinx.serialization.Serializable
data class DiaryMainDataClass(
    val id: Int,
    val icons:Int,
    val categoryName:String

):Serializable

