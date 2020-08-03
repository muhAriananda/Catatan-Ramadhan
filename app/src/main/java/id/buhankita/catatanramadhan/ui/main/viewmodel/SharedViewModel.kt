package id.buhankita.catatanramadhan.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    val loadingData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun isDataLoading(state: Boolean) {
        loadingData.value = state
    }
}