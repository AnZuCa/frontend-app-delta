package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.model.RepairResult
import una.delta.frontenddelta.repository.RepairRepository
import una.delta.frontenddelta.repository.UserRepository
import una.delta.frontenddelta.utils.MyApplication.Companion.sessionManager

class MyRepairsViewModel constructor(
    private val repairsRepository: RepairRepository,
    private val profileRepository: UserRepository,
) : ViewModel() {

    val repairList = MutableLiveData<List<RepairResult>>()

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    fun findRepairsByMechanic() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val userResponse =
                profileRepository.getUserByEmail(sessionManager?.fetchUsername() ?: "")

            withContext(Dispatchers.Main) {
                when (userResponse.isSuccessful) {
                    true -> {
                        val response =
                            repairsRepository.getRepairsByMechanic(userResponse.body()?.id!!)

                        when (response.isSuccessful) {
                            true -> {
                                repairList.postValue(response.body())
                                loading.postValue(false)
                            }
                            false -> {
                                onError("Error : ${response.errorBody()}")
                            }
                        }
                    }
                    false -> {
                        onError("Error : ${userResponse.errorBody()}")
                    }
                }
            }
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