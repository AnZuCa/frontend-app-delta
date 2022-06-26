package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.model.Vehicle
import una.delta.frontenddelta.repository.VehicleRepository

class SearchVehicleViewModel constructor(
    private val vehicleRepository: VehicleRepository,
) : ViewModel() {

    val vehicleList = MutableLiveData<List<Vehicle>>()
    private val fullVehicleList = MutableLiveData<List<Vehicle>>()

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    fun findAllVehicles() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = vehicleRepository.getAllVehicles()

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> {
                        vehicleList.postValue(response.body())
                        fullVehicleList.postValue(response.body())
                        loading.postValue(false)
                    }
                    false -> {
                        onError("Error : ${response.errorBody()}")
                    }
                }
            }
        }
    }

    fun filterVehicles(filterValue: String) {
        when (filterValue.isNotBlank()) {
            true -> vehicleList.postValue(fullVehicleList.value?.filter {
                it.plateNumber!!.contains(filterValue, true)
            })
            false -> vehicleList.postValue(fullVehicleList.value)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
