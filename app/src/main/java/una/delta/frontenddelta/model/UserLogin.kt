package una.delta.frontenddelta.model

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int?= null,
    val isDataValid: Boolean = false
)

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
data class LoginRequest(
    var username: String,
    var password: String,
)


data class LoggedInUserView (
    val username: String,
)


data class UserLoginResponse(
    var username: String,
    var password: String,
    var authorities: List<Authority>,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
)


data class Authority(
    var authorty: String,
)
