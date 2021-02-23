package com.example.mypillclock.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.R

class DiaryFoodActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_food_main)

        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        Log.d("Food_getIncomingIntent", "getIncomingIntent: checking for incoming intents.")
        if (intent.hasExtra("image_url") && intent.hasExtra("image_name")) {
            Log.d("Food_getIncomingIntent", "getIncomingIntent: found intent extras.")
            val position = intent.getStringExtra("holder position")
            val categoryName = intent.getStringExtra("category name")
            setItemList()
        }
    }

    fun setItemList(){

    }


}