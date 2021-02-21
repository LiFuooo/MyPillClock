package com.example.mypillclock.Utilities

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.mypillclock.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*


// https://stackoverflow.com/questions/37854906/how-to-add-circular-decorator-in-calendar-dayview


//class CalenderViewDecoratorCircleToday(
//    private val color: Int,
//    dates: Collection<CalendarDay?>?
//) :
//        DayViewDecorator {
//        private val dates: HashSet<CalendarDay>
//        override fun shouldDecorate(day: CalendarDay): Boolean {
//            return dates.contains(day)
//        }
//    val drawble = context!!.resources.getDrawable(R.drawable.ic_circle, context!!.theme)
//        override fun decorate(view: DayViewFacade) {
//            view.addSpan(DotSpan(5F, color))
//            view.setBackgroundDrawable(drawble)
//        }
//
//        init {
//            this.dates = HashSet(dates)
//        }
//
//    }


class CurrentDayDecorator(context: Activity?) : DayViewDecorator {
    private val drawable: Drawable?
    var currentDay: CalendarDay = CalendarDay.from(Date())
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == currentDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(drawable!!)
    }

    init {
        drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_circle)
    }
}
