package id.buhankita.catatanramadhan.ui.base.fragments.form

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Login Process
        btn_login.setOnClickListener {
            val email = edt_email.editText?.text.toString()
            val password = edt_password.editText?.text.toString()
            var noError = false

            if (email.isEmpty() || password.isEmpty()) {
                edt_email.error = resources.getString(R.string.error_message_field)
                edt_password.error = resources.getString(R.string.error_message_field)
            } else {
                edt_email.error = null
                edt_password.error = null
                noError = true
            }

            if (noError) {
                isLoading(true)
                loginUser(email, password)
            }

        }

        //to Register
        tv_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        //to Forget Password
        tv_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

    }

    private fun loginUser(email: String, password: String) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Successfully Logged In", Toast.LENGTH_LONG).show()
                updateUI()
            } else {
                isLoading(false)
                Log.d("TAG", task.exception.toString())
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
            btn_login.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            btn_login.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

}