package id.buhankita.catatanramadhan.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.buhankita.catatanramadhan.data.repository.PrayerScheduleRepository
import id.buhankita.catatanramadhan.utils.Resource
import kotlinx.coroutines.Dispatchers

class PrayerScheduleViewModel(private val repository: PrayerScheduleRepository) : ViewModel() {

    fun getPrayerSchedule(date: String, latitude: Double, longitude: Double) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success( data = repository.getPrayerSchedule(
                            date,
                            latitude,
                            longitude
                        )
                    )
                )
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error"))
            }
        }

}