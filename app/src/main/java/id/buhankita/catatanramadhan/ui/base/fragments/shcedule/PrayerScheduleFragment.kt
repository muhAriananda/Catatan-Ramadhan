package id.buhankita.catatanramadhan.ui.base.fragments.shcedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.api.ApiHelper
import id.buhankita.catatanramadhan.data.api.RetrofitInstance
import id.buhankita.catatanramadhan.ui.main.viewmodel.PrayerScheduleViewModel
import id.buhankita.catatanramadhan.ui.main.viewmodel.ViewModelFactory
import id.buhankita.catatanramadhan.utils.Status
import kotlinx.android.synthetic.main.fragment_prayer_schedule.*

class PrayerScheduleFragment : Fragment() {

    private lateinit var viewModel : PrayerScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //For set Toolbar
        ((activity as AppCompatActivity)).setSupportActionBar(toolbar_schedule)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prayer_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        viewModel.getPrayerSchedule("29-07-2020", -2.7217612, 115.2007728).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
               when(resource.status) {
                   Status.SUCCESS -> {
                       resource.data?.let { schedule -> Log.d("TAG", schedule.toString()) }
                   }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                   Status.LOADING -> { }
               }
            }
        })
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitInstance.apiSalat))
        viewModel = ViewModelProviders.of(this, factory).get(PrayerScheduleViewModel::class.java)
    }
}