package id.buhankita.catatanramadhan.ui.main.binding

import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import id.buhankita.catatanramadhan.data.model.doa.Doa
import id.buhankita.catatanramadhan.ui.base.fragments.doa.DoaFragmentDirections

class BindingAdapter {

    companion object {

        @BindingAdapter("android:isLoading")
        @JvmStatic
        fun isLoading(view: View, isLoading: MutableLiveData<Boolean>) {
            when(isLoading.value) {
                false ->  view.visibility = View.INVISIBLE
                true -> view.visibility = View.VISIBLE
            }
        }

        @BindingAdapter("android:showItem")
        @JvmStatic
        fun showItem(view: View, isLoading: MutableLiveData<Boolean>) {
            when(isLoading.value) {
                false ->  view.visibility = View.VISIBLE
                true -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:sendDataDetailDoa")
        @JvmStatic
        fun sendDataDetailDoa(view: CardView, doa: Doa) {
            view.setOnClickListener {
                val action = DoaFragmentDirections.actionDoaFragmentToFragmentDetailDoa(doa)
                view.findNavController().navigate(action)
            }
        }

    }
}