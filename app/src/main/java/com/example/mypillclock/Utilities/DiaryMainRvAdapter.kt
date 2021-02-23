package com.example.mypillclock.Utilities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.Activities.*
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.R


// https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick


class DiaryMainRvAdapter(var context: Context, var arrayList: MutableList<diaryMainDataClass>) :
    RecyclerView.Adapter<DiaryMainRvAdapter.ItemHolder>(){

    private lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryMainRvAdapter.ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_item_diary_main, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DiaryMainRvAdapter.ItemHolder, position: Int) {
        val charItem: diaryMainDataClass = arrayList[position]


        holder.icons.setImageResource(charItem.icons!!)
        holder.titles.text = charItem.categoryName

        holder.titles.setOnClickListener {
            Toast.makeText(context, charItem.categoryName, Toast.LENGTH_LONG).show()
            startActivity(context, Intent(context, DiaryFoodActivity::class.java), Bundle())


        }
    }

    override fun onBindViewHolder(
        holder: DiaryMainRvAdapter.ItemHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        holder.itemView.setOnClickListener {
            Log.i("rv_diary_item", "Clicked position  = $position, titles =  ${holder.titles}")
//
//            startActivity(Intent(context, MainActivity::class.java))
        }
        super.onBindViewHolder(holder, position, payloads)
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }


    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position:Int, model: PillInfo)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
//        val position = itemView.getposi
//        init {
            var icons = itemView.findViewById<ImageView>(R.id.iv_diary_main_cardView)
            var titles = itemView.findViewById<TextView>(R.id.tv_diary_main_cardView)


//        fun onClick(itemView: View){
//            itemView.getLayoutPosition()
//        }
//        public void onClick(View v) {
//
//            final Intent intent;
//            switch (getAdapterPostion()){
//                case 0:
//                intent =  new Intent(context, FirstActivity.class);
//                break;
//
//                case 1:
//                intent =  new Intent(context, SecondActivity.class);
//                break;
//                ...
//                default:
//                intent =  new Intent(context, DefaultActivity.class);
//                break;
//            }
//            context.startActivity(intent);
//        }

//            itemView.setOnClickListener {
//                Log.i("rv_diary_item", "Clicked position  = $position, titles =  ${holder.titles}")
//
//                startActivity(Intent(context, MainActivity::class.java))
//            }

//        }

    }


}

