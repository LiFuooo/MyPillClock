package com.example.mypillclock.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.fragment_pill_item_adapter.view.*


class PillItemAdapter(val context:Context, var items:List<PillInfo>) :
    RecyclerView.Adapter<PillItemAdapter.PillViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_pill_item_adapter,
            parent,
            false)

        return PillViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        val currentItem = items[position]
        val displayString = currentItem.name + ",  " + currentItem.amount + currentItem.amountType

        holder.itemView.apply {
            tvPillItem_Qty.text = currentItem.amount.toString()
            tvPillItem_content.text = displayString


        if(position %2 == 0){
            llPillItem.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else{
            llPillItem.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
            )
            )
        }
        }

    }

    override fun getItemCount()= items.size


    class PillViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val llPillItem = itemView.llPillItem
        val tvPillItemQty = itemView.tvPillItem_Qty
        val tvPillItemcontent = itemView.tvPillItem_content
        val ivAlarmOn = itemView.ivAlarmOn

    init{
        itemView.setOnClickListener(this)
    }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }


}

