//package com.example.mypillclock.Reminder
//
//import android.os.*
//import androidx.fragment.app.DialogFragment
//import com.example.mypillclock.DataClass.RemindDateTimeDataClass
//import com.example.mypillclock.Database.reminderDBHelper
//
//class ReminderDialog:DialogFragment(), ReminderContracts.View {
//
//    var handlerThread: HandlerThread?= null
//    var backgroundHandler: Handler? = null
//    val foregroundHandler = Handler(Looper.getMainLooper())
//
//    var presenter:ReminderContracts.Presenter? = null
//    var onCloseListener: ParcelFileDescriptor.OnCloseListener? = null
//
//    interface onCloseListener {
//        fun onCLose(opCode: Int, reminderData: RemindDateTimeDataClass)
//    }
//
//    companion object {
//        const val TAG = "ReminderDialog"
//        // key for passed in data
//        const val KEY_DATA = "reminder_data"
//        const val KEY_ID = "id"
//        const val KEY_ADMINISTERED = "administered"
//
//        // opcodes for success
//        const val REMINDER_CREATED = 0
//        const val REMINDER_UPDATED = 1
//        const val REMINDER_DELETED = 2
//
//        // error states for validation
//        const val ERROR_NO_NAME = 0
//        const val ERROR_NO_MEDICINE = 1
//        const val ERROR_NO_TIME = 2
//        const val ERROR_NO_DAYS = 3
//        const val ERROR_NO_DESC = 4
//        const val ERROR_SAVE_FAILED = 5
//        const val ERROR_DELETE_FAILED = 6
//        const val ERROR_UPDATE_FAILED = 7
//
//        fun newInstance(args: Bundle?):ReminderDialog{
//            val reminderDialog = ReminderDialog()
//            if (args != null){
//                reminderDialog.arguments = args
//            }
//            return reminderDialog
//        }
//    }
//
//    override fun OnCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//
//        handlerThread = HandlerThread("This is handler Thread")
//        handlerThread!!.start()
//        backgroundHandler = Handler(handlerThread!!.looper)
//
//        val args = arguments
//        var reminderData: RemindDateTimeDataClass
//        if(args != null) {
//            reminderData = args.getParcelable(KEY_DATA)
//        }
//        val reminderDBHelper = reminderDBHelper()
//        val model = Model(reminderDBHelper, reminderData)
//
//        presenter = Presenter(this, model)
//
//
//    }
//
//
//
//
//
//
//
//}