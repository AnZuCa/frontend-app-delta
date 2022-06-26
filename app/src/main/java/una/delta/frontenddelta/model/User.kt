package una.delta.frontenddelta.model

import java.util.*

data class UserInput(
    var id: Long? = null,
    var idCard: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var createDate: Date? = null,
    var address: String? = null,
    var mobileNumber: String? = null,
    var password: String? = null,
    var enabled: Boolean? = null,
    var roleList: Set<RoleDetails>? = null,
)

data class UserResult(
    var id: Long? = null,
    var idCard: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var enabled: Boolean? = null,
    var tokenExpired: Boolean? = null,
    var createDate: Date? = null,
    var address: String? = null,
    var mobileNumber: String? = null,
    var roleList: List<RoleDetails>? = null,
)