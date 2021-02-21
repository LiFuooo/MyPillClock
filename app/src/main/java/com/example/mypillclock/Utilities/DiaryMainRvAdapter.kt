package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.R

class DiaryMainRvAdapter(var context: Context, var arrayList: ArrayList<diaryMainDataClass>) :
    RecyclerView.Adapter<DiaryMainRvAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryMainRvAdapter.ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_diary_main_cardview, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DiaryMainRvAdapter.ItemHolder, position: Int) {
        val charItem: diaryMainDataClass = arrayList[position]

        holder.icons.setImageResource(charItem.icons!!)
        holder.titles.text = charItem.alpha

        holder.titles.setOnClickListener {
            Toast.makeText(context, charItem.alpha, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBindViewHolder(
        holder: DiaryMainRvAdapter.ItemHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var icons = itemView.findViewById<ImageView>(R.id.iv_diary_main_cardView)
        var titles = itemView.findViewById<TextView>(R.id.tv_diary_main_cardView)

    }
}