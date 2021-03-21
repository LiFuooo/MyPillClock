package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillScheduleTimeDataClass
import com.example.mypillclock.DataClass.PillTimeCompareDataClass
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_view_one_pill_clockin_history.view.*
import kotlinx.android.synthetic.main.item_one_pill_clockin_history.view.*


class ViewOnePillClockInHistoryRvAdapter(
    var context: Context,
    var arrayList: MutableList<PillTimeCompareDataClass>,
) :
    RecyclerView.Adapter<ViewOnePillClockInHistoryRvAdapter.ViewHolder>() {

    private val TAG = "ViewOnePillClockInHistoryRvAdapter"
//    private var mOnCategoryListener: OnCategoryListener? = null



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewOnePillClockInHistoryRvAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_one_pill_clockin_history, parent, false)

        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(
        holder: ViewOnePillClockInHistoryRvAdapter.ViewHolder,
        position: Int
    ) {
        val pillClockInCompareData: PillTimeCompareDataClass = arrayList[position]
        holder.itemView.apply {
            tv_ll_one_pill_clockin_history_name_item.text = pillClockInCompareData.pillName
            tv_ll_one_pill_clockin_history_planTime_item.text = DateTimeFormatConverter().timeLongToDateTimeString(
                pillClockInCompareData.scheduleTime
            )
            tv_ll_one_pill_clockin_history_actualTime_item.text = if(pillClockInCompareData.clockInTime != null){
                DateTimeFormatConverter().timeLongToDateTimeString(pillClockInCompareData.clockInTime)
            } else{
                "None"
            }
            if(pillClockInCompareData.isClockIn){
                iv_ll_one_pill_clockin_history_istaken__item.setImageResource(R.drawable.ic_baseline_check_24)
            } else{
                iv_ll_one_pill_clockin_history_istaken__item.setImageResource(R.drawable.ic_baseline_clear_24)
            }
        }
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }



    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)



}

