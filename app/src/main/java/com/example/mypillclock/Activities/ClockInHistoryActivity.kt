package com.example.mypillclock.Activities

//import com.example.mypillclock.Utilities.CalenderViewDecoratorCircleToday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mypillclock.Database.DiaryClockInDBHelper
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.*
import com.example.mypillclock.databinding.ActivityClockInHistoryBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.util.*


class ClockInHistoryActivity: AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private lateinit var binding:ActivityClockInHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockInHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//         pill ClockIn DB helper
        val pillClockInDBHelper = PillClockInDBHelper()


//------------ Add fake data to DB for testing ---------------
//        pillClockInDBHelper.deleteAllRecords()
//        pillClockInDBHelper.addFakeDataToDB()
//-------------------- TEST OVER -----------------------------


        val allPillClockInHistoryRecords = pillClockInDBHelper.getAllClockInListFromDB()


        //         diary ClockIn DB helper
        val dirayClockInDBHelper = DiaryClockInDBHelper()


//        val dateLong1 = caV_clockInHistory.date
        val pillClockInListView = findViewById<ListView>(R.id.lv_pillClockInHistory)
        val diaryClockInListView = findViewById<ListView>(R.id.lv_DiaryClockInHistory)

//        TODO: on create Activity, show today's clock-in record
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val todayFormatted = sdf.format(today)
        val now = System.currentTimeMillis()
        val tomorrow = now + 1000*24*60*60
//        val yesterday = now - 1000*24*60*60
        val mydate = CalendarDay.from(2021, 2, 9)
        val colorPill = ContextCompat.getColor(this, R.color.colorOrange)
        val colorDiary = ContextCompat.getColor(this, R.color.colorBlue)

//        set color for two dots
        val iv_pillClockIn_dot = findViewById<ImageView>(R.id.iv_pillClockIn_dot)
        iv_pillClockIn_dot.setColorFilter(colorPill)

        val iv_diaryClockIn_dot = findViewById<ImageView>(R.id.iv_diaryClockIn_dot)
        iv_diaryClockIn_dot.setColorFilter(colorDiary)


//        -----------------calendar view part -----------------
        var mCalendarView: MaterialCalendarView =
            findViewById(R.id.caV_clockInHistory) as MaterialCalendarView


        mCalendarView.setSelectedDate(today)

        val daysListHelper = ClockInHistoryDBCalenderAdapter()
        val diaryDaysListHelper = DiaryItemClockInDBCalendarAdapter()

        val circleDecorator = CurrentDayDecorator(this)
        mCalendarView.addDecorator(circleDecorator)



        val eventDecorator_left = EventDecorator_left(
            colorPill,
            daysListHelper.hasRecordDays())
        mCalendarView.addDecorator(eventDecorator_left)

        val eventDecorator_right = EventDecorator_right(
            colorDiary,
            diaryDaysListHelper.hasRecordDays())
        mCalendarView.addDecorator(eventDecorator_right)



//      history text view part
        val dateSelected =  mCalendarView.selectedDate.calendar.time
        val selectedDateFormatted = sdf.format(dateSelected)
        val tvOfClockinHistory = findViewById<TextView>(R.id.tv_clockInHistory_activity)
        val tvOfClockinDiaryHistory = findViewById<TextView>(R.id.tv_diaryClockInHistory_title)
        tvOfClockinHistory.text = "Pill of $selectedDateFormatted"
        tvOfClockinDiaryHistory.text = "Diary of $selectedDateFormatted"



//        set Today's records into List view
        val pillClockInRecordToday = pillClockInDBHelper.findRecordsByDate(todayFormatted)
        pillClockInListView.adapter =ClockInHistoryPillListViewAdapter(
            this@ClockInHistoryActivity,
            R.layout.item_clock_in_history_pill,
            pillClockInRecordToday,
            dateSelected)



        val DiaryClockInRecordToday = dirayClockInDBHelper.findDiaryClockInRecordByDate(todayFormatted)
        diaryClockInListView.adapter =ClockInHistoryDiaryListViewAdapter(
            this@ClockInHistoryActivity,
            R.layout.item_clock_in_history_diary,
            DiaryClockInRecordToday,
            dateSelected)




        binding.caVClockInHistory.setOnDateChangedListener { widget, date, selected ->

//             1. change text View text
            val dateSelected =  date.calendar.time
            val selectedDateFormatted = sdf.format(dateSelected)
            tvOfClockinHistory.text = "Pill of $selectedDateFormatted"
            tvOfClockinDiaryHistory.text = "Diary of $selectedDateFormatted"


//            2. set records into List View
            val matchedPillRecords = pillClockInDBHelper.findRecordsByDate(selectedDateFormatted)
            pillClockInListView.adapter =ClockInHistoryPillListViewAdapter(
                this@ClockInHistoryActivity,
                R.layout.item_clock_in_history_pill,
                matchedPillRecords,
                dateSelected)



            val matchedDiaryRecords = dirayClockInDBHelper.findDiaryClockInRecordByDate(selectedDateFormatted)
            diaryClockInListView.adapter =ClockInHistoryDiaryListViewAdapter(
                this@ClockInHistoryActivity,
                R.layout.item_clock_in_history_diary,
                matchedDiaryRecords,
                dateSelected)


        }


    }




    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clock_in_history_activity_toolbar, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.action_diary_item_add -> {
//            // User chose the "Print" item
//            Toast.makeText(this, "add item btn clicked", Toast.LENGTH_LONG).show()
//            onCreateAddItemDialogue()
//            true
//        }

        android.R.id.home -> {
            // app icon in action bar clicked; goto parent activity.
            this.finish()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}