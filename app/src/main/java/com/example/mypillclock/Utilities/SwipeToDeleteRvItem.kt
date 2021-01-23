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






import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.R


// TODO (Step 1: Create a class for delete feature same as edit feature just changed the icon and direction.)
// START
// For detail explanation of this class you can look at below link.
// https://medium.com/@kitek/recyclerview-swipe-to-delete-easier-than-you-thought-cff67ff5e5f6
/**
 * A abstract class which we will use for delete feature.
 */
abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {

    private val deleteIcon = ContextCompat.getDrawable(
        context,
        R.drawable.ic_baseline_delete_withcross_24
    )
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        /**
         * To disable "swipe" for specific item return 0 here.
         * For example:
         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
         * if (viewHolder?.adapterPosition == 0) return 0
         */
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive
        Log.i("dX", dX.toString())
        Log.i("dY", dY.toString())

        if (isCanceled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        Log.i("itemView.right", itemView.right.toString())
        Log.i("iView.right+dX.toInt()", (itemView.right + dX.toInt()).toString())
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }



//    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, swipeDir: Int) {
//
//        // Show delete confirmation if swipped left
//        if (swipeDir == ItemTouchHelper.LEFT) {
//            val builder: AlertDialog.Builder = Builder(getActivity())
//            builder.setMessage("Are you sure you want to delete?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
//                    deleteItem(viewHolder)
//                    //                                    getActivity().finish();
//                })
//                .setNegativeButton("No",
//                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
//            val alert: AlertDialog = builder.create()
//            alert.show()
//        } else if (swipeDir == ItemTouchHelper.RIGHT) {
//            // Show edit dialog
//        }
//    }
//}
// END

}