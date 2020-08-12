package id.buhankita.catatanramadhan.data.model.note

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var date: String? = null,
    var puasa: Boolean = false,
    var subuh: Boolean = false,
    var dzhuhur: Boolean = false,
    var ashar: Boolean = false,
    var magrib: Boolean = false,
    var isya: Boolean = false,
    var juz: Int? = null,
    var surah: String? = null,
    var ayat: Int? = null
): Parcelable