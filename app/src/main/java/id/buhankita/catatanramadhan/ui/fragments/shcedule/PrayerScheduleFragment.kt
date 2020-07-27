package id.buhankita.catatanramadhan.ui.fragments.shcedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import id.buhankita.catatanramadhan.R
import kotlinx.android.synthetic.main.fragment_prayer_schedule.*

class PrayerScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //For set Toolbar
        ((activity as AppCompatActivity)).setSupportActionBar(toolbar_schedule)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prayer_schedule, container, false)
    }
}