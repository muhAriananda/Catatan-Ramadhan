package id.buhankita.catatanramadhan.ui.base.fragments.doa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.databinding.FragmentDetailDoaBinding
import id.buhankita.catatanramadhan.ui.base.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_detail_doa.*

class DoaDetailFragment : Fragment() {

    private val arg: DoaDetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailDoaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data binding
        _binding = FragmentDetailDoaBinding.inflate(inflater, container, false)
        binding.doa = arg.DoaItem

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()

    }

    private fun setupToolbar() {
        ((requireActivity() as HomeActivity)).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "Doa"
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_fragmentDetailDoa_to_doaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}