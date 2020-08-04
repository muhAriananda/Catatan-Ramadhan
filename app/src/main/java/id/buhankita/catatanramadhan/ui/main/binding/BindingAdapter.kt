package id.buhankita.catatanramadhan.ui.main.binding

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import id.buhankita.catatanramadhan.data.model.doa.Doa
import id.buhankita.catatanramadhan.ui.base.fragments.doa.DoaFragmentDirections

class BindingAdapter {

    companion object {

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