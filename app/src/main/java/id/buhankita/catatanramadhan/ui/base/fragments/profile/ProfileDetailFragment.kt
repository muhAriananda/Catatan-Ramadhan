package id.buhankita.catatanramadhan.ui.base.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.ProfileUpdateActivity
import kotlinx.android.synthetic.main.fragment_profile_detail.*

class ProfileDetailFragment : Fragment() {

    private val auth = Firebase.auth
    private val user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

    }

    private fun initView() {

        //Sign Out
        tv_sign_out.setOnClickListener {
            showDialogLogOut()
        }

        //to Edit
        tv_edit_profile.setOnClickListener {
            val intent = Intent(activity, ProfileUpdateActivity::class.java)
            activity?.startActivity(intent)
        }

        user?.let {
            tv_profile_name.text = it.displayName
            tv_profile_email.text = it.email
        }
    }

    private fun showDialogLogOut() {
        val builder =
            AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        builder.setTitle(getString(R.string.sign_out))
        builder.setMessage("Are you sure want to Log out?")
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            auth.signOut()
            findNavController().navigate(R.id.action_fragmentProfileDetail_to_scheduleFragment)
        }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()

        user?.let {
            tv_profile_name.text = it.displayName
            tv_profile_email.text = it.email
        }
    }

}