//import NotesRecyclerAdapter.OnNoteListener
//import android.R
//import android.content.Intent
//import android.os.Bundle
//import android.provider.ContactsContract.CommonDataKinds.Note
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//
//class NotesListActivity : AppCompatActivity(), OnNoteListener, View.OnClickListener {
//    // ui components
//    private var mRecyclerView: RecyclerView? = null
//
//    // vars
//    private val mNotes: ArrayList<Note> = ArrayList()
//    private var mNoteRecyclerAdapter: NotesRecyclerAdapter? = null
//    private var mNoteRepository: NoteRepository? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_notes_list)
//        mRecyclerView = findViewById(R.id.recyclerView)
//        findViewById<View>(R.id.fab).setOnClickListener(this)
//        initRecyclerView()
//        mNoteRepository = NoteRepository(this)
//        retrieveNotes()
//        //        insertFakeNotes();
//        setSupportActionBar(findViewById<View>(R.id.notes_toolbar) as Toolbar)
//        title = "Notes"
//    }
//
//    private fun retrieveNotes() {
//        mNoteRepository.retrieveNotesTask().observe(this, object : Observer<List<Note?>?>() {
//            fun onChanged(@Nullable notes: List<Note>?) {
//                if (mNotes.size() > 0) {
//                    mNotes.clear()
//                }
//                if (notes != null) {
//                    mNotes.addAll(notes)
//                }
//                mNoteRecyclerAdapter.notifyDataSetChanged()
//            }
//        })
//    }
//
//    private fun insertFakeNotes() {
//        for (i in 0..999) {
//            val note = Note()
//            note.setTitle("title #$i")
//            note.setContent("content #: $i")
//            note.setTimestamp("Jan 2019")
//            mNotes.add(note)
//        }
//        mNoteRecyclerAdapter.notifyDataSetChanged()
//    }
//
//    private fun initRecyclerView() {
//        val linearLayoutManager = LinearLayoutManager(this)
//        mRecyclerView!!.layoutManager = linearLayoutManager
//        val itemDecorator = VerticalSpacingItemDecorator(10)
//        mRecyclerView!!.addItemDecoration(itemDecorator)
//        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView)
//        mNoteRecyclerAdapter = NotesRecyclerAdapter(mNotes, this)
//        mRecyclerView!!.adapter = mNoteRecyclerAdapter
//    }
//
//    fun onNoteClick(position: Int) {
//        val intent = Intent(this, NoteActivity::class.java)
//        intent.putExtra("selected_note", mNotes[position])
//        startActivity(intent)
//    }
//
//    override fun onClick(view: View?) {
//        val intent = Intent(this, NoteActivity::class.java)
//        startActivity(intent)
//    }
//
//    private fun deleteNote(note: Note) {
//        mNotes.remove(note)
//        mNoteRecyclerAdapter.notifyDataSetChanged()
//        mNoteRepository.deleteNoteTask(note)
//    }
//
//    var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
//        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                deleteNote(mNotes[viewHolder.adapterPosition])
//            }
//        }
//
//    companion object {
//        private const val TAG = "NotesListActivity"
//    }
//}
//
//
////---------------------------------------------------------------------------------------------------
//
//class NotesRecyclerAdapter(mNotes: ArrayList<Note>, onNoteListener: OnNoteListener) :
//    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {
//    private val mNotes: ArrayList<Note> = ArrayList()
//    private val mOnNoteListener: OnNoteListener
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_note_list_item, parent, false)
//        return ViewHolder(view, mOnNoteListener)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        try {
//            var month: String = mNotes[position].getTimestamp().substring(0, 2)
//            month = Utility.getMonthFromNumber(month)
//            val year: String = mNotes[position].getTimestamp().substring(3)
//            val timestamp = "$month $year"
//            holder.timestamp.text = timestamp
//            holder.title.setText(mNotes[position].getTitle())
//        } catch (e: NullPointerException) {
//            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.message)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mNotes.size()
//    }
//
//    inner class ViewHolder(itemView: View, onNoteListener: OnNoteListener) :
//        RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        var timestamp: TextView
//        var title: TextView
//        var mOnNoteListener: OnNoteListener
//
//
//        override fun onClick(view: View) {
//            Log.d(TAG, "onClick: $adapterPosition")
//            mOnNoteListener.onNoteClick(adapterPosition)
//        }
//
//        init {
//            timestamp = itemView.findViewById(R.id.note_timestamp)
//            title = itemView.findViewById(R.id.note_title)
//            mOnNoteListener = onNoteListener
//            itemView.setOnClickListener(this)
//        }
//    }
//
//    interface OnNoteListener {
//        fun onNoteClick(position: Int)
//    }
//
//    companion object {
//        private const val TAG = "NotesRecyclerAdapter"
//    }
//
//    init {
//        this.mNotes = mNotes
//        mOnNoteListener = onNoteListener
//    }
//}