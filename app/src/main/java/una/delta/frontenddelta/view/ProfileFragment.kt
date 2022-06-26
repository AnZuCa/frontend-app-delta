package una.delta.frontenddelta.view

import androidx.activity.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import una.delta.frontenddelta.R
import una.delta.frontenddelta.databinding.FragmentProfileBinding
import una.delta.frontenddelta.viewmodel.ProfileViewModel
import una.delta.frontenddelta.viewmodel.ProfileViewModelFactory
import java.text.SimpleDateFormat;
import una.delta.frontenddelta.model.UserInput

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment()  {

    private lateinit  var _binding: FragmentProfileBinding
    private val  profileViewModel: ProfileViewModel by activityViewModels(){
        ProfileViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentProfileBinding.inflate(layoutInflater)
        _binding.btnSaveProfile.setOnClickListener {
            saveUser()
        }



        profileViewModel.profile.observe(viewLifecycleOwner, Observer {
            _binding.profileTextName.setText(it.firstName)
            _binding.profileTextLastNames.setText(it.lastName)
            _binding.profileTextAddress .setText(it.address)
            _binding.profileTextEmailAddress.setText(it.email)
            _binding.profileTextMobileNumber.setText(it.mobileNumber)
            _binding.profileTextCreateDate.setText(SimpleDateFormat("dd/MM/yyyy").format(it.createDate))
            _binding.profileTextID.setText(it.idCard.toString())
        })


        profileViewModel.getProfile()
        return _binding.root
    }

    private fun saveUser() {
        val client = UserInput(
            id= 0,
            idCard = _binding.profileTextID.text.toString(),
            firstName = _binding.profileTextName.text.toString(),
            lastName =_binding.profileTextLastNames.text.toString(),
            email =_binding.profileTextEmailAddress.text.toString(),
            address =_binding.profileTextAddress.text.toString(),
            mobileNumber =_binding.profileTextMobileNumber.text.toString(),

        )
        profileViewModel.saveUser(client)
        Toast.makeText(activity, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
    }


}