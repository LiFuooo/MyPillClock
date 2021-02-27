package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemInCategoryDataClass
import com.example.mypillclock.R

object Exercise {
    val CATEGORY_EXCERCISE = "Exercise"
    val list = mutableListOf(
        DiaryItemInCategoryDataClass(
            0,
            CATEGORY_EXCERCISE,
            "Football",
            R.drawable.ic_baseline_sports_kabaddi_24),

        DiaryItemInCategoryDataClass(
            1,
            CATEGORY_EXCERCISE,
            "Basketball",
            R.drawable.ic_baseline_sports_kabaddi_24),

        DiaryItemInCategoryDataClass(
            3,
            CATEGORY_EXCERCISE,
            "Soda",
            R.drawable.ic_baseline_sports_kabaddi_24)
    )
}