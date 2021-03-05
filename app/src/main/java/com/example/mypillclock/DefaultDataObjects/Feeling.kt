package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object Feeling {
    val CATEGORY_FEELING = DiaryMainDataClass(5, R.drawable.ic_baseline_yard_24, "Feeling")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_FEELING,
            "headache",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FEELING,
            "tired",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FEELING,
            "dizzy",
            R.drawable.ic_baseline_smart_toy_24),


    )
}