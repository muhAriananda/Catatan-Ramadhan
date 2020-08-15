package id.buhankita.catatanramadhan.ui.base.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R

class FormActivity : AppCompatActivity() {

    companion object {
        const val REQ_REGISTER = "req_login"
    }

    private val auth = Firebase.auth
    private val user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val isRegister = intent.getBooleanExtra(REQ_REGISTER, false)

        if (isRegister) {
            findNavController(R.id.nav_host_form).navigate(R.id.registerFragment)
        } else {
            findNavController(R.id.nav_host_form).navigate(R.id.loginFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        if (user != null) {
            updateUI()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}