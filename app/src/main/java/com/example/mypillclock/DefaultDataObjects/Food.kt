package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object Food {
    val CATEGORY_FOOD = DiaryMainDataClass(1,R.drawable.ic_baseline_fastfood_24, "Food")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_FOOD,
            "Beef",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FOOD,
            "Chicken",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FOOD,
            "Pork",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FOOD,
            "Burger",
            R.drawable.ic_baseline_fastfood_24),

        DiaryItemDataClass(
            0,
            CATEGORY_FOOD,
            "Sandwich",
            R.drawable.ic_baseline_fastfood_24)
    )

}