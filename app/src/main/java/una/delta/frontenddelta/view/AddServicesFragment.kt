package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import una.delta.frontenddelta.R
import una.delta.frontenddelta.adapter.MyItemDetailsLookup
import una.delta.frontenddelta.adapter.ServiceAdapter
import una.delta.frontenddelta.databinding.FragmentAddServicesBinding
import una.delta.frontenddelta.model.ServiceDetails
import una.delta.frontenddelta.viewmodel.NewRepairViewModel
import una.delta.frontenddelta.viewmodel.NewRepairViewModelFactory

class AddServicesFragment : Fragment() {
    private var _binding: FragmentAddServicesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewRepairViewModel by activityViewModels()

    private val adapter = ServiceAdapter()
    private var tracker: SelectionTracker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddServicesBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setupActionListeners()
        listenForEvents()
        loadAllServices()

        viewModel.setSelectedServices(tracker?.selection?.toList()!!)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupActionListeners() {
        binding.btnNextStep.setOnClickListener {
            viewModel.setServiceDetail(binding.etAddServicesDetails.text.toString())
            viewModel.saveRepair()
        }
    }

    private fun listenForEvents() {
        viewModel.services.observe(viewLifecycleOwner) {
            adapter.setServiceList(it)
        }

        viewModel.wasSaved.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.wasSaved.postValue(false)
                showMessage("Reparación guardada correctamente")
                findNavController().navigate(R.id.action_nav_addServicesFragment_to_mainFragment)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.errorMessage.postValue(null)
            }
        }

        viewModel.selectedServices.observe(viewLifecycleOwner) {
            binding.btnNextStep.isEnabled = it != null && it.isNotEmpty()
        }
    }

    private fun loadAllServices() {
        viewModel.findAllServices()
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        tracker = SelectionTracker.Builder(
            "mySelection",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            MyItemDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    binding.tvNumberOfServicesDetail.text = tracker?.selection?.size().toString()
                    viewModel.setSelectedServices(tracker?.selection?.toList()!!)
                }
            })

        adapter.tracker = tracker
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}