package com.example.mypillclock.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.Fragments.ClockInPillItemAdapter
import com.example.mypillclock.Fragments.PillItemAdapter
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_clock_in.*

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
        }


        //    function to show the list of pill Info in rv
         fun setupListOfDataIntoClockInRV(SavedPillList: MutableList<PillInfo>) {
            rvClockInPillItem.layoutManager = LinearLayoutManager(this)

            val itemAdapter = ClockInPillItemAdapter(this, SavedPillList)
            rvClockInPillItem.adapter = itemAdapter

        }
    }
