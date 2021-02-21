package com.example.mypillclock.Activities

//import com.example.mypillclock.Utilities.CalenderViewDecoratorCircleToday

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.CalendarViewDecoratorDotAllRecords
import com.example.mypillclock.Utilities.ClockInHistoryDBCalenderAdapter
import com.example.mypillclock.Utilities.ClockInHistoryListViewAdapter
import com.example.mypillclock.Utilities.CurrentDayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_clock_in_history.*
import java.text.SimpleDateFormat
import java.util.*


class ClockInHistoryActivity: AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in_history)


        val dbHelper = PillClockInDBHelper()
//        Add fake data to DB for testing
        dbHelper.deleteAllRecords()
        dbHelper.addFakeDataToDB()
        val allHistoryRecords = dbHelper.getAllClockInListFromDB()

//        val dateLong1 = caV_clockInHistory.date
        val listView = findViewById<ListView>(R.id.lv_clockInHistory)

//        TODO: on create Activity, show today's clock-in record
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val todayFormatted = sdf.format(today)
        val now = System.currentTimeMillis()
        val tomorrow = now + 1000*24*60*60
//        val yesterday = now - 1000*24*60*60
        val mydate = CalendarDay.from(2021, 2, 9)


//


//        -----------------calendar view part -----------------
        var mCalendarView: MaterialCalendarView =
            findViewById(R.id.caV_clockInHistory) as MaterialCalendarView


        mCalendarView.setSelectedDate(today)

        val colorOrange = ContextCompat.getColor(this, R.color.colorBlue);
        val daysListHelper = ClockInHistoryDBCalenderAdapter()
        val DayViewDecorator = CalendarViewDecoratorDotAllRecords(
            colorOrange,
            daysListHelper.hasRecordDays()
        )
        mCalendarView.addDecorator(DayViewDecorator)

        val circleDecorator = CurrentDayDecorator(this)
        mCalendarView.addDecorator(circleDecorator)


//      history text view part
        val dateSelected =  mCalendarView.selectedDate.calendar.time
        val selectedDateFormatted = sdf.format(dateSelected)
        val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory_activity)
        tvOfClockinHistory.text = "Activity of $selectedDateFormatted"




        caV_clockInHistory.setOnDateChangedListener { widget, date, selected ->

//             1. change text View text
            val dateSelected =  date.calendar.time
            val selectedDateFormatted = sdf.format(dateSelected)
            val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory_activity)
            tvOfClockinHistory.text = "Activity of $selectedDateFormatted"


//            2. set records into List View
            val matchedRecords = dbHelper.findRecordsByDate(selectedDateFormatted)
            listView.adapter =ClockInHistoryListViewAdapter(
                this@ClockInHistoryActivity,
                R.layout.item_clock_in_history_pill,
                matchedRecords,
                dateSelected)


        }


    }
//
//    fun calendarDayToSdf(calendarDay: CalendarDay): String? {
//        val year = calendarDay.year
//        val month = calendarDay.month
//        val day = calendarDay.day
//        val calendar = Calendar.getInstance()
//        calendar.set(year,month,day)
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//       return sdf.format(calendar)
//
//
//    }






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



//        caV_clockInHistory.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
//            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
//
//                val year = p1
//                val month = p2
//                val day = p3
//                val calendar = Calendar.getInstance()
//                calendar.set(Calendar.YEAR, year)
//                calendar.set(Calendar.MONTH, month)
//                calendar.set(Calendar.DAY_OF_MONTH, day)
//
//                val selectedDateLong = calendar.timeInMillis
//                val selectedDateFormatted = sdf.format(selectedDateLong)
////                val dateSelected = "$p1-${p2 + 1}-$p3"
////                change text view
//                val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory)
//                tvOfClockinHistory.text = "Activities of $selectedDateFormatted"


//                Toast.makeText(
//                    this@ClockInHistoryActivity,
//                    "The selected date is $selectedDateFormatted, today = $todayFormatted",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                val hasAnyRecord = allHistoryRecords.any {
//                    sdf.format(it.timeClockIn) == selectedDateFormatted
//                }
//
////                Toast.makeText(
////                    this@ClockInHistoryActivity,
////                    "hasAnyRecord =  $hasAnyRecord",
////                    Toast.LENGTH_SHORT
////                ).show()
//
//
//
//                if(hasAnyRecord){
//                    val matchedRecords = dbHelper.findRecordsByDate(selectedDateFormatted)
//
//
//                    Toast.makeText(
//                        this@ClockInHistoryActivity,
//                        "recordLength =  ${matchedRecords.size}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    listView.adapter = ClockInHistoryItemAdapter(this@ClockInHistoryActivity,
//                        R.layout.item_clock_in_history_pill,
//                        matchedRecords,
//                        selectedDateLong)
//                }
//                }


//        })


//    }


}