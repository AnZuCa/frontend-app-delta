package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.repository.VehicleRepository
import una.delta.frontenddelta.service.VehicleService

@Suppress("UNCHECKED_CAST")
class SearchVehicleViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchVehicleViewModel::class.java)) {
            SearchVehicleViewModel(
                vehicleRepository = VehicleRepository(vehicleService = VehicleService.getInstance())
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
