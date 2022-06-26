package una.delta.frontenddelta.model

import java.util.*

data class StatusDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class RepairInput(
    var id: Long? = null,
    var description: String? = null,
    var mechanic: UserInput? = null,
    var client: UserInput? = null,
    var vehicle: Vehicle? = null,
    var registrationDate: Date? = null,
    var status: StatusDetails? = null,
    var serviceList: Set<ServiceDetails>? = null,
)

data class RepairResult(
    var id: Long,
    var description: String,
    var mechanic: UserResult,
    var client: UserResult,
    var vehicle: Vehicle,
    var status: StatusDetails,
    var serviceList: Set<ServiceDetails>,
)

data class Repair(
    var id: Long? = null,
    var description: String? = null,
    var mechanic: String? = null,
    var client: String? = null,
    var vehicle: String? = null,
    var status: String? = null
    //var serviceList: Set<ServiceDetails>? = null,
)