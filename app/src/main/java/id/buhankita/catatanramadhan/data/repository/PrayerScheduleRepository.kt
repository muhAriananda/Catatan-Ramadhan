package id.buhankita.catatanramadhan.data.repository

import id.buhankita.catatanramadhan.data.api.ApiHelper

class PrayerScheduleRepository(private val apiHelper: ApiHelper) {

    suspend fun getPrayerSchedule(date: String, latitude: Double, longitude: Double) =
        apiHelper.getPrayerSchedule(date, latitude, longitude)
}