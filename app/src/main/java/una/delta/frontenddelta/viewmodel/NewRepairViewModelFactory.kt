package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.repository.RepairRepository
import una.delta.frontenddelta.repository.SearchServiceRepository
import una.delta.frontenddelta.repository.UserRepository
import una.delta.frontenddelta.repository.VehicleRepository
import una.delta.frontenddelta.service.RepairService
import una.delta.frontenddelta.service.SearchServiceService
import una.delta.frontenddelta.service.UserService
import una.delta.frontenddelta.service.VehicleService

@Suppress("UNCHECKED_CAST")
class NewRepairViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewRepairViewModel::class.java)) {
            NewRepairViewModel(
                RepairRepository(RepairService.getInstance()),
                SearchServiceRepository(SearchServiceService.getInstance()),
                VehicleRepository(VehicleService.getInstance()),
                UserRepository(UserService.getInstance()),
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
