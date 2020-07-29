package id.buhankita.catatanramadhan.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getPrayerSchedule(date: String, latitude: Double, longitude: Double) =
        apiService.getPrayerSchedule(date, latitude, longitude)

    suspend fun getDoa() = apiService.getDoa()
}