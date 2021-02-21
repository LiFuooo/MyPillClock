package com.example.mypillclock.Utilities
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*



//// https://github.com/prolificinteractive/material-calendarview/wiki/Decorators
//// https://applandeo.com/blog/material-calendar-view-customized-calendar-widget-android/
//// https://github.com/Applandeo/Material-Calendar-View
//// https://medium.com/@Patel_Prashant_/android-custom-calendar-with-events-fa48dfca8257
////https://github.com/prolificinteractive/material-calendarview

//https://github.com/prolificinteractive/material-calendarview/blob/master/docs/CUSTOMIZATION.md

class CalendarViewDecoratorDotAllRecords(private val color: Int, dates: Collection<CalendarDay?>?) :
    DayViewDecorator {
    private val dates: HashSet<CalendarDay>
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(5F, color))
    }

    init {
        this.dates = HashSet(dates)
    }
}

