package id.buhankita.catatanramadhan.utils

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date()
        return formatter.format(date)
    }
}