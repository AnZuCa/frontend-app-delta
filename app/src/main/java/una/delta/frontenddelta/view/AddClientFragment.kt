package una.delta.frontenddelta.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import una.delta.frontenddelta.R
import una.delta.frontenddelta.databinding.FragmentAddClientBinding
import una.delta.frontenddelta.model.UserResult
import una.delta.frontenddelta.viewmodel.NewRepairViewModel
import una.delta.frontenddelta.viewmodel.NewRepairViewModelFactory

class AddClientFragment : Fragment() {
    private var _binding: FragmentAddClientBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewRepairViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddClientBinding.inflate(inflater, container, false)

        setupActionListeners()
        listenForEvents()

        viewModel.clientDataChanged(null)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupActionListeners() {
        binding.etId.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.findClientByIdCard(binding.etId.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.btnNextStep.setOnClickListener {
            viewModel.setClient(viewModel.client.value ?: getClient())
            findNavController().navigate(R.id.action_nav_addClientFragment_to_addMechanicFragment)
        }

        binding.etId.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etName.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etLastName.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etPhoneNumber.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etAddress.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etEmailAddress.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }

        binding.etEmailAddress.afterTextChanged {
            viewModel.clientDataChanged(getClient())
        }
    }

    private fun listenForEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.errorMessage.postValue(null)
            }
        }

        viewModel.client.observe(viewLifecycleOwner) {
            when (it) {
                is UserResult -> {
                    binding.etId.setText(it.idCard)

                    binding.etName.setText(it.firstName)
                    binding.etName.isEnabled = false

                    binding.etLastName.setText(it.lastName)
                    binding.etLastName.isEnabled = false

                    binding.etPhoneNumber.setText(it.mobileNumber)
                    binding.etPhoneNumber.isEnabled = false

                    binding.etAddress.setText(it.address)
                    binding.etAddress.isEnabled = false

                    binding.etEmailAddress.setText(it.email)
                    binding.etEmailAddress.isEnabled = false
                }
                else -> {
                    binding.etName.setText("")
                    binding.etName.isEnabled = true

                    binding.etLastName.setText("")
                    binding.etLastName.isEnabled = true

                    binding.etPhoneNumber.setText("")
                    binding.etPhoneNumber.isEnabled = true

                    binding.etAddress.setText("")
                    binding.etAddress.isEnabled = true

                    binding.etEmailAddress.setText("")
                    binding.etEmailAddress.isEnabled = true
                }
            }
        }

        viewModel.clientFormState.observe(viewLifecycleOwner) {
            val formState = it ?: return@observe

            // disable next button if data is invalid
            binding.btnNextStep.isEnabled = formState.isDataValid

            when {
                formState.idCardError != null -> {
                    binding.etId.error = getString(formState.idCardError)
                }
                formState.nameError != null -> {
                    binding.etName.error = getString(formState.nameError)
                }
                formState.lastNameError != null -> {
                    binding.etLastName.error = getString(formState.lastNameError)
                }
                formState.phoneNumberError != null -> {
                    binding.etPhoneNumber.error = getString(formState.phoneNumberError)
                }
                formState.addressError != null -> {
                    binding.etAddress.error = getString(formState.addressError)
                }
                formState.emailError != null -> {
                    binding.etEmailAddress.error = getString(formState.emailError)
                }
                formState.isDataValid -> {
                    binding.etId.error = null
                    binding.etName.error = null
                    binding.etLastName.error = null
                    binding.etPhoneNumber.error = null
                    binding.etAddress.error = null
                    binding.etEmailAddress.error = null
                }
            }
        }
    }

    private fun getClient(): UserResult {
        return UserResult(
            idCard = binding.etId.text.toString(),
            firstName = binding.etName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            email = binding.etEmailAddress.text.toString(),
            enabled = true,
            tokenExpired = false,
            address = binding.etAddress.text.toString(),
            mobileNumber = binding.etPhoneNumber.text.toString(),
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
