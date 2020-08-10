package id.buhankita.catatanramadhan.ui.base.fragments.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //send request
        btn_send_request.setOnClickListener {
            val email = edt_email_forgot.editText.toString()

            if (email.isEmpty()) {
                edt_email_forgot.error = resources.getString(R.string.error_message_field)
            } else {
                edt_email_forgot.error = null
                sendRequestEmail(email)
            }
        }

        //to login
        tv_back.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
    }

    private fun sendRequestEmail(email: String) {
        auth = Firebase.auth

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Reset link sent in your email", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Unable to send reset mail", Toast.LENGTH_LONG).show()
            }
        }
    }

}