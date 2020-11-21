//package com.example.mypillclock.Utilities
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.*
//import android.graphics.drawable.ColorDrawable
//import android.view.MotionEvent
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mypillclock.Fragments.PillItemAdapter
//import com.example.mypillclock.R
//
//class SwipeToDeleteCallBack(context: Context) :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
//
//    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_withcross_24)
//    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
//    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
//    private val backgound = ColorDrawable()
//    private val backgroundColor = Color.parseColor("#FF0000")
//    private val claerPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
//    private var swipeBack = false
//    private var buttonShowedState = ButtonsSate.GONE
//
//    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//        return super.getMovementFlags(recyclerView, viewHolder)
//    }
//
//    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        val position = viewHolder.adapterPosition
//
//
//    }
//
//    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
//        if (swipeBack){
//            swipeBack = false
//            return 0
//        }
//        return super.convertToAbsoluteDirection(flags, layoutDirection)
//    }
//
//    override fun onChildDraw(c: Canvas,
//                             recyclerView: RecyclerView,
//                             viewHolder: RecyclerView.ViewHolder,
//                             dX: Float, dY: Float,
//                             actionState: Int,
//                             isCurrentlyActive: Boolean) {
//        if(actionState == ACTION_STATE_SWIPE){
//            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//        }
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    fun setTouchListener(c: Canvas,
//                         recyclerView: RecyclerView,
//                         viewHolder: RecyclerView.ViewHolder,
//                         dX: Float, dY: Float,
//                         actionState: Int,
//                         isCurrentlyActive: Boolean){
//        recyclerView.setOnTouchListener(object: View.OnTouchListener{
//            override fun onTouch(view: View, event: MotionEvent): Boolean {
//                var swipeBack = event.action == MotionEvent.ACTION_CANCEL ||
//                        event.action == MotionEvent.ACTION_UP
//                if(swipeBack){
//                    if(dX < -intrinsicWidth) {
//                        buttonShowedState = ButtonsSate.RIGHT_VISIBLE
//                    } else if(dX > intrinsicWidth){
//                        buttonShowedState = ButtonsSate.LEFT_VISIBLE
//                    }
//
//                    if (buttonShowedState != ButtonsSate.GONE){
//                        viewHolder.setTouchListener(c, recyclerView, viewHolder,dX,dY,actionState, isCurrentlyActive)
//                        recyclerView.isClickable = true
//                    }
//                }
//                return false
//            }
//        })
//    }
//
//
//    fun setTouchListener(c: Canvas,
//                         recyclerView: RecyclerView,
//                         viewHolder: RecyclerView.ViewHolder,
//                         dX: Float, dY: Float,
//                         actionState: Int,
//                         isCurrentlyActive: Boolean){
//        recyclerView.setOnTouchListener(View.OnTouchListener(){
//            override onClick
//        })
//    }
//
//}
//
//enum class ButtonsSate{
//    GONE,
//    LEFT_VISIBLE,
//    RIGHT_VISIBLE
//}