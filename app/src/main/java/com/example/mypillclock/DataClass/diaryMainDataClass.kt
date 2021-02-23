package com.example.mypillclock.DataClass
import java.io.Serializable

@kotlinx.serialization.Serializable
data class diaryMainDataClass(
    var icons:Int,
    var categoryName:String

):Serializable

