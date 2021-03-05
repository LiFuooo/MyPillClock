package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object Smoke {
    val CATEGORY_SMOKE = DiaryMainDataClass(4,R.drawable.ic_baseline_smoking_rooms_24, "Smoking")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_SMOKE,
            "cigarette",
            R.drawable.ic_baseline_sports_kabaddi_24))
}