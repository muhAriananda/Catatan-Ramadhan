package id.buhankita.catatanramadhan.ui.base.fragments.prayer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.api.ApiHelper
import id.buhankita.catatanramadhan.data.api.RetrofitInstance
import id.buhankita.catatanramadhan.ui.main.viewmodel.DoaViewModel
import id.buhankita.catatanramadhan.ui.main.viewmodel.ViewModelFactory
import id.buhankita.catatanramadhan.utils.Status

class DoaFragment : Fragment() {

    private lateinit var viewModel: DoaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        viewModel.getAllDoa().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { doa -> Log.d("TAG", doa.toString()) }
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(),resource.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {}
                }
            }
        })
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitInstance.apiDoa))
        viewModel = ViewModelProviders.of(this, factory).get(DoaViewModel::class.java)
    }

}