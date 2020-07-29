package id.buhankita.catatanramadhan.data.api

import id.buhankita.catatanramadhan.data.model.doa.Doa
import id.buhankita.catatanramadhan.data.model.jadwal.Salat
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("timings/{date}")
    suspend fun getPrayerSchedule(
        @Path("date") data: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int = 11
    ) : Salat

    @GET("1")
    suspend fun getDoa() : List<Doa>

}