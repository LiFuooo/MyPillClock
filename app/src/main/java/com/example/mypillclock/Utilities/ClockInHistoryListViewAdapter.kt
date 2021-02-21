package com.example.mypillclock.Utilities

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.pillClockInDataClass
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ClockInHistoryListViewAdapter(context: Context,
                                    var resources:Int,
                                    var items:MutableList<pillClockInDataClass>,
                                    var dateSelected:Date):
    ArrayAdapter<pillClockInDataClass>(context,resources, items ) {
    private val mContext: Context = context


    override fun getCount(): Int {
       return items.size
    }

    override fun getItem(position: Int): pillClockInDataClass? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    override fun getView(position: Int, onvertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val clockInItemView = layoutInflater.inflate(resources,null)
        var pillName = clockInItemView.findViewById<TextView>(R.id.tv_clockinhistory_item)

//        val allPillClockInRecords = PillClockInDBHelper().getClockInListFromDB()
        val now:Long = System.currentTimeMillis()
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)


        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val sdf_now = sdf.format(now)
        val sdf_dateSelected = sdf.format(dateSelected)



        val pillClockInRecord = items[position]
        val dateOfRecord = pillClockInRecord.timeClockIn
        val sdf_Record = sdf.format(dateOfRecord)
        if(sdf_Record == sdf_dateSelected){
            pillName.text = pillClockInRecord.name
        }

        return clockInItemView
    }


}