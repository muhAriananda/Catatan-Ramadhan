package id.buhankita.catatanramadhan.ui.base.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.buhankita.catatanramadhan.R

class FormActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            updateUI()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}