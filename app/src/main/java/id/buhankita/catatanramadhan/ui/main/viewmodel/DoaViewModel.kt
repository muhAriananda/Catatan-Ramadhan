package id.buhankita.catatanramadhan.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.buhankita.catatanramadhan.data.repository.DoaRepository
import id.buhankita.catatanramadhan.utils.Resource
import kotlinx.coroutines.Dispatchers

class DoaViewModel(private val repository: DoaRepository): ViewModel() {

    fun getAllDoa() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getDoa()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }

}