package com.example.mypillclock.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryMainRvAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiaryMainActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var charItem: ArrayList<diaryMainDataClass>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var alphaAdapters: DiaryMainRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)

//        recyclerView = findViewById(R.id.rv_diary_main)
//        gridLayoutManager =
//            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
//        recyclerView?.layoutManager = gridLayoutManager
//        recyclerView?.setHasFixedSize(true)
//
//        charItem = ArrayList()
//        charItem = setAlphas()
//        alphaAdapters = DiaryMainRvAdapter(applicationContext, charItem!!)
//        recyclerView?.adapter = alphaAdapters


//        val navigation = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
//        navigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.ic_home -> {
//                    val a = Intent(this, MainActivity::class.java)
//                    startActivity(a)
//                }
//                R.id.ic_clock_in -> {
//                    val b = Intent(this, ClockInActivity::class.java)
//                    startActivity(b)
//                }
//                R.id.ic_diary -> {
//                    val c = Intent(this, DiaryMainActivity::class.java)
//                    startActivity(c)
//                }
//            }
//            true
//        }

    }

    private fun setAlphas(): ArrayList<diaryMainDataClass> {

        var arrayList: ArrayList<diaryMainDataClass> = ArrayList()

        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_fastfood_24, "A Latter"))


        return arrayList
    }
}