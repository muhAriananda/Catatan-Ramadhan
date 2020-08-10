package id.buhankita.catatanramadhan.data.model.note

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val date: String? = null,
    val puasa: Boolean? = null,
    val subuh: Boolean? = null,
    val dzhuhur: Boolean? = null,
    val ashar: Boolean? = null,
    val magrib: Boolean? = null,
    val isya: Boolean? = null,
    val juz: Int? = null,
    val surah: String? = null,
    val ayat: String? = null
): Parcelable