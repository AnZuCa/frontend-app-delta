package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.repository.SearchServiceRepository
import una.delta.frontenddelta.service.SearchServiceService
/**
 * if we need to pass some input data to the constructor of the viewModel,
 * we need to create a factory class for viewModel.
 */
@Suppress("UNCHECKED_CAST")
class SearchServiceViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchServiceViewModel::class.java)) {
            SearchServiceViewModel(
                searchServiceRepository = SearchServiceRepository(
                    searchServiceService = SearchServiceService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}