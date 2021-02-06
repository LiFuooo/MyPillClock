package com.example.mypillclock.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_clockin.view.*


//https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/

open class ClockInPillItemAdapter(

    val context: Context,
    var itemsList:MutableList<PillInfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener:OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_clockin,
            parent,
            false)
        return PillViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime+
                " ,id = ${currentItem.id}"


        holder.itemView.apply {
            clockInItemTvPillName.text = currentItem.name
            clockInItemtvCount.text = countClockIn()

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, currentItem)
                }
            }

            if (position % 2 == 0) {
                clockInItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorLightGray))
            } else {
                clockInItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white))
            }
        }
    }



    override fun getItemCount()= itemsList.size


    fun removeAt(position: Int){
        val dbHandler = pillInfoDBHelper()
        try {
            dbHandler.deletePill(itemsList[position])
        } catch (e:Exception){

        }

//        if(isDeleted > 0){
//            itemsList.removeAt(position)
////            notifyItemMoved(position-1,position)
    }


    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position:Int, model: PillInfo)
    }

    class PillViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    fun countClockIn(): String {
        return "you have clock-in for 2 days"

    }



}
