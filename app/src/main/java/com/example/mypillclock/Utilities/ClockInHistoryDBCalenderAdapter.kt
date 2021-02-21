package com.example.mypillclock.Utilities

import com.example.mypillclock.Database.PillClockInDBHelper
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*

class ClockInHistoryDBCalenderAdapter {
    fun hasRecordDays(): MutableList<CalendarDay> {
        val allClockInRecords = PillClockInDBHelper().getAllClockInListFromDB()
        val hasRecordDays: MutableList<CalendarDay> = mutableListOf()
        allClockInRecords.forEach {
            val recordMilliSeconds = it.timeClockIn
            val recordToCalendar = Calendar.getInstance()
            recordToCalendar.timeInMillis = recordMilliSeconds
            val year = recordToCalendar.get(Calendar.YEAR)
            val month = recordToCalendar.get(Calendar.MONTH)
            val day = recordToCalendar.get(Calendar.DAY_OF_MONTH)
            val mydate = CalendarDay.from(year,month,day)

            hasRecordDays.add(mydate)
        }
        return hasRecordDays
    }

    fun today(): MutableList<CalendarDay> {
        val todayList: MutableList<CalendarDay> = mutableListOf()

        val recordToCalendar = Calendar.getInstance()
        val now = System.currentTimeMillis()
        recordToCalendar.timeInMillis = now

        val year = recordToCalendar.get(Calendar.YEAR)
        val month = recordToCalendar.get(Calendar.MONTH)
        val day = recordToCalendar.get(Calendar.DAY_OF_MONTH)
        todayList.add(CalendarDay.from(year,month,day))

        return todayList
    }


}