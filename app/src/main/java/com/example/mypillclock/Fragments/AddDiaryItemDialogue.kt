package com.example.mypillclock.Fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.Database.DiaryItemDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryItemRvAdapter

class AddDiaryItemDialogue(
    val context: Context,
    val diaryItemsList: MutableList<DiaryItemDataClass>?,
    val diaryItemAdapter: DiaryItemRvAdapter?,
    val category: DiaryMainDataClass
) {

    fun onCreateAddCategoryDialogue() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogueView = Activity().layoutInflater.inflate(R.layout.dialog_add_diary_item, null)
        dialogBuilder.setView(dialogueView)


        dialogBuilder.setPositiveButton(
            R.string.btn1_text_dialog_add_diary_item
        ) { dialog, id ->
            Log.i("diaryItem AlertDialog", "AlertDialog: Add button pushed")

//                      add item to DB
            val drawableId = R.drawable.ic_baseline_kayaking_24
            val itemName = "New Item"

            val diaryItemData = DiaryItemDataClass(0,category, itemName,drawableId)
            if (diaryItemsList != null) {
                diaryItemsList.add(diaryItemData)
            }
            DiaryItemDBHelper().addDiaryItemToDB(diaryItemData)
            diaryItemAdapter?.notifyDataSetChanged()
        }


        dialogBuilder.setNegativeButton(
            R.string.btn2_text_dialog_add_dairy_item,
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Log.i("diary item AlertDialog", "AlertDialog: Cancel button pushed")
                    if (dialog != null) {
                        dialog.dismiss()
                    }
                }
            })

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}