package com.example.mypillclock.Activities

import android.content.DialogInterface
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
import com.example.mypillclock.DataClass.DiaryClockInDataClass
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.Database.DiaryCategoryDbHelper
import com.example.mypillclock.Database.DiaryClockInDBHelper
import com.example.mypillclock.Database.DiaryItemDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryItemRvAdapter
import com.example.mypillclock.databinding.ActivityDiaryItemShowBinding
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction

class DiaryItemsActivity: AppCompatActivity() {

    private val TAG = "DiaryItemsActivity"
    private var diaryItemAdapter: DiaryItemRvAdapter? = null
    private var diaryItemsList: MutableList<DiaryItemDataClass>? = null
    private var positionFromIntent:Int = 1
    private var categoryData: DiaryMainDataClass? = null
    private lateinit var binding:ActivityDiaryItemShowBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryItemShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        DiaryItemDBHelper().deleteAllItemsFromDB()
//        DiaryItemDBHelper().setAllDefaultObjectsIntoDB()

        Log.i(TAG, intent.extras.toString())
        if (intent.extras != null) {
            positionFromIntent = intent.getIntExtra("holder position", 1)
            Log.i(TAG, "position = $positionFromIntent")

            transaction {
                val itemList = getItemListFromDb(positionFromIntent + 1)
                diaryItemsList = itemList
                setListDataIntoDiaryItemRV(itemList)
                getCagetoryData()
            }
        }

    }



    fun onCreateAddItemDialogue() {
        val dialogueView = layoutInflater.inflate(R.layout.dialog_add_diary_item, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogueView)
            .setPositiveButton(R.string.btn1_text_dialog_add_diary_item, null) //Set to null. We override the onclick
            .setNegativeButton(R.string.btn2_text_dialog_add_dairy_item, null)
            .create()


        var etItemName = dialogueView.findViewById<EditText>(R.id.et_dialog_add_dairy_item)as EditText

        dialog.setOnShowListener(DialogInterface.OnShowListener {
            val positiveBtn: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    var newItemData: DiaryItemDataClass? = null
                    Log.i("diaryAlertDialog", "AlertDialog: Add button pushed")
                    val itemIcon = R.drawable.ic_baseline_kayaking_24

                    if (etItemName.text.toString().isEmpty()) {
                        Log.i(
                            "Item Name is empty",
                            etItemName.text.toString().isEmpty().toString()
                        )
                        Toast.makeText(
                            applicationContext,
                            "Item name can't be empty",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val itemName = etItemName.text.toString()
                        Log.i("categoryData", categoryData?.categoryName.isNullOrEmpty().toString())
                        val itemData =
                            categoryData?.let { it1 ->
                                DiaryItemDataClass(
                                    if (newItemData == null) 0 else newItemData!!.id,
                                    it1,
                                    itemName,
                                    itemIcon
                                )
                            }
                        if (itemData != null) {
                            DiaryItemDBHelper().addDiaryItemToDB(itemData)
                            Log.i("item activity item name", itemData.itemName)
//                            diaryItemsList?.add(itemData)

                        }
                        diaryItemAdapter?.notifyDataSetChanged()
                        dialog.dismiss()


                    }
                }
            })
        })


        dialog.show()
    }

//    should test override onResume() too
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        transaction {
            val itemList = getItemListFromDb(positionFromIntent + 1)
            diaryItemsList = itemList
            setListDataIntoDiaryItemRV(itemList)
            getCagetoryData()
        }
    }

    private fun getCagetoryData(){
        categoryData = DiaryCategoryDbHelper.DiaryCategoryEntity[positionFromIntent + 1].let {
            DiaryMainDataClass(
                it.id.value,
                it.icons,
                it.categoryName
            )}
    }



    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.diary_item_toolbar, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_diary_item_add -> {
            // User chose the "Print" item
            Toast.makeText(this, "add item btn clicked", Toast.LENGTH_LONG).show()
            onCreateAddItemDialogue()
            true
        }

        android.R.id.home -> {
            // app icon in action bar clicked; goto parent activity.
            this.finish()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    private fun onItemClick(position: Int) {
//        TODO: add clock-in data into DB
        val now = System.currentTimeMillis()
        val itemClockIn = diaryItemsList?.get(position)?.let { DiaryClockInDataClass(0, it, now) }
        if (itemClockIn != null) {
            DiaryClockInDBHelper().addDiaryItemClockInRecordToDB(itemClockIn)
        }
//        Toast.makeText(this,"ClockIn position = $position", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "ClockIn item = $itemClockIn", Toast.LENGTH_LONG).show()
    }


    private fun getItemListFromDb(categoryId: Int): MutableList<DiaryItemDataClass> {
        return DiaryCategoryDbHelper.DiaryCategoryEntity[categoryId].items.toDataClassList()
            .toMutableList()
    }

    private fun SizedIterable<DiaryItemDBHelper.DiaryItemEntity>.toDataClassList() =
        map { categoryTableEntity ->
            categoryTableEntity.toDataClass()
        }

    fun DiaryItemDBHelper.DiaryItemEntity.toDataClass() =
        DiaryItemDataClass(
            id.value,
            category.toDataClass(),
            itemName,
            itemIcon
        )

    fun DiaryCategoryDbHelper.DiaryCategoryEntity.toDataClass(): DiaryMainDataClass {
        return let { DiaryMainDataClass(it.id.value, it.icons, it.categoryName) }
    }


    private fun setListDataIntoDiaryItemRV(list: MutableList<DiaryItemDataClass>) {
        val onClick = this::onItemClick
        diaryItemAdapter = DiaryItemRvAdapter(this, list, onClick)
        binding.rvDiaryItem.adapter = diaryItemAdapter
        binding.rvDiaryItem.layoutManager = GridLayoutManager(
            this,
            4,
            LinearLayoutManager.VERTICAL,
            false
        )
    }




}


