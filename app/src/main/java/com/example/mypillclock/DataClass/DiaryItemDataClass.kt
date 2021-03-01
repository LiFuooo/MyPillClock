package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class DiaryItemDataClass(
    val id:Int,
    var category: DiaryMainDataClass,
    val itemName:String,
    val itemIcon:Int
):Serializable