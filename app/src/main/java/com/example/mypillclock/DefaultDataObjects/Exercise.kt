package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object Exercise {
    val CATEGORY_EXCERCISE = DiaryMainDataClass(0,R.drawable.ic_baseline_snowboarding_24, "Exercise")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_EXCERCISE,
            "Football",
            R.drawable.ic_baseline_sports_kabaddi_24),

        DiaryItemDataClass(
            1,
            CATEGORY_EXCERCISE,
            "Basketball",
            R.drawable.ic_baseline_sports_kabaddi_24),

        DiaryItemDataClass(
            3,
            CATEGORY_EXCERCISE,
            "Soda",
            R.drawable.ic_baseline_sports_kabaddi_24)
    )
}