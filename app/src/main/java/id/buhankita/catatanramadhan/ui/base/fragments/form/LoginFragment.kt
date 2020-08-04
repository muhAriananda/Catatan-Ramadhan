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
        val email = edt_email.editText.toString()
        val password = edt_password.editText.toString()

        btn_login.setOnClickListener {
            isLoading(true)
            loginUser(email, password)
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
        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Successfully Logged In", Toast.LENGTH_LONG).show()
                updateUI()
            } else {
                isLoading(false)
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
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