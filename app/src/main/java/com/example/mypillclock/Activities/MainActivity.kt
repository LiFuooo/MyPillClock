package com.example.mypillclock.Activities

import SwipeToDeleteCallback
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.util.Log.INFO
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.EnvironmentCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.DatabaseHelper
import com.example.mypillclock.Fragments.PillItemAdapter
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.jar.Manifest
import java.util.logging.Level.INFO


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

//
        }
        setupPermissions()

        Database.connect("jdbc:h2:${filesDir.absolutePath}/PillInfo", "org.h2.Driver")
        transaction {
            SchemaUtils.create(DatabaseHelper.DBExposedPillsTable)
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
            PillInfo(1, "name1", 24, 30, 1, "pills", "12:00PM", "123-098x", "No Food"),
            PillInfo(2, "name2", 2, 3, 2, "pills", "12:00PM", "123-098x", "No Food"),
            PillInfo(3, "name3", 24, 30, 1, "pills", "12:00PM", "123-098x", "No Food"),
            PillInfo(4, "name4", 24, 30, 1, "pills", "12:00PM", "nul123-098xl", "No Food"),
            PillInfo(5, "name5", 2, 3, 20, "pills", "12:00PM", "123-098x", "No Food"),
            PillInfo(6, "name6", 24, 30, 100, "pills", "12:00PM", "123-098x", "No Food"),
            PillInfo(7, "name7", 2, 3, 3, "pills", "12:00PM", "nu123-098xll", "No Food"),
            PillInfo(8, "name8", 24, 30, 1, "pills", "12:00PM", "123-098x", "No Food")
        )
//        val adapter = PillItemAdapter(this, fakePillList)
//        rvPillItem.adapter = adapter
//        rvPillItem.layoutManager = LinearLayoutManager(this)
//

        getItemListFromLocalDB()



    }

    //    function to get Pill list from database
    private fun getItemListFromLocalDB(){
        val getSavedPillList = DatabaseHelper().getPillListFromDB()

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
                // TODO (Step 6: Call the adapter function when it is swiped for delete)
                // START
                val adapter = rvPillItem.adapter as PillItemAdapter
                adapter.removeAt(viewHolder.adapterPosition)

                getItemListFromLocalDB() // Gets the latest list from the local database after item being delete from it.
                // END
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rvPillItem)
        // END



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