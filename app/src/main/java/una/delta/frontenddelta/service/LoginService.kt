package una.delta.frontenddelta.service

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import una.delta.frontenddelta.model.UserResult
import una.delta.frontenddelta.model.LoginRequest
import una.delta.frontenddelta.model.UserLoginResponse

interface LoginService {

    @POST("/v1/users/login")
    suspend fun login(@Body userLogin: LoginRequest) : Response<UserLoginResponse>

    companion object{
        private var loginService : LoginService? = null
        fun getInstance() : LoginService{
            if (loginService == null) {
                loginService = ServiceBuilder.buildService(LoginService::class.java)
            }
            return loginService!!
        }
    }
}