package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.repository.LoginRepository
import una.delta.frontenddelta.service.LoginService
import una.delta.frontenddelta.view.LoginActivity
import java.lang.IllegalArgumentException


@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(
                loginRepository = LoginRepository(
                    loginService = LoginService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel not Found")
        }
    }

}