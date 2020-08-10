package id.buhankita.catatanramadhan.ui.base.fragments.shcedule

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.api.ApiHelper
import id.buhankita.catatanramadhan.data.api.RetrofitInstance
import id.buhankita.catatanramadhan.databinding.FragmentPrayerScheduleBinding
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import id.buhankita.catatanramadhan.ui.main.viewmodel.PrayerScheduleViewModel
import id.buhankita.catatanramadhan.ui.main.viewmodel.ViewModelFactory
import id.buhankita.catatanramadhan.utils.Constant.RC_LOCATION_PERM
import id.buhankita.catatanramadhan.utils.DateHelper.getCurrentDate
import id.buhankita.catatanramadhan.utils.Status.*
import kotlinx.android.synthetic.main.fragment_prayer_schedule.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class PrayerScheduleFragment :
    Fragment(),
    EasyPermissions.PermissionCallbacks {

    private lateinit var viewModel: PrayerScheduleViewModel

    private lateinit var fuseLocationClient: FusedLocationProviderClient

    private var _binding: FragmentPrayerScheduleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data Binding
        _binding = FragmentPrayerScheduleBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //For set Toolbar
        ((activity as HomeActivity)).apply {
            setSupportActionBar(toolbar_schedule)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        //Setup Location Permission
        locationTask()

        //Setup ViewModel
        setupViewModel()

        //Setup Observer
        setupPrayerSchedule()

        //
        refreshTask()
    }

    //setup ViewModel
    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitInstance.apiSalat))
        viewModel =
            ViewModelProvider(requireActivity(), factory)[PrayerScheduleViewModel::class.java]
    }

    //Observer
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun setupPrayerSchedule() {

        val currentDate = getCurrentDate()

        fuseLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fuseLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            location?.let {
                val latitude = location.latitude
                val longitude = location.longitude

                viewModel.getPayerSchedule(currentDate, latitude, longitude)
                    .observe(viewLifecycleOwner, Observer { resource ->
                        when (resource.status) {
                            SUCCESS -> {
                                swipeToRefresh.isRefreshing = false
                                resource.data?.let { schedule ->
                                    binding.salat = schedule

                                    val day = schedule.data.date.hijri.day
                                    val month = schedule.data.date.hijri.month.en
                                    val year = schedule.data.date.hijri.year

                                    binding.tvDateHijr.text = "$day $month $year"
                                }
                            }

                            ERROR -> {
                                swipeToRefresh.isRefreshing = false
                                Toast.makeText(
                                    requireContext(),
                                    resource.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            LOADING -> {
                                swipeToRefresh.isRefreshing = true
                            }
                        }
                    })
            }
        }

    }

    //Setup permission location
    private fun hasLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun locationTask() {
        if (hasLocationPermission()) {
            return
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d("TAG", "onPermissionDenied: $requestCode : ${perms.size}")

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("TAG", "onPermissionsGranted: $requestCode : ${perms.size}")
    }

    private fun refreshTask() {
        swipeToRefresh.setOnRefreshListener {
            setupPrayerSchedule()
        }
    }

    //Destroy view
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}