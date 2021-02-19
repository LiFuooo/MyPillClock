package com.example.mypillclock.Activities

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Utilities.ClockInHistoryItemAdapter
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_clock_in_history.*
import java.text.SimpleDateFormat
import java.util.*

class ClockInHistoryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in_history)


        val dbHelper = PillClockInDBHelper()
//        Add fake data to DB for testing
        dbHelper.deleteAllRecords()
        dbHelper.addFakeDataToDB()
        val allHistoryRecords = dbHelper.getAllClockInListFromDB()

        val dateLong1 = caV_clockInHistory.date
        val listView = findViewById<ListView>(R.id.lv_clockInHistory)

//        TODO: on create Activity, show today's clock-in record
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val todayFormatted = sdf.format(today)
        val now = System.currentTimeMillis()



//        change text view
        val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory)
        tvOfClockinHistory.text = "Activity of $todayFormatted"


//        val isTodayClockInNotEmpty = allHistoryRecords.any {
//            sdf.format(it.timeClockIn) == todayFormatted
//        }
//
//        if(isTodayClockInNotEmpty){
//            listView.adapter = ClockInHistoryItemAdapter(this@ClockInHistoryActivity,
//                R.layout.item_clock_in_history_pill,
//                allHistoryRecords,
//                now)
//        }



        caV_clockInHistory.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {

                val year = p1
                val month = p2
                val day = p3
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                val selectedDateLong = calendar.timeInMillis
                val selectedDateFormatted = sdf.format(selectedDateLong)
//                val dateSelected = "$p1-${p2 + 1}-$p3"
//                change text view
                val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory)
                tvOfClockinHistory.text = "Activities of $selectedDateFormatted"


//                Toast.makeText(
//                    this@ClockInHistoryActivity,
//                    "The selected date is $selectedDateFormatted, today = $todayFormatted",
//                    Toast.LENGTH_SHORT
//                ).show()

                val hasAnyRecord = allHistoryRecords.any {
                    sdf.format(it.timeClockIn) == selectedDateFormatted
                }

//                Toast.makeText(
//                    this@ClockInHistoryActivity,
//                    "hasAnyRecord =  $hasAnyRecord",
//                    Toast.LENGTH_SHORT
//                ).show()



                if(hasAnyRecord){
                    val matchedRecords = dbHelper.findRecordsByDate(selectedDateFormatted)


                    Toast.makeText(
                        this@ClockInHistoryActivity,
                        "recordLength =  ${matchedRecords.size}",
                        Toast.LENGTH_SHORT
                    ).show()

                    listView.adapter = ClockInHistoryItemAdapter(this@ClockInHistoryActivity,
                        R.layout.item_clock_in_history_pill,
                        matchedRecords,
                        selectedDateLong)
                }
                }


        })


    }
}