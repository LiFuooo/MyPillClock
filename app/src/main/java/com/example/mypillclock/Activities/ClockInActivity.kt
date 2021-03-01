package com.example.mypillclock.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        createBottomNavBar(R.id.ic_clock_in, btm_navi)


        }


    //    function to show the list of pill Info in rv
    fun setupListOfDataIntoClockInRV(SavedPillList: MutableList<PillInfo>) {
        rvClockInPillItem.layoutManager = LinearLayoutManager(this)

        val itemAdapter = ClockInPillItemAdapter(this, SavedPillList)
        rvClockInPillItem.adapter = itemAdapter
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clock_in_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId
        if (item.itemId == R.id.toolbar_clockIn_calendar){
            Log.i("diaryPage", "Add button pushed")
//            start activity of clock in calendar
            val intent = Intent(this, ClockInHistoryActivity::class.java)
            this.startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
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
