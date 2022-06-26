package una.delta.frontenddelta.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.R
import una.delta.frontenddelta.model.*
import una.delta.frontenddelta.repository.RepairRepository
import una.delta.frontenddelta.repository.SearchServiceRepository
import una.delta.frontenddelta.repository.UserRepository
import una.delta.frontenddelta.repository.VehicleRepository
import una.delta.frontenddelta.utils.Constants

class NewRepairViewModel constructor(
    private val repairRepository: RepairRepository,
    private val searchServiceRepository: SearchServiceRepository,
    private val vehicleRepository: VehicleRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?> = _vehicle

    private val _client = MutableLiveData<UserResult?>()
    val client: LiveData<UserResult?> = _client

    private val _mechanics = MutableLiveData<List<UserResult>>()
    val mechanics: LiveData<List<UserResult>> = _mechanics

    private val _services = MutableLiveData<List<ServiceDetails>>()
    val services: LiveData<List<ServiceDetails>> = _services

    private val _selectedMechanic = MutableLiveData<UserResult?>()
    val selectedMechanic: LiveData<UserResult?> = _selectedMechanic

    private val _selectedServices = MutableLiveData<List<ServiceDetails>>()
    val selectedServices: LiveData<List<ServiceDetails>> = _selectedServices

    private val _repairDetail = MutableLiveData<String>()

    private var job: Job? = null
    val errorMessage = MutableLiveData<String?>()
    private val loading = MutableLiveData<Boolean>()
    val wasSaved = MutableLiveData<Boolean>()

    private val _vehicleFormState = MutableLiveData<VehicleFormState>()
    val vehicleFormState : LiveData<VehicleFormState> = _vehicleFormState

    private val _clientFormState = MutableLiveData<ClientFormState>()
    val clientFormState : LiveData<ClientFormState> = _clientFormState

    fun setVehicle(vehicle: Vehicle) {
        _vehicle.value = vehicle
    }

    fun setClient(client: UserResult) {
        _client.value = client
    }

    fun setSelectedServices(indexes: List<Long>) {
        _selectedServices.value =
            _services.value?.filterIndexed { i, _ -> indexes.contains(i.toLong()) }
    }

    fun setServiceDetail(repairDetail: String) {
        _repairDetail.value = repairDetail
    }

    fun setSelectedMechanic(mechanic: UserResult?) {
        _selectedMechanic.value = mechanic
    }

    fun findVehicleByPlateNumber(plateNumber: String) {
        _vehicle.postValue(null)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = vehicleRepository.getVehicleByPlateNumber(plateNumber)

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> _vehicle.postValue(response.body())
                    false -> onError("Vehiculo no encontrado")
                }
            }
        }
    }

    fun findClientByIdCard(idCard: String) {
        _client.postValue(null)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = userRepository.getUserByIdCard(idCard)

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> _client.postValue(response.body())
                    false -> onError("Cliente no encontrado")
                }
            }
        }
    }

    fun findAllServices() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = searchServiceRepository.getAllService()

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> {
                        _services.postValue(response.body())
                        loading.value = false
                    }
                    false -> onError("Error al obtener los servicios")
                }
            }
        }
    }

    fun findAllMechanics() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getAllMechanics()

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> {
                        _mechanics.postValue(response.body())
                        loading.value = false
                    }
                    false -> onError("Error al obtener los mecanicos")
                }
            }
        }
    }

    fun saveRepair() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)

            val response = repairRepository.saveRepair(
                RepairInput(
                    description = _repairDetail.value,
                    mechanic = UserInput(_selectedMechanic.value?.id),
                    client = UserInput(
                        id = _client.value?.id,
                        idCard = _client.value?.idCard,
                        firstName = _client.value?.firstName,
                        lastName = _client.value?.lastName,
                        email = _client.value?.email,
                        address = _client.value?.address,
                        mobileNumber = _client.value?.mobileNumber,
                        enabled = _client.value?.enabled,
                        roleList = _client.value?.roleList?.toSet()
                            ?: setOf(RoleDetails(id = Constants.CLIENT_ROLE_ID)),
                    ),
                    vehicle = _vehicle.value,
                    status = StatusDetails(1), // This value should be set by default on backend
                    serviceList = _selectedServices.value?.toSet(),
                )
            )

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> {
                        loading.value = false

                        _vehicle.postValue(null)
                        _client.postValue(null)
                        _selectedServices.postValue(listOf())

                        wasSaved.value = true
                    }
                    false -> {
                        onError("No se pudo registrar la reparación")
                    }
                }
            }
        }
    }

    fun vehicleDataChanged(vehicle: Vehicle?) {
        when {
            vehicle?.plateNumber.isNullOrBlank() -> {
                _vehicleFormState.value = VehicleFormState(plateNumberError = R.string.invalid_plate_number)
            }
            vehicle?.vinNumber.isNullOrBlank() -> {
                _vehicleFormState.value = VehicleFormState(vinNumberError = R.string.invalid_vin_number)
            }
            vehicle?.vehicleBrand.isNullOrBlank() -> {
                _vehicleFormState.value = VehicleFormState(brandError = R.string.invalid_brand)
            }
            vehicle?.engineNumber.isNullOrBlank() -> {
                _vehicleFormState.value = VehicleFormState(engineNumberError = R.string.invalid_engine_number)
            }
            vehicle?.engineType.isNullOrBlank() -> {
                _vehicleFormState.value = VehicleFormState(engineTypeError = R.string.invalid_engine_type)
            }
            else -> {
                _vehicleFormState.value = VehicleFormState(isDataValid = true)
            }
        }
    }

    fun clientDataChanged(user: UserResult?) {
        when {
            user?.idCard.isNullOrBlank() -> {
                _clientFormState.value = ClientFormState(idCardError = R.string.invalid_id_card)
            }
            user?.firstName.isNullOrBlank() -> {
                _clientFormState.value = ClientFormState(nameError = R.string.invalid_name)
            }
            user?.lastName.isNullOrBlank() -> {
                _clientFormState.value = ClientFormState(lastNameError = R.string.invalid_last_name)
            }
            user?.mobileNumber.isNullOrBlank() -> {
                _clientFormState.value = ClientFormState(phoneNumberError = R.string.invalid_phone_number)
            }
            user?.address.isNullOrBlank() -> {
                _clientFormState.value = ClientFormState(addressError = R.string.invalid_address)
            }
            !isEmailValid(user?.email) -> {
                _clientFormState.value = ClientFormState(emailError = R.string.invalid_email)
            }
            else -> {
                _clientFormState.value = ClientFormState(isDataValid = true)
            }
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String?) {
        errorMessage.postValue(message)
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
