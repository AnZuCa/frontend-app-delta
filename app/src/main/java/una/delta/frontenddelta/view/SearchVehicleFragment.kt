package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.adapter.SearchVehicleAdapter
import una.delta.frontenddelta.databinding.FragmentSearchVehicleBinding
import una.delta.frontenddelta.viewmodel.SearchVehicleViewModel
import una.delta.frontenddelta.viewmodel.SearchVehicleViewModelFactory

class SearchVehicleFragment : Fragment() {
    private var _binding: FragmentSearchVehicleBinding? = null
    private val binding get() = _binding!!
    private val adapter = SearchVehicleAdapter()
    private val viewModel: SearchVehicleViewModel by activityViewModels {
        SearchVehicleViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchVehicleBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setUpActionListeners()
        listenForEvents()
        loadAllVehicles()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = binding.rvSearchVehicle
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun setUpActionListeners() {
        binding.svSearchVehicle.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryValue: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryValue: String?): Boolean {
                if (queryValue != null) {
                    viewModel.filterVehicles(queryValue)
                }
                return false
            }
        })
    }

    private fun listenForEvents() {
        viewModel.vehicleList.observe(viewLifecycleOwner) {
            adapter.setVehicleList(it)
        }
    }

    private fun loadAllVehicles() {
        viewModel.findAllVehicles()
    }
}
