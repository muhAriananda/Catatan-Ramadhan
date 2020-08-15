package id.buhankita.catatanramadhan.ui.base.fragments.note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.model.note.Note
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import id.buhankita.catatanramadhan.ui.base.activities.NoteAddUpdateActivity
import id.buhankita.catatanramadhan.utils.Constant.PATH_DATE
import id.buhankita.catatanramadhan.utils.Constant.PATH_NOTE
import id.buhankita.catatanramadhan.utils.Constant.PATH_USER
import kotlinx.android.synthetic.main.fragment_list_note.*
import kotlinx.android.synthetic.main.item_list_note.view.*


class ListNoteFragment : Fragment() {

    private val auth = Firebase.auth
    private val userId = auth.currentUser?.uid.toString()

    private val db = Firebase.firestore
    private lateinit var mAdapter: FirestoreRecyclerAdapter<Note, NoteViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setup view
        initView()

        //setup adapter
        setAdapter()
    }


    private fun initView() {

        ((activity as HomeActivity)).apply {
            setSupportActionBar(toolbar_note)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        btn_add_note.setOnClickListener {
            val intent = Intent(activity, NoteAddUpdateActivity::class.java)
            activity?.startActivity(intent)
        }

        rv_note.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setAdapter() {
        val mNoteCollection = db.collection(PATH_USER).document(userId).collection(PATH_NOTE)
        val mQuery = mNoteCollection.orderBy(PATH_DATE, Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(mQuery, Note::class.java)
            .build()

        mAdapter = object : FirestoreRecyclerAdapter<Note, NoteViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
                return NoteViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_note, parent, false)
                )
            }

            override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Note) {
                holder.bind(model)
                holder.itemView.setOnClickListener {
                    val snapshot = snapshots.getSnapshot(holder.adapterPosition)
                    val docId = snapshot.id
                    val intent = Intent(context, NoteAddUpdateActivity::class.java).apply {
                        putExtra(NoteAddUpdateActivity.EXTRA_ID, docId)
                        putExtra(NoteAddUpdateActivity.EXTRA_NOTE, model)
                    }
                    context?.startActivity(intent)
                }
            }

            override fun onDataChanged() {
                super.onDataChanged()
                if (itemCount == 0) {
                    img_empty.visibility = View.VISIBLE
                    tv_empty.visibility = View.VISIBLE

                } else {
                    img_empty.visibility = View.INVISIBLE
                    tv_empty.visibility = View.INVISIBLE
                }
            }

        }
        mAdapter.notifyDataSetChanged()
        rv_note.adapter = mAdapter
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.apply {
                tv_note_date.text = note.date
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}