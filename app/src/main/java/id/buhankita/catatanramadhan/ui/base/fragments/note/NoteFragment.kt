package id.buhankita.catatanramadhan.ui.base.fragments.note

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.FormActivity
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_to_form.setOnClickListener {
            val intent = Intent(activity, FormActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            findNavController().navigate(R.id.action_noteFragment_to_listNoteFragment)
        }
    }

}