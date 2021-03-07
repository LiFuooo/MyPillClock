package com.example.mypillclock.Activities

import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import org.jetbrains.exposed.sql.transactions.transaction


// https://www.youtube.com/watch?v=69C1ljfDvl0

class DiaryMainActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var diaryCategoryList = DiaryCategoryDbHelper().getCategoryListFromDB()
    private var gridLayoutManager: GridLayoutManager? = null
    private var diaryCategoryAdapter: DiaryMainRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)


//        add default Data to DB
//        DiaryCategoryDbHelper().deleteAllCategoryRecords()
//        DiaryCategoryDbHelper().addDefaultCategoriesToDB()

        initRecyclerView()


//        Bottom navigation part, already Done
        createBottomNavBar(R.id.ic_diary, btm_navi)


    }



    private fun initRecyclerView() {
        diaryCategoryList = DiaryCategoryDbHelper().getCategoryListFromDB()
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

    private fun onCategoryClick(position: Int) {
        val intent = Intent(this, DiaryItemsActivity::class.java)
        intent.putExtra("holder position", position)
        this.startActivity(intent)
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
        val dialogueView = layoutInflater.inflate(R.layout.dialog_add_diary_category, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogueView)
            .setPositiveButton(R.string.btn1_text_dialog_add_diary_category, null) //Set to null. We override the onclick
            .setNegativeButton(R.string.btn2_text_dialog_add_dairy_category, null)
            .create()


        var etCategoryName = dialogueView.findViewById<EditText>(R.id.et_dialog_add_dairy_category)as EditText

        dialog.setOnShowListener(OnShowListener {
            val positiveBtn: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    Log.i("diaryAlertDialog", "AlertDialog: Add button pushed")
                    val drawableId = R.drawable.ic_baseline_kayaking_24

                    if(etCategoryName.text.toString().isEmpty()){
                        Log.i(
                            "category name is empty",
                            etCategoryName.text.toString().isEmpty().toString()
                        )
                        Toast.makeText(applicationContext, "Category name can't be empty", Toast.LENGTH_LONG).show()
                    } else{
                        val categoryName = etCategoryName.text.toString()
                        val categoryData = DiaryMainDataClass(0, drawableId, categoryName)
                        DiaryCategoryDbHelper().addDiaryCategory(categoryData)

                        diaryCategoryAdapter?.notifyDataSetChanged()
                        dialog.dismiss()
            }
                }
            })
        })


        dialog.show()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        initRecyclerView()
        }






}

