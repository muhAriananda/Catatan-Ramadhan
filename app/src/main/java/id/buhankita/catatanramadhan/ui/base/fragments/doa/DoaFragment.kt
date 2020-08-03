package id.buhankita.catatanramadhan.ui.base.fragments.doa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.buhankita.catatanramadhan.data.api.ApiHelper
import id.buhankita.catatanramadhan.data.api.RetrofitInstance
import id.buhankita.catatanramadhan.databinding.FragmentDoaBinding
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import id.buhankita.catatanramadhan.ui.main.adapter.DoaAdapter
import id.buhankita.catatanramadhan.ui.main.viewmodel.DoaViewModel
import id.buhankita.catatanramadhan.ui.main.viewmodel.SharedViewModel
import id.buhankita.catatanramadhan.ui.main.viewmodel.ViewModelFactory
import id.buhankita.catatanramadhan.utils.Status.*
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_doa.*

class DoaFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: DoaViewModel

    private var _binding: FragmentDoaBinding? = null
    private val binding get() = _binding!!

    private val mDoaAdapter: DoaAdapter by lazy { DoaAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set Data Binding
        _binding = FragmentDoaBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Set toolbar
        setupToolbar()

        //Setup ViewModel
        setupViewModel()

        //Observer
        setupDoaObserver()

        //Set RecyclerView
        setupRecyclerView()

    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitInstance.apiDoa))
        viewModel = ViewModelProvider(requireActivity(), factory)[DoaViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun setupDoaObserver() {
        viewModel.getAllDoa().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> {
                    sharedViewModel.isDataLoading(false)
                    resource.data?.let { doa -> mDoaAdapter.setData(doa) }
                }

                ERROR -> {
                    sharedViewModel.isDataLoading(false)
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

                LOADING -> {
                    sharedViewModel.isDataLoading(true)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvDoa
        recyclerView.adapter = mDoaAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
    }

    private fun setupToolbar() {
        ((activity as HomeActivity)).apply {
            setSupportActionBar(toolbar_doa)
            supportActionBar?.title = "Doa"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}