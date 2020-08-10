package id.buhankita.catatanramadhan.ui.base.fragments.note

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.NoteAddUpdateActivity
import kotlinx.android.synthetic.main.fragment_list_note.*


class ListNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_add_note.setOnClickListener {
            val intent = Intent(activity, NoteAddUpdateActivity::class.java)
            activity?.startActivity(intent)
        }

    }

}