package com.example.mypillclock.Activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Database.PillScheduleTimeDBHelper
import com.example.mypillclock.Database.PillTimeCompareDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DataClassEntityConverter
import com.example.mypillclock.Utilities.ViewOnePillClockInHistoryRvAdapter
import kotlinx.android.synthetic.main.activity_view_one_pill_clockin_history.*
import java.io.File
import java.io.FileOutputStream

// https://stuff.mit.edu/afs/sipb/project/android/docs/training/basics/data-storage/files.html
// https://developer.android.com/training/data-storage/shared/documents-files

class ActivityViewOnePillClockInHistory:AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
//    private var allPillScheduleClockInList = PillScheduleTimeDBHelper().getAllScheduleListFromDB()
    private var gridLayoutManager: GridLayoutManager? = null
    private var thisPageAdapter: ViewOnePillClockInHistoryRvAdapter? = null
    private val TAG = "ActivityViewOnePillClockInHistory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_one_pill_clockin_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//         get position info from getExtra
        if (intent.extras != null) {
            var pillIDFromIntent = intent.getIntExtra("pill id", 1)
            Log.i(TAG, "pill id = $pillIDFromIntent")
            val pillInfoEntity = PillInfoDBHelper().queryOnePillById(pillIDFromIntent)
            val pillInfo  = DataClassEntityConverter().pillInfoEntityToDataClass(pillInfoEntity!!)
            initRecyclerView(pillInfo)
        }
    }



    private fun initRecyclerView(pillInfo:PillInfo) {
//        var allPillScheduleClockInList =  PillScheduleTimeDBHelper().findScheduleListByPillId(pillID)
        var allPillScheduleClockInList =  PillTimeCompareDBHelper().isClockInList(pillInfo)

        var pillListToShow = allPillScheduleClockInList
        thisPageAdapter = ViewOnePillClockInHistoryRvAdapter(this, allPillScheduleClockInList!!)
        rv_viewOnePillClockInHistory.adapter = thisPageAdapter
        rv_viewOnePillClockInHistory.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }




//    setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_one_pill_schedule_actual_time_page, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.tb_one_pill_schedule_actual_time_page_download -> {
            // app icon in action bar clicked; goto parent activity.
            Toast.makeText(this, "download clicked", Toast.LENGTH_SHORT).show()
            downloadFile()
            true
        }
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


//     TODO: click button, then download, code below does not work
    private fun downloadFile(){
        val filename = "pill history file"
        val file = File(this.getFilesDir(), filename)
        val string = "this is file string"
        val outputStream: FileOutputStream

        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE)
            outputStream.write(string.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}