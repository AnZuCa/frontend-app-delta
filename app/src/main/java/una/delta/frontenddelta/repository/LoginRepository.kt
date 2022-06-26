package una.delta.frontenddelta.repository

import retrofit2.Response
import una.delta.frontenddelta.model.LoginRequest
import una.delta.frontenddelta.model.UserLoginResponse
import una.delta.frontenddelta.service.LoginService
import una.delta.frontenddelta.utils.MyApplication
import una.delta.frontenddelta.utils.MyApplication.Companion.sessionManager

class LoginRepository constructor(
    private val loginService: LoginService
) {

    //in-memory cache of loggedInUser object
    private var user: UserLoginResponse?=null

    val isLoggedIn: Boolean
    get() = user != null

    init{
        //if user credentials will be cached in local storage, it is recommended it be encryted
        user = null
    }

    fun logout(){
        user = null
        sessionManager?.deleteAuthToken()
    }

    suspend fun login(userLogin:LoginRequest):Response<UserLoginResponse>{
        val response = loginService.login(userLogin)
        if (response.isSuccessful){
            setLoggedInUser(response.body(),response.headers()["Authorization"].toString())
        }
        return response
    }


    private fun setLoggedInUser(loginRequest: UserLoginResponse?,token:String){
        this.user = loginRequest
        sessionManager?.saveAuthToken(token)
        sessionManager?.saveUsername(this.user!!.username)
    }

}