package una.delta.frontenddelta.repository

import una.delta.frontenddelta.model.UserInput
import una.delta.frontenddelta.service.UserService
import una.delta.frontenddelta.utils.Constants

class UserRepository constructor(private val userService: UserService) {
    suspend fun getUserByIdCard(idCard: String) = userService.getUserByIdCard(idCard)
    suspend fun getAllMechanics() = userService.getAllUsers(roleId = Constants.MECHANIC_ROLE_ID)
    suspend fun getAllClients() = userService.getAllUsers(roleId = Constants.CLIENT_ROLE_ID)


    suspend fun getUserById(id : Long) = userService.getUserById(id)
    suspend fun getUserByEmail(email : String) = userService.getUserByEmail(email)
    suspend fun saveUser(user : UserInput) = userService.updateUser(user)

}