package id.buhankita.catatanramadhan.data.model.doa

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doa(
    val arab: String,
    val arti: String,
    val id: Int,
    val latin: String,
    val nama: String
): Parcelable