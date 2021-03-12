package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object SleepGetUp {
    val CATEGORY_SLEEP = DiaryMainDataClass(6, R.drawable.ic_baseline_smoking_rooms_24, "Sleep")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_SLEEP,
            "sleep",
            R.drawable.ic_baseline_sports_kabaddi_24)
    )
}