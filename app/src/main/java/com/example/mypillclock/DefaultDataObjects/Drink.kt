package com.example.mypillclock.DefaultDataObjects

import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R

object Drink {
    val CATEGORY_DRINK = DiaryMainDataClass(2,R.drawable.ic_baseline_wine_bar_24, "Drink")
    val list = mutableListOf(
        DiaryItemDataClass(
            0,
            CATEGORY_DRINK,
            "Beer",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_DRINK,
            "Wine",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_DRINK,
            "Soda",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_DRINK,
            "Orange Juice",
            R.drawable.ic_baseline_smart_toy_24),

        DiaryItemDataClass(
            0,
            CATEGORY_DRINK,
            "Lemonade",
            R.drawable.ic_baseline_smart_toy_24))


}