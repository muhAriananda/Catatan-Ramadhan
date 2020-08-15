package id.buhankita.catatanramadhan.ui.base.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.FormActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val auth = Firebase.auth
    private val user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_profile_login.setOnClickListener {
            val intent = Intent(activity, FormActivity::class.java)
            activity?.startActivity(intent)
        }

        btn_profile_register.setOnClickListener {
            val intent = Intent(activity, FormActivity::class.java)
            intent.putExtra(FormActivity.REQ_REGISTER, true)
            activity?.startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (user != null) {
            findNavController().navigate(R.id.action_profileFragment_to_fragmentProfileDetail)
        }
    }

}