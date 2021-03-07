package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mypillclock.DataClass.DiaryClockInDataClass
import com.example.mypillclock.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ClockInHistoryDiaryListViewAdapter (context: Context,
    var resources:Int,
    var items:MutableList<DiaryClockInDataClass>,
    var dateSelected: Date):
    ArrayAdapter<DiaryClockInDataClass>(context,resources, items ) {
        private val mContext: Context = context


        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(position: Int): DiaryClockInDataClass? {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }



        override fun getView(position: Int, onvertView: View?, parent: ViewGroup): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val clockInItemView = layoutInflater.inflate(resources,null)
            var diaryItemName = clockInItemView.findViewById<TextView>(R.id.tv_clockinhistory_diary_item)
            var diaryItemIcon = clockInItemView.findViewById<ImageView>(R.id.iv2_clockinhistory_diary_item)
            var diaryItemClockInTime = clockInItemView.findViewById<TextView>(R.id.tv2_clockinhistory_diary_item)

//        val allPillClockInRecords = PillClockInDBHelper().getClockInListFromDB()
            val now:Long = System.currentTimeMillis()
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)


            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sdf_now = sdf.format(now)
            val sdf_dateSelected = sdf.format(dateSelected)



            val diaryClockInRecord = items[position]
            val dateOfRecord = diaryClockInRecord.clockInTime

            val sdf_time = SimpleDateFormat("HH:mm")
            val timeString = sdf_time.format(dateOfRecord)


            val sdf_Record = sdf.format(dateOfRecord)
            if(sdf_Record == sdf_dateSelected){
                diaryItemName.text = diaryClockInRecord.item.itemName
                diaryItemIcon.setImageResource(diaryClockInRecord.item.itemIcon)
                diaryItemClockInTime.text = String.format("%s", timeString)
            }

            return clockInItemView
        }


    }