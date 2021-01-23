package com.example.mypillclock.Activities

import SwipeToDeleteCallback
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.Fragments.PillItemAdapter
import com.example.mypillclock.R
import com.example.mypillclock.Alarm.NotificationHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        fun setupPermissions() {
            val permissionRead = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)
            val permissionWrite = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (permissionRead != PackageManager.PERMISSION_GRANTED ) {
                Log.e("permission", "MainActivity Permission to Read denied")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "MainActivity Permission to Write denied")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            }

            val externalFileDir = openFileOutput("test.txt", MODE_PRIVATE)
            Log.i("externalFileDir", externalFileDir.toString())
        }
        setupPermissions()


//connect database
        Database.connect("jdbc:h2:${filesDir.absolutePath}/PillInfo", "org.h2.Driver")
        transaction {
//            SchemaUtils.drop(DatabaseHelper.DBExposedPillsTable)
            SchemaUtils.create(pillInfoDBHelper.DBExposedPillsTable)
        }

//        AddPill FAB
        AddPillFab.setOnClickListener {
            val intent = Intent(this, AddPillActivity::class.java).also {
                startActivityForResult(it, ADD_PILL_ACTIVITY_REQUEST_CODE)
            }
        }

// get PillList into rv
//        setupListOfDataIntoRecycleView()

        var fakePillList = mutableListOf<PillInfo>(
            PillInfo(1, "name1", 24, 30, 1, "pills", "2020-01-09","12:00PM", "123-098x", "No Food"),
            PillInfo(2, "name2", 2, 3, 2, "pills", "2020-01-09","12:00PM", "123-098x", "No Food"),
            PillInfo(3, "name3", 24, 30, 1, "pills", "2020-01-09","12:00PM", "123-098x", "No Food"),
            PillInfo(4, "name4", 24, 30, 1, "pills", "2020-01-09","12:00PM", "nul123-098xl", "No Food"),
            PillInfo(5, "name5", 2, 3, 20, "pills", "2020-01-09","12:00PM", "123-098x", "No Food"),
            PillInfo(6, "name6", 24, 30, 100, "pills", "2020-01-09","12:00PM", "123-098x", "No Food"),
            PillInfo(7, "name7", 2, 3, 3, "pills", "2020-01-09","12:00PM", "nu123-098xll", "No Food"),
            PillInfo(8, "name8", 24, 30, 1, "pills", "2020-01-09","12:00PM", "123-098x", "No Food")
        )
//        val adapter = PillItemAdapter(this, fakePillList)
//        rvPillItem.adapter = adapter
//        rvPillItem.layoutManager = LinearLayoutManager(this)
//

        getItemListFromLocalDB()

//set up notification channel
        val pillNotification = NotificationHelper()
        pillNotification.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")


    }

    //    function to get Pill list from database
    private fun getItemListFromLocalDB(){
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()

        if(getSavedPillList.size > 0){
            rvPillItem.visibility = View.VISIBLE
            tvNoMorePillRecords.visibility = View.GONE
            setupListOfDataIntoRecycleView(getSavedPillList)
        } else {
            rvPillItem.visibility = View.GONE
            tvNoMorePillRecords.visibility = View.VISIBLE
        }
    }


    //    function to show the list of pill Info in rv
    private fun setupListOfDataIntoRecycleView(SavedPillList: MutableList<PillInfo>){
        rvPillItem.layoutManager = LinearLayoutManager(this)

        val itemAdapter = PillItemAdapter(this, SavedPillList)
        rvPillItem.adapter = itemAdapter


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
                            val adapter = rvPillItem.adapter as PillItemAdapter
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
        deleteItemTouchHelper.attachToRecyclerView(rvPillItem)
        // END

//       TODO( Notification Part)
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()
        getSavedPillList.forEach {
            val pillName = it.name
            NotificationHelper().createNotificationChannel(this,
                NotificationManagerCompat.IMPORTANCE_LOW, true,
                pillName, "Notification channel for cats.")
        }




}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_PILL_ACTIVITY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                getItemListFromLocalDB()
            } else{
                Log.e("Acctivity_Main", "Canceled or Back Pressed")
            }
        }
    }

    companion object {
        var ADD_PILL_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PILL_DETAIL = "ExtraPillDetail"
    }




}