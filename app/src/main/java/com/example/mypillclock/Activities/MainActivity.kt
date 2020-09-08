package com.example.mypillclock.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillDatabaseHandler
import com.example.mypillclock.Fragments.PillItemAdapter
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AddPill FAB
        AddPillFab.setOnClickListener {
            val intent = Intent(this, AddPillActivity::class.java).also {
                startActivityForResult(it, ADD_PILL_ACTIVITY_REQUEST_CODE)
            }
        }

// get PillList into rv
//        setupListOfDataIntoRecycleView()

        var fakePillList = mutableListOf<PillInfo>(
            PillInfo("name1", 24,30,1,"pills","12:00PM",null),
            PillInfo("name2", 2 ,3,2,"pills","12:00PM",null),
            PillInfo("name3", 24,30,1,"pills","12:00PM",null),
            PillInfo("name4", 24,30,1,"pills","12:00PM",null),
            PillInfo("name5", 2 ,3,20,"pills","12:00PM",null),
            PillInfo("name6", 24,30,100,"pills","12:00PM",null),
            PillInfo("name7", 2 ,3,3,"pills","12:00PM",null),
            PillInfo("name8", 24,30,1,"pills","12:00PM",null)
        )
//        val adapter = PillItemAdapter(this, fakePillList)
//        rvPillItem.adapter = adapter
//        rvPillItem.layoutManager = LinearLayoutManager(this)


        setupListOfDataIntoRecycleView()



    }

    //    function to get Pill list from database
    private fun getItemList(): MutableList<PillInfo> {
        val databaseHandler = PillDatabaseHandler(this)
        return databaseHandler.viewPill()
    }


    //    function to show the list of pill Info in rv
    private fun setupListOfDataIntoRecycleView(){
        if(getItemList().size > 0){

            rvPillItem.visibility = View.VISIBLE
            tvNoMorePillRecords.visibility = View.GONE

            rvPillItem.layoutManager = LinearLayoutManager(this)
            val itemAdapter = PillItemAdapter(this, getItemList())

            rvPillItem.adapter = itemAdapter
            rvPillItem.layoutManager = LinearLayoutManager(this)
        } else {
            rvPillItem.visibility = View.GONE
            tvNoMorePillRecords.visibility = View.VISIBLE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_PILL_ACTIVITY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                setupListOfDataIntoRecycleView()
            } else{
                Log.e("Acctivity_Main", "Canceled or Back Pressed")
            }
        }
    }

    companion object {
        var ADD_PILL_ACTIVITY_REQUEST_CODE = 1
    }




}