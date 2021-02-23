package com.example.mypillclock.DataClass
import java.io.Serializable


@kotlinx.serialization.Serializable
data class DiaryItemInCategoryDataClass(
    val id:Int,
    var categoryName:String,
    val itemName:String,
    val itemIcon:Int
):Serializable