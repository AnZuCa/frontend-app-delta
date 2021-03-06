package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.model.UserResult
import una.delta.frontenddelta.repository.UserRepository

class SearchClientViewModel constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val clientList = MutableLiveData<List<UserResult>>()
    private val fullClientList = MutableLiveData<List<UserResult>>()

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    fun findAllClients() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getAllClients()

            withContext(Dispatchers.Main) {
                when (response.isSuccessful) {
                    true -> {
                        clientList.postValue(response.body())
                        fullClientList.postValue(response.body())
                        loading.postValue(false)
                    }
                    false -> {
                        onError("Error : ${response.errorBody()}")
                    }
                }
            }
        }
    }

    fun filterClients(filterValue: String) {
        when (filterValue.isNotBlank()) {
            true -> clientList.postValue(fullClientList.value?.filter {
                it.idCard?.contains(filterValue, true) ?: false ||
                        it.firstName?.contains(filterValue, true) ?: false ||
                        it.lastName?.contains(filterValue, true) ?: false
            })
            false -> clientList.postValue(fullClientList.value)
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