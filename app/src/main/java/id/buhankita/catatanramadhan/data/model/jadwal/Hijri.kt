package id.buhankita.catatanramadhan.data.model.jadwal

data class Hijri(
    val date: String,
    val day: String,
    val format: String,
    val holidays: List<Any>,
    val month: Month,
    val weekday: Weekday,
    val year: String
)