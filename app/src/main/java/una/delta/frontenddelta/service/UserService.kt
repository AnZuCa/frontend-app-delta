package una.delta.frontenddelta.service

import retrofit2.Response
import retrofit2.http.*
import una.delta.frontenddelta.model.UserInput
import una.delta.frontenddelta.model.UserResult

interface UserService {
    @GET("v1/users")
    suspend fun getUserByIdCard(@Query("idCard") idCard: String): Response<UserResult>

    @GET("v1/users")
    suspend fun getAllUsers(@Query("role_id") roleId: Long): Response<List<UserResult>>

    @GET("v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long) : Response<UserResult>

    @GET("v1/users")
    suspend fun getUserByEmail(@Query("email") email: String) : Response<UserResult>

    @PUT("v1/users")
    suspend fun updateUser(@Body user: UserInput): Response<UserResult>

    companion object {
        private var userService: UserService? = null
        fun getInstance(): UserService {
            if (userService == null) {
                userService = ServiceBuilder.buildService(UserService::class.java)
            }
            return userService!!
        }
    }
}