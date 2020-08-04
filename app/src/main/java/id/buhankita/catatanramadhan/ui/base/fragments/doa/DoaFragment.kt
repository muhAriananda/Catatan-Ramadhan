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
import id.buhankita.catatanramadhan.ui.main.viewmodel.ViewModelFactory
import id.buhankita.catatanramadhan.utils.Status.*
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_doa.*

class DoaFragment : Fragment() {

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

        //Refresh
        refreshTask()

    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitInstance.apiDoa))
        viewModel = ViewModelProvider(requireActivity(), factory)[DoaViewModel::class.java]
    }

    private fun setupDoaObserver() {
        viewModel.getAllDoa().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> {
                    swipeToRefresh.isRefreshing = false
                    resource.data?.let { doa -> mDoaAdapter.setData(doa) }
                }

                ERROR -> {
                    swipeToRefresh.isRefreshing = false
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

                LOADING -> {
                    swipeToRefresh.isRefreshing = true
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
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun refreshTask() {
        swipeToRefresh.setOnRefreshListener {
            setupDoaObserver()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}