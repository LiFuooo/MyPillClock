package com.example.mypillclock.Utilities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.Activities.*
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.R



// https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
// https://camposha.info/android-recyclerview-master-detail-open-new-activitypass-data/

// use this one:
//https://codingwithmitch.com/blog/android-recyclerview-onclicklistener/

class DiaryMainRvAdapter(var context: Context, var arrayList: MutableList<diaryMainDataClass>) :
    RecyclerView.Adapter<DiaryMainRvAdapter.ViewHolder>(){

    private lateinit var onClickListener: OnClickListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryMainRvAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_item_diary_main, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DiaryMainRvAdapter.ViewHolder, position: Int) {
        val diaryCategory: diaryMainDataClass = arrayList[position]


        holder.icons.setImageResource(diaryCategory.icons!!)
        holder.titles.text = diaryCategory.categoryName

        holder.itemView.setOnClickListener {
           Log.i("viewHolder postion", position.toString())
//            Toast.makeText(context, diaryCategory.categoryName, Toast.LENGTH_SHORT).show()
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()

            val intent = Intent(context, DiaryCategoryItemsActivity::class.java)
            intent.putExtra("holder position", position)
            intent.putExtra("category name", diaryCategory.categoryName)
                context.startActivity(intent)


        }
    }




    override fun getItemCount(): Int {
        return arrayList.size
    }


    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int, model: PillInfo)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        var icons = itemView.findViewById<ImageView>(R.id.iv_diary_main_cardView)
        var titles = itemView.findViewById<TextView>(R.id.tv_diary_main_cardView)
    }
//        private var itemClickListener: ItemClickListener? = null
//
//
//        override fun onClick(v: View?) {
//            val intent: Intent
//            intent = if (getAdapterPosition() === 0) {
//                Intent(context, OneActivity::class.java)
//            } else if (ThemeReader.getPosition() === sth2) {
//                Intent(context, SecondActivity::class.java)
//            } else {
//                Intent(context, DifferentActivity::class.java)
//            }
//            context.startActivity(intent)
//        }
//
//
//        override fun onClick(v: View) {
//            itemClickListener.onItemClick(v, layoutPosition)
//        }
//
//        fun setItemClickListener(ic: ItemClickListener?) {
//            itemClickListener = ic
//        }



//    }

//    interface ItemClickListener {
//        fun onItemClick(v: View?, pos: Int)
//    }
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
//        }public void onClick(View v) {
//
//    final Intent intent;
//    if (getAdapterPosition() == sth){
//       intent =  new Intent(context, OneActivity.class);
//    } else if (getPosition() == sth2){
//       intent =  new Intent(context, SecondActivity.class);
//    } else {
//       intent =  new Intent(context, DifferentActivity.class);
//    }
//    context.startActivity(intent);
//}

//            itemView.setOnClickListener {
//                Log.i("rv_diary_item", "Clicked position  = $position, titles =  ${holder.titles}")
//
//                startActivity(Intent(context, MainActivity::class.java))
//            }

//        }




}

