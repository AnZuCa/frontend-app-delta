package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import una.delta.frontenddelta.R
import una.delta.frontenddelta.adapter.AddMechanicAdapter
import una.delta.frontenddelta.databinding.FragmentAddMechanicBinding
import una.delta.frontenddelta.viewmodel.NewRepairViewModel
import una.delta.frontenddelta.viewmodel.NewRepairViewModelFactory

class AddMechanicFragment : Fragment() {
    private var _binding: FragmentAddMechanicBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewRepairViewModel by activityViewModels()
    private val adapter = AddMechanicAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMechanicBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setupActionListeners()
        listenForEvents()
        loadAllMechanics()

        viewModel.setSelectedMechanic(null)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.rvAddMechanic
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.onItemClick = { mechanic ->
            viewModel.setSelectedMechanic(mechanic)
        }
    }

    private fun setupActionListeners() {
        binding.btnNextStep.setOnClickListener {
            findNavController().navigate(R.id.action_addMechanicFragment_to_addServicesFragment)
        }
    }

    private fun listenForEvents() {
        viewModel.mechanics.observe(viewLifecycleOwner) {
            adapter.setMechanicList(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.errorMessage.postValue(null)
            }
        }

        viewModel.selectedMechanic.observe(viewLifecycleOwner) {
            binding.btnNextStep.isEnabled = it != null
        }
    }

    private fun loadAllMechanics() {
        viewModel.findAllMechanics()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
