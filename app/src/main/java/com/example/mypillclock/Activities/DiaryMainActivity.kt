package com.example.mypillclock.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.Database.DiaryCategoryDbHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryMainRvAdapter
import kotlinx.android.synthetic.main.activity_diary_main.*
import kotlinx.android.synthetic.main.dialog_add_diary_category.*


class DiaryMainActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var diaryCategoryList: MutableList<diaryMainDataClass>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var diaryCategoryAdapter: DiaryMainRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)


//        add default Data to DB
        DiaryCategoryDbHelper().deleteAllCategoryRecords()
        DiaryCategoryDbHelper().addDefaultCategoriesToDb()
        diaryCategoryList = DiaryCategoryDbHelper().getCategoryListFromDB()

//        diaryCategoryList = DiaryCategoryDbHelper().defaultCategories()

        diaryCategoryAdapter = DiaryMainRvAdapter(this, diaryCategoryList!!)
        rv_diary_main.adapter = diaryCategoryAdapter
        rv_diary_main.layoutManager = GridLayoutManager(
            this,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )


//        TODO: Bottom navigation part
//        val bnv = findViewById<View>(R.id.btm_navi) as BottomNavigationView
        btm_navi.selectedItemId = R.id.ic_diary
        btm_navi.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv3)
        btm_navi.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv3)

        btm_navi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    val a = Intent(this, MainActivity::class.java)
                    startActivity(a)

                }
                R.id.ic_clock_in -> {
                    val b = Intent(this, ClockInActivity::class.java)
                    startActivity(b)

                }
                R.id.ic_diary -> {
                    val c = Intent(this, DiaryMainActivity::class.java)
                    startActivity(c)
                }
            }
            true
        }


//        TODO: add button in Toolbar
//        setSupportActionBar(diaryToolbar)


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
            val categoryData = diaryMainDataClass(drawableId, categoryName)
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


}