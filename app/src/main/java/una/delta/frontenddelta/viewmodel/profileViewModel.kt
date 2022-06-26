package una.delta.frontenddelta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import una.delta.frontenddelta.model.*
import una.delta.frontenddelta.repository.UserRepository
import una.delta.frontenddelta.utils.MyApplication.Companion.sessionManager


class ProfileViewModel constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val profile =  MutableLiveData<UserResult>()
    var job: Job? = null

    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    /**
     * When we call getProfile() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun getProfile() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getUserByEmail(sessionManager?.fetchUsername()!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    profile.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun saveUser(client : UserInput) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            client.id = profile.value?.id
            val response = userRepository.saveUser(
                client
            )
            withContext(Dispatchers.Main) {
                    when (response.isSuccessful) {
                        true -> {
                            loading.value = false

                            println(response.body())
                            profile.postValue(response.body())

                        }
                        false -> {
                            onError("No se pudo actualizar el usuario")
                        }
                    }
            }
        }
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