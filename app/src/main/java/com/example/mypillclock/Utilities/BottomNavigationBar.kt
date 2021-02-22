//package com.example.mypillclock.Utilities
//
//import android.content.Context
//import android.content.Intent
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.core.content.ContextCompat.startActivity
//import com.example.mypillclock.Activities.ClockInActivity
//import com.example.mypillclock.Activities.DiaryMainActivity
//import com.example.mypillclock.Activities.MainActivity
//import com.example.mypillclock.R
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class BottomNavigationBar(val context: Context) {
//
//    fun setUp() {
//
//
//        val bnv = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
//        bnv.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.ic_home -> {
//                    val a = Intent(context, MainActivity::class.java)
//                    startActivity(a)
//                    bnv.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv1)
//                    bnv.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv1)
//                }
//                R.id.ic_clock_in -> {
//                    val b = Intent(context, ClockInActivity::class.java)
//                    startActivity(b)
//                    bnv.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv2)
//                    bnv.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv2)
//                }
//                R.id.ic_diary -> {
//                    val c = Intent(context, DiaryMainActivity::class.java)
//                    startActivity(c)
//                    bnv.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv3)
//                    bnv.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv3)
//                }
//            }
//            true
//        }
//    }
//}