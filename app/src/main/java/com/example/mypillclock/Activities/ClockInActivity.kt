package com.example.mypillclock.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.Utilities.ClockInPillItemAdapter
import com.example.mypillclock.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_clock_in.*
import kotlinx.android.synthetic.main.activity_clock_in.btm_navi
import kotlinx.android.synthetic.main.activity_diary_main.*


class ClockInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in)

        //        ToDO: List all pills from db here
//         fun getItemListFromLocalDB() {
            val getSavedPillList = pillInfoDBHelper().getPillListFromDB()

            if (getSavedPillList.size > 0) {
                rvClockInPillItem.visibility = View.VISIBLE
                setupListOfDataIntoClockInRV(getSavedPillList)
            } else {
                rvClockInPillItem.visibility = View.GONE
            }



//        TODO: set navigation bar
//        val bnv = findViewById<View>(R.id.btm_navi) as BottomNavigationView
        btm_navi.selectedItemId = R.id.ic_clock_in
        btm_navi.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
        btm_navi.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)

        btm_navi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    val a = Intent(this, MainActivity::class.java)
                    startActivity(a)
                }
                R.id.ic_clock_in -> {
                    val b = Intent(this, ClockInActivity::class.java)
                    startActivity(b)
                }
                R.id.ic_diary -> {
                    val c = Intent(this, DiaryMainActivity::class.java)
                    startActivity(c)
                }
            }
            true
        }
//        val navigation = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
//        navigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.ic_home -> {
//                    val a = Intent(this, MainActivity::class.java)
//                    startActivity(a)
//                }
//                R.id.ic_clock_in -> {
//                    val b = Intent(this, ClockInActivity::class.java)
//                    startActivity(b)
//                }
//                R.id.ic_diary -> {
//                    val c = Intent(this, DiaryMainActivity::class.java)
//                    startActivity(c)
//                }
//            }
//            true
//        }

        }


    //    function to show the list of pill Info in rv
    fun setupListOfDataIntoClockInRV(SavedPillList: MutableList<PillInfo>) {
        rvClockInPillItem.layoutManager = LinearLayoutManager(this)

        val itemAdapter = ClockInPillItemAdapter(this, SavedPillList)
        rvClockInPillItem.adapter = itemAdapter


//
////        TODO(Step1: Touch Item, change icon color)
//        itemAdapter.setOnClickListener(object : ClockInPillItemAdapter.OnClickListener {
//            override fun onClick(position: Int, model: PillInfo) {
////                clockInItemIv.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_light))
//
//        })
//
//    }
    }


//    override fun onClick(position: Int, model: PillInfo) {
//        Toast.makeText(this, "clicked Item", Toast.LENGTH_LONG).show()
////        clockInItemIv.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_light))
//
//
//    }
//
//
//    fun onClockInItemClick(position:Int){
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//
////        clockInTimeDBHelper().addClockInTime(clockInData)
////        change icon color to green
//        clockInItemIv.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_light))
//
//    }

    fun addCount(alreadyCount: Int): Int {
        return alreadyCount + 1
    }



    }
