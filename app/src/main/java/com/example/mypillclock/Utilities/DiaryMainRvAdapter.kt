package com.example.mypillclock.Utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.R


// https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
// https://camposha.info/android-recyclerview-master-detail-open-new-activitypass-data/

// use this one:
//https://codingwithmitch.com/blog/android-recyclerview-onclicklistener/
// https://gist.github.com/webserveis/d78b7ec6ba17ca025aa0570c63874319

class DiaryMainRvAdapter(var context: Context,
                         var arrayList: MutableList<DiaryMainDataClass>,
                         val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<DiaryMainRvAdapter.ViewHolder>() {

    private val TAG = "DiaryMainRvAdapter"
//    private var mOnCategoryListener: OnCategoryListener? = null



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryMainRvAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_item_diary_main, parent, false)

        return ViewHolder(viewHolder, onClick)
    }

    override fun onBindViewHolder(holder: DiaryMainRvAdapter.ViewHolder, position: Int) {
        val diaryCategory: DiaryMainDataClass = arrayList[position]
        holder.icons.setImageResource(diaryCategory.icons!!)
        holder.titles.text = diaryCategory.categoryName

    }


    override fun getItemCount(): Int {
        return arrayList.size
    }



    inner class ViewHolder(itemView: View, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var icons = itemView.findViewById<ImageView>(R.id.iv_diary_main_cardView)
        var titles = itemView.findViewById<TextView>(R.id.tv_diary_main_cardView)
         var onCategoryListener: OnCategoryListener = onClick

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(view: View) {
            Log.d(TAG, "onClick: $adapterPosition")
            onCategoryListener(adapterPosition)
        }
    }

}
typealias OnCategoryListener  = (Int) -> Unit

