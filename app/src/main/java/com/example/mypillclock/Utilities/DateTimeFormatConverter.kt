package com.example.mypillclock.Utilities

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeFormatConverter {
    val now = System.currentTimeMillis()
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

    fun dateTimeStringToLong(dateFormat: String?): Long {
        var date = Date()
        try {
            date = formatter.parse(dateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
//    println("Today is $date")
        return date.time
    }
    fun timeLongToDateString(time:Long): String? {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        return dateFormatter.format(time)
    }

    fun timeLongToTimeString(time:Long): String? {
        val dateFormatter = SimpleDateFormat("HH:mm")
        return dateFormatter.format(time)
    }

    fun timeLongToDateTimeString(time:Long): String? {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return dateFormatter.format(time)
    }

}