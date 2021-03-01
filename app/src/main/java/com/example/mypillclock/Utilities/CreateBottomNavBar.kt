package com.example.mypillclock.Activities

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.mypillclock.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Activity.createBottomNavBar(pageId: Int, bottomNavigationView: BottomNavigationView) {
    bottomNavigationView.selectedItemId = pageId
    bottomNavigationView.itemIconTintList =
        ContextCompat.getColorStateList(this, R.color.color_bnv3)
    bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv3)

    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
}