package id.buhankita.catatanramadhan.data.repository

import id.buhankita.catatanramadhan.data.api.ApiHelper

class DoaRepository(private val apiHelper: ApiHelper) {

    suspend fun getDoa() = apiHelper.getDoa()

}