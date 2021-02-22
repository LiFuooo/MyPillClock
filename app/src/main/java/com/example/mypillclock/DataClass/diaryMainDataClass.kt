package com.example.mypillclock.DataClass
import java.io.Serializable

@kotlinx.serialization.Serializable
data class diaryMainDataClass(
    var icons:Int,
    var categoryName:String

):Serializable


//class diaryMainDataClass {
//
//    var icons:Int ? = 0
//    var alpha:String ? = null
//
//    constructor(icons: Int?, alpha: String?) {
//        this.icons = icons
//        this.alpha = alpha
//    }
//}