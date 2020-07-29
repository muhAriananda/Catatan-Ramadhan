package id.buhankita.catatanramadhan.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.buhankita.catatanramadhan.data.api.ApiHelper
import id.buhankita.catatanramadhan.data.repository.DoaRepository
import id.buhankita.catatanramadhan.data.repository.PrayerScheduleRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PrayerScheduleViewModel::class.java) -> {
                PrayerScheduleViewModel(PrayerScheduleRepository(apiHelper)) as T
            }

            modelClass.isAssignableFrom(DoaViewModel::class.java) -> {
                DoaViewModel(DoaRepository(apiHelper)) as T
            }

            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}