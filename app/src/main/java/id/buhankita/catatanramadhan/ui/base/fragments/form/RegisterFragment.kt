package id.buhankita.catatanramadhan.ui.base.fragments.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        auth = Firebase.auth

        //Register Proses
        btn_register.setOnClickListener {
            val name = edt_name.editText?.text.toString()
            val email = edt_email_register.editText?.text.toString()
            val password = edt_password_register.editText?.text.toString()
            var noError = false

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                edt_name.error = resources.getString(R.string.error_message_field)
                edt_email_register.error = resources.getString(R.string.error_message_field)
                edt_password_register.error = resources.getString(R.string.error_message_field)
            } else {
                edt_name.error = null
                edt_email_register.error = null
                edt_password_register.error = null
                noError = true
            }

            if (noError) {
                isLoading(true)
                registerUser(name, email, password)
            }
        }

        //to Login
        tv_login.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun registerUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUser(name)
            } else {
                isLoading(false)
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUser(name: String) {
        val user = auth.currentUser

        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user!!.updateProfile(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Registration Successfully", Toast.LENGTH_LONG).show()
                updateUI()
            } else {
                isLoading(false)
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUI() {
        val intent = Intent(activity, HomeActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            btn_register.visibility = View.INVISIBLE
            progressBar3.visibility = View.VISIBLE
        } else {
            btn_register.visibility = View.VISIBLE
            progressBar3.visibility = View.INVISIBLE
        }
    }

}