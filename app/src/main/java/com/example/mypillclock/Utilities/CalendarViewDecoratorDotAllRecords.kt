package com.example.mypillclock.Utilities

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
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




class DotSpan_left(private val radius: Float,
                   private val color: Int): LineBackgroundSpan {

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val oldColor = paint.color
        if (color != 0) {
            paint.color = color
        }
        canvas.drawCircle((left + right) / 2 - 5f, bottom + radius, radius, paint)
        paint.color = oldColor
    }
}


class DotSpan_right(private val radius: Float,
                    private val color: Int) : LineBackgroundSpan{
    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val oldColor = paint.color
        if (color != 0) {
            paint.color = color
        }
        canvas.drawCircle((left + right) / 2 + 5f, bottom + radius, radius, paint)
        paint.color = oldColor
    }
}




class EventDecorator_left(
    private val color: Int,
    private var dates: List<CalendarDay>
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay) = dates.contains(day)

    override fun decorate(view: DayViewFacade) = with(view) {
        addSpan(DotSpan_left(3f, color))
    }
}




class EventDecorator_right(
    private val color: Int,
    private var dates: List<CalendarDay>
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay) = dates.contains(day)

    override fun decorate(view: DayViewFacade) = with(view) {
        addSpan(DotSpan_right(3f, color))
    }

}