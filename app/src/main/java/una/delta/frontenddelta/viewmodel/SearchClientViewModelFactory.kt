package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.repository.UserRepository
import una.delta.frontenddelta.service.UserService

@Suppress("UNCHECKED_CAST")
class SearchClientViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchClientViewModel::class.java)) {
            SearchClientViewModel(
                userRepository = UserRepository(userService = UserService.getInstance())
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}