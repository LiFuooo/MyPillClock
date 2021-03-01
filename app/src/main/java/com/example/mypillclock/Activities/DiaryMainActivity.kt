package com.example.mypillclock.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.Database.DiaryCategoryDbHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryMainRvAdapter
import kotlinx.android.synthetic.main.activity_diary_main.*


// https://www.youtube.com/watch?v=69C1ljfDvl0

class DiaryMainActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var diaryCategoryList: MutableList<DiaryMainDataClass>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var diaryCategoryAdapter: DiaryMainRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)


//        add default Data to DB


//        diaryCategoryList = DiaryCategoryDbHelper().getCategoryListFromDB()

        initRecyclerView()


//        TODO: Bottom navigation part, already Done
        createBottomNavBar(R.id.ic_diary, btm_navi)


//        TODO: add button in Toolbar
//        setSupportActionBar(diaryToolbar)


    }



    private fun initRecyclerView() {
        val onClick = this::onCategoryClick
        diaryCategoryAdapter = DiaryMainRvAdapter(this, diaryCategoryList!!, onClick)
        rv_diary_main.adapter = diaryCategoryAdapter
        rv_diary_main.layoutManager = GridLayoutManager(
            this,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.diary_tool_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId
        if (item.itemId == R.id.toolbarAdd){
            Log.i("diaryPage", "Add button pushed")
            onCreateAddCategoryDialogue()

        }
        return super.onOptionsItemSelected(item)
    }


    fun onCreateAddCategoryDialogue() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogueView = layoutInflater.inflate(R.layout.dialog_add_diary_category, null)
        dialogBuilder.setView(dialogueView)


        dialogBuilder.setPositiveButton(
            getString(R.string.btn1_text_dialog_add_dairy_category)
        ) { dialog, id ->
            Log.i("diaryAlertDialog", "AlertDialog: Add button pushed")

            //            add category to DB
            val drawableId = R.drawable.ic_baseline_kayaking_24
            //                    val categoryName = et_dialog_add_dairy_category.text.toString().trim()
            val categoryName = "new category"
            val categoryData = DiaryMainDataClass(0,drawableId, categoryName)
            diaryCategoryList?.add(categoryData)
            DiaryCategoryDbHelper().addDiaryCategory(categoryData)
            diaryCategoryAdapter?.notifyDataSetChanged()
            }


        dialogBuilder.setNegativeButton(
            getString(R.string.btn2_text_dialog_add_dairy_category),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Log.i("diaryAlertDialog", "AlertDialog: Cancel button pushed")
                    if (dialog != null) {
                        dialog.dismiss()
                    }
                }
            })

        val dialog = dialogBuilder.create()
        dialog.show()



    }

    fun onCategoryClick(position: Int) {
        val intent = Intent(this, DiaryItemsActivity::class.java)
            intent.putExtra("holder position", position)
//            intent.putExtra("category name", this.categoryName)
        this.startActivity(intent)
    }


}

