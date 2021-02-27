package com.example.mypillclock.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.DiaryItemInCategoryDataClass
import com.example.mypillclock.DefaultDataObjects.Food
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryItemInEachCategoryRvAdapter
import kotlinx.android.synthetic.main.activity_diary_item_show.*

class DiaryCategoryItemsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_item_show)

//        getIncomingIntent()
        setListDataIntoDiaryItemRV(Food.list)



    }

    private fun getIncomingIntent() {
        Log.d("Food_getIncomingIntent", "getIncomingIntent: checking for incoming intents.")
        if (intent.hasExtra("image_url") && intent.hasExtra("image_name")) {
            Log.d("Food_getIncomingIntent", "getIncomingIntent: found intent extras.")
            val position = intent.getStringExtra("holder position")
            val categoryName = intent.getStringExtra("category name")
            setListDataIntoDiaryItemRV(Food.list)
        }
    }



    fun setListDataIntoDiaryItemRV(DiaryItemList: MutableList<DiaryItemInCategoryDataClass>) {
//        rv_diary_item_in_category.layoutManager = LinearLayoutManager(this)
//
//        val itemAdapter = DiaryCateoryRvAdapter(this, DiaryItemList)
//        rv_diary_item_in_category.adapter = itemAdapter


        var diaryItemAdapter = DiaryItemInEachCategoryRvAdapter(this)
        rv_diary_item_in_category.adapter = diaryItemAdapter
        rv_diary_item_in_category.layoutManager = GridLayoutManager(
            this,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )

    }


    //    function to save pill info to database
//    private fun addDiaryItemClockInRecord(itemClicked: DiaryItemClockInDataClass) {
//        val databaseHelper = DiaryItemClockInDBHelper()
//            try {
//                val currentTime = System.currentTimeMillis()
//                databaseHelper.addDiaryItemClockInRecord(itemClicked,currentTime)
//                setResult(Activity.RESULT_OK)
//                Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//
//                Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
//            }
//
//    }



    }