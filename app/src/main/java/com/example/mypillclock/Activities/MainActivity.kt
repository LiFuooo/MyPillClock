package com.example.mypillclock.Activities

import SwipeToDeleteCallback
import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.Alarm.AlarmSchedule
import com.example.mypillclock.Alarm.NotificationHelper
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.*
import com.example.mypillclock.Utilities.PillItemAdapter
import com.example.mypillclock.R
import com.example.mypillclock.databinding.ActivityMainBinding
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


class MainActivity : AppCompatActivity() {
    var recreateDatabase = true
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fun setupPermissions() {
            val permissionRead = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val permissionWrite = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (permissionRead != PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "MainActivity Permission to Read denied")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "MainActivity Permission to Write denied")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }

            val externalFileDir = openFileOutput("test.txt", MODE_PRIVATE)
            Log.i("externalFileDir", externalFileDir.toString())
        }
        setupPermissions()


//    ToDo: connect database
        Database.connect("jdbc:h2:${filesDir.absolutePath}/PillInfo", "org.h2.Driver")
        if (recreateDatabase) {
            transaction {
                val table = arrayOf(
                    PillInfoDBHelper.DBExposedPillsTable,
                    PillClockInDBHelper.clockInTimeTable,
                    DiaryCategoryDbHelper.DiaryCategoryTable,
                    DiaryItemDBHelper.DiaryItemsTable,
                    DiaryClockInDBHelper.diaryItemclockInTimeTable,
                    PillScheduleTimeDBHelper.pillScheduleTimeTable
                )
                SchemaUtils.drop(*table)
                SchemaUtils.create(*table)
                PillInfoDBHelper().addSampleDataToDB()
                DiaryCategoryDbHelper().addAllDefaultCategoriesToDB()
                DiaryItemDBHelper().setAllDefaultObjectsIntoDB()
            }
        }


//        TODO: Add Pill when clicking FAB
        binding.AddPillFab.setOnClickListener {
            recreateDatabase = false
            val intent = Intent(this, AddPillActivity::class.java).also {
                startActivityForResult(it, ADD_PILL_ACTIVITY_REQUEST_CODE)

            }
        }

//        TODO: set bottom Fragment Navigation
//        val btm_navi = findViewById<View>(R.id.btm_navi) as BottomNavigationView
        createBottomNavBar(R.id.ic_home, binding.btmNaviMain)



// get PillList into rv
//        setupListOfDataIntoRecycleView()


//        val adapter = PillItemAdapter(this, fakePillList)
//        rvPillItem.adapter = adapter
//        rvPillItem.layoutManager = LinearLayoutManager(this)
//

//       ----- add sample data to DB for testing------
//        val pillInfoDB = pillInfoDBHelper()
//        pillInfoDB.addSampleDataToDB()
//       ---------------------------------------------


        getItemListFromLocalDB()


//        TODO: Notification Part
        val getSavedPillList = PillInfoDBHelper().getPillListFromDB()



        NotificationHelper().createNotificationChannel(this)


        binding.notifiTestBtn.setOnClickListener {
//            NotificationHelper().createPillNotification(this,pillToNotify)
//            if (getSavedPillList.count() != 0){
////                val pillToNotify = getSavedPillList[0]
//                getSavedPillList.forEach {
//                AlarmSchedule(it).scheduleAlarms(it, this)
//                }
//            }
//            startActivity(Intent(this, ClockInHistoryActivity::class.java))
        }


    }

    //    function to get Pill list from database
    private fun getItemListFromLocalDB() {
        val getSavedPillList = PillInfoDBHelper().getPillListFromDB()
        Log.i("Main getSavedPillList", getSavedPillList.size.toString())

        if (getSavedPillList.size > 0) {
            binding.rvPillItem.visibility = View.VISIBLE
            binding.tvNoMorePillRecords.visibility = View.GONE
            setupListOfDataIntoRecycleView(getSavedPillList)
        } else {
            binding.rvPillItem.visibility = View.GONE
            binding.tvNoMorePillRecords.visibility = View.VISIBLE
        }
    }


    //    function to show the list of pill Info in rv
    private fun setupListOfDataIntoRecycleView(SavedPillList: MutableList<PillInfo>) {
        binding.rvPillItem.layoutManager = LinearLayoutManager(this)

        val itemAdapter = PillItemAdapter(this, SavedPillList)
        binding.rvPillItem.adapter = itemAdapter


//        TODO(Step1: Touch Item, go to EditPillActivity Page)
        itemAdapter.setOnClickListener(object : PillItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: PillInfo) {
                val intent = Intent(
                    this@MainActivity,
                    EditPillActivity::class.java
                )
                intent.putExtra(EXTRA_PILL_DETAIL, model)
                startActivity(intent)
            }
        })

// TODO(Step 2: swipe item, show delete item option)
        val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // Call the adapter function when it is swiped for delete
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        val adapter = binding.rvPillItem.adapter as PillItemAdapter
                        adapter.removeAt(viewHolder.adapterPosition)
                        getItemListFromLocalDB()
                    })
                    .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }

//        delteItemTouchHelper
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding.rvPillItem)
        // END

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PILL_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getItemListFromLocalDB()
            } else {
                Log.e("Acctivity_Main", "Canceled or Back Pressed")
            }
        }
    }

    companion object {
        var ADD_PILL_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PILL_DETAIL = "ExtraPillDetail"
    }


}