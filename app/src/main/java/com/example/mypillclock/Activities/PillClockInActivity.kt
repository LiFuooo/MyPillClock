package com.example.mypillclock.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Utilities.ClockInPillItemAdapter
import com.example.mypillclock.R
import com.example.mypillclock.databinding.ActivityClockInBinding


class PillClockInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityClockInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appContext = getApplicationContext()
//        CustomizeListenerObject.setContext(this)

        //        ToDO: List all pills from db here
            val getSavedPillList = PillInfoDBHelper().getPillListFromDB()
            if (getSavedPillList.size > 0) {
                binding.rvClockInPillItem.visibility = View.VISIBLE
                setupListOfDataIntoClockInRV(getSavedPillList)
            } else {
                binding.rvClockInPillItem.visibility = View.GONE
            }



//        TODO: set navigation bar
        createBottomNavBar(R.id.ic_clock_in, binding.btmNavi)


        }


    //    function to show the list of pill Info in rv
    fun setupListOfDataIntoClockInRV(SavedPillList: MutableList<PillInfo>) {
//        val onClick = this::onItemClick
        binding.rvClockInPillItem.layoutManager = LinearLayoutManager(this)

        val itemAdapter = ClockInPillItemAdapter(this, SavedPillList,CustomizeListenerObject)
        binding.rvClockInPillItem.adapter = itemAdapter
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clock_in_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId
        if (item.itemId == R.id.toolbar_clockIn_calendar){
            Log.i("diaryPage", "Calendar button pushed")
//            start activity of clock in calendar
            val intent = Intent(this, ClockInHistoryActivity::class.java)
            this.startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }


    val CustomizeListenerObject =
        ClockInPillItemAdapter.MultipleListenerClass(this)


    }



