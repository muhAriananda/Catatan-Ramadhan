package id.buhankita.catatanramadhan.ui.base.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import kotlinx.android.synthetic.main.activity_profile_update.*

class ProfileUpdateActivity : AppCompatActivity() {

    private val auth = Firebase.auth
    private val user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        supportActionBar?.apply {
            title = "Update Profile"
            setDisplayHomeAsUpEnabled(true)
        }

        btn_profile_confirm.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        isLoading(true)

        val name = edt_profile_name.editText?.text.toString()
        val password = edt_profile_password.editText?.text.toString()
        val rePassword = edt_profile_rePassword.editText?.text.toString()

        if (name.isNotEmpty() && password.isNotEmpty()) {
            updateNameUser(name)
            updatePassword(password, rePassword)

        } else if (name.isNotEmpty()) {
            updateNameUser(name)

        } else if (password.isNotEmpty()) {
            updatePassword(password, rePassword)
        }

    }

    private fun updatePassword(password: String, rePassword: String) {
        if (password.isEmpty() || rePassword.isEmpty()) {
            edt_profile_password.editText?.error = getString(R.string.error_message_field)
            edt_profile_rePassword.editText?.error = getString(R.string.error_message_field)

        } else if (password == rePassword) {
            edt_profile_rePassword.editText?.error = getString(R.string.error_different_field)

        } else {
            user?.updatePassword(password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Update", Toast.LENGTH_LONG).show()
                    } else {
                        isLoading(false)
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun updateNameUser(name: String) {
        val user = auth.currentUser

        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user!!.updateProfile(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully Update", Toast.LENGTH_LONG).show()
                finish()
            } else {
                isLoading(false)
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            btn_profile_confirm.visibility = View.INVISIBLE
            progressBar4.visibility = View.VISIBLE
        } else {
            btn_profile_confirm.visibility = View.VISIBLE
            progressBar4.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}