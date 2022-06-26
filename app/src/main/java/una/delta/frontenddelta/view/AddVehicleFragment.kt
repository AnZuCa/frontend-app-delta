package una.delta.frontenddelta.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import una.delta.frontenddelta.R
import una.delta.frontenddelta.databinding.FragmentAddVehicleBinding
import una.delta.frontenddelta.model.Vehicle
import una.delta.frontenddelta.viewmodel.NewRepairViewModel
import una.delta.frontenddelta.viewmodel.NewRepairViewModelFactory

class AddVehicleFragment : Fragment() {
    private var _binding: FragmentAddVehicleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewRepairViewModel by activityViewModels{
        NewRepairViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVehicleBinding.inflate(inflater, container, false)

        setupSpinner()
        setupActionListeners()
        listenForEvents()

        viewModel.vehicleDataChanged(null)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.vehicle_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spVehicleType.adapter = adapter
        }
    }

    private fun setupActionListeners() {
        binding.etPlateNumber.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.findVehicleByPlateNumber(binding.etPlateNumber.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.btnNextStep.setOnClickListener {
            viewModel.setVehicle(viewModel.vehicle.value ?: getVehicle())
            findNavController().navigate(R.id.action_addVehicleFragment_to_addClientFragment)
        }

        binding.etPlateNumber.afterTextChanged {
            viewModel.vehicleDataChanged(getVehicle())
        }

        binding.etVinNumber.afterTextChanged {
            viewModel.vehicleDataChanged(getVehicle())
        }

        binding.etBrand.afterTextChanged {
            viewModel.vehicleDataChanged(getVehicle())
        }

        binding.etEngineNumber.afterTextChanged {
            viewModel.vehicleDataChanged(getVehicle())
        }

        binding.etEngineType.afterTextChanged {
            viewModel.vehicleDataChanged(getVehicle())
        }
    }

    private fun listenForEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.errorMessage.postValue(null)
            }
        }

        viewModel.vehicle.observe(viewLifecycleOwner) {
            when (it) {
                is Vehicle -> {
                    binding.etPlateNumber.setText(it.plateNumber)

                    binding.etVinNumber.setText(it.vinNumber)
                    binding.etVinNumber.isEnabled = false

                    binding.etEngineNumber.setText(it.engineNumber)
                    binding.etEngineNumber.isEnabled = false

                    binding.etBrand.setText(it.vehicleBrand)
                    binding.etBrand.isEnabled = false

                    binding.spVehicleType.setSelection(
                        resources.getStringArray(R.array.vehicle_type_array).indexOf(it.vehicleType)
                    )
                    binding.spVehicleType.isEnabled = false

                    binding.etEngineType.setText(it.engineType)
                    binding.etEngineType.isEnabled = false
                }
                else -> {
                    binding.etVinNumber.setText("")
                    binding.etVinNumber.isEnabled = true

                    binding.etEngineNumber.setText("")
                    binding.etEngineNumber.isEnabled = true

                    binding.etBrand.setText("")
                    binding.etBrand.isEnabled = true

                    binding.spVehicleType.setSelection(0)
                    binding.spVehicleType.isEnabled = true

                    binding.etEngineType.setText("")
                    binding.etEngineType.isEnabled = true
                }
            }
        }

        viewModel.vehicleFormState.observe(viewLifecycleOwner) {
            val formState = it ?: return@observe

            // disable next button if data is invalid
            binding.btnNextStep.isEnabled = formState.isDataValid

            when {
                formState.plateNumberError != null -> {
                    binding.etPlateNumber.error = getString(formState.plateNumberError)
                }
                formState.vinNumberError != null -> {
                    binding.etVinNumber.error = getString(formState.vinNumberError)
                }
                formState.brandError != null -> {
                    binding.etBrand.error = getString(formState.brandError)
                }
                formState.engineNumberError != null -> {
                    binding.etEngineNumber.error = getString(formState.engineNumberError)
                }
                formState.engineTypeError != null -> {
                    binding.etEngineType.error = getString(formState.engineTypeError)
                }
                formState.isDataValid -> {
                    binding.etPlateNumber.error = null
                    binding.etVinNumber.error = null
                    binding.etBrand.error = null
                    binding.etEngineNumber.error = null
                    binding.etEngineType.error = null
                }
            }
        }
    }

    private fun getVehicle(): Vehicle {
        return Vehicle(
            plateNumber = binding.etPlateNumber.text?.toString(),
            vinNumber = binding.etVinNumber.text?.toString(),
            engineNumber = binding.etEngineNumber.text?.toString(),
            vehicleBrand = binding.etBrand.text?.toString(),
            vehicleType = binding.spVehicleType.selectedItem?.toString(),
            engineType = binding.etEngineType.text?.toString(),
        )
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
