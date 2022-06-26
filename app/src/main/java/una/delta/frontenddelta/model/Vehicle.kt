package una.delta.frontenddelta.model

data class Vehicle(
    var id: Long? = null,
    var plateNumber: String? = null,
    var vinNumber: String? = null,
    var engineNumber: String? = null,
    var vehicleBrand: String? = null,
    var vehicleType: String? = null,
    var engineType: String? = null
)
