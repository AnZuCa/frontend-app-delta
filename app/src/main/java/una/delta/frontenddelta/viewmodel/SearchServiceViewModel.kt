package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.model.ServiceDetails
import una.delta.frontenddelta.model.ServiceProvider
import una.delta.frontenddelta.repository.SearchServiceRepository

class SearchServiceViewModel constructor(
    private val searchServiceRepository: SearchServiceRepository,
) : ViewModel() {
    val service =  MutableLiveData<ServiceDetails>()
    val serviceList = MutableLiveData<List<ServiceDetails>>()
    var servicesFilter = MutableLiveData<List<ServiceDetails>>()
    var job: Job? = null

    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    fun getService() {
        val position = (0..3).random()
        val _service = ServiceProvider.findServiceById(position)
        service.postValue(_service)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    /**
     * When we call findAllService() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun findAllService() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = searchServiceRepository.getAllService()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    serviceList.postValue(response.body())
                    servicesFilter.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun filterServiceList(filterExpression: String) {
        if(filterExpression.isNotEmpty())
            this.serviceList.postValue(this.servicesFilter.value!!.filter { service -> service.name!!.contains(filterExpression, ignoreCase = true) }.toMutableList())
        else
            this.serviceList.postValue(this.servicesFilter.value!!.toMutableList())
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