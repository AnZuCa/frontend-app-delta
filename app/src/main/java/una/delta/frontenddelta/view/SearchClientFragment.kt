package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import una.delta.frontenddelta.adapter.SearchClientAdapter
import una.delta.frontenddelta.databinding.FragmentSearchClientBinding
import una.delta.frontenddelta.viewmodel.SearchClientViewModel
import una.delta.frontenddelta.viewmodel.SearchClientViewModelFactory

class SearchClientFragment : Fragment() {
    private var _binding: FragmentSearchClientBinding? = null
    private val binding get() = _binding!!
    private val adapter = SearchClientAdapter()
    private val viewModel: SearchClientViewModel by activityViewModels {
        SearchClientViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchClientBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setUpActionListeners()
        listenForEvents()
        loadAllClients()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.rvSearchClient
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun setUpActionListeners() {
        binding.svSearchClient.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryValue: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryValue: String?): Boolean {
                if (queryValue != null) {
                    viewModel.filterClients(queryValue)
                }
                return false
            }
        })
    }

    private fun listenForEvents() {
        viewModel.clientList.observe(viewLifecycleOwner) {
            adapter.setClientList(it)
        }
    }

    private fun loadAllClients() {
        viewModel.findAllClients()
    }
}