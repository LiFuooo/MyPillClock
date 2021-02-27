package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemInCategoryDataClass
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.R

object Food {
    val list = mutableListOf(
        DiaryItemInCategoryDataClass(
            0,
            "Food",
            "Beef",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemInCategoryDataClass(
            1,
            "Food",
            "Chicken",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemInCategoryDataClass(
            2,
            "Food",
            "Pork",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemInCategoryDataClass(
            3,
            "Food",
            "Burger",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemInCategoryDataClass(
            4,
            "Food",
            "Sandwich",
            R.drawable.ic_baseline_fastfood_24)
    )

}