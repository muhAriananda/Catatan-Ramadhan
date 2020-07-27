package id.buhankita.catatanramadhan.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.buhankita.catatanramadhan.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //For add bottom navigation
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val navigation = findNavController(R.id.fragment_container_home)
        bottomNavigation.setupWithNavController(navigation)
    }
}