package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_clockin.view.*


//https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/

open class ClockInPillItemAdapter(

    val context: Context,
    var itemsList:MutableList<PillInfo>,

) : RecyclerView.Adapter<ClockInPillItemAdapter.clockInItemViewHolder>() {

    private var onClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): clockInItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_clockin,
            parent,
            false
        )
        return clockInItemViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: clockInItemViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime +
                " ,id = ${currentItem.id}"
        val currentTime = System.currentTimeMillis()


        holder.itemView.apply {
            clockInItemTvPillName.text = currentItem.name
            clockInItemtvCount.text = countClockIn()



            holder.clockInItemImageView.setOnClickListener {
                holder.clockInItemImageView.setImageResource(R.drawable.ic_baseline_snowboarding_24)
                PillClockInDBHelper().addClockInRecord(currentItem, currentTime)
            }

//            holder.ClockInItemBtn.setOnClickListener {
//                startActivity(Intent(context, ClockInHistoryActivity::class.java))
//            }


            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, currentItem)
                }

            }

            if (position % 2 == 0) {
                clockInItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorLightGray
                    )
                )
            } else {
                clockInItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }
    }


    override fun getItemCount() = itemsList.size


    fun removeAt(position: Int) {
        val dbHandler = pillInfoDBHelper()
        try {
            dbHandler.deletePill(itemsList[position])
        } catch (e: Exception) {

        }

//        if(isDeleted > 0){
//            itemsList.removeAt(position)
////            notifyItemMoved(position-1,position)
    }


    fun setOnClickListener(onClickListener: OnItemClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int, model: PillInfo)
    }

//    class PillViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    var count = 0
    fun countClockIn(): String {
        count += 1
        return "you have clock-in for" + count + "days"

    }

    inner class clockInItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val clockInItemImageView = itemView.clockInItemIv
        val clockInItemPillName = itemView.clockInItemTvPillName
        val clockInItemCount = itemView.clockInItemtvCount
        val ClockInItemBtn = itemView.checkClockInHistoryBtn

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(AdapterView,p0,position,0)
            }
        }


    }

}





