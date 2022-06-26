package una.delta.frontenddelta.repository

import una.delta.frontenddelta.service.VehicleService

class VehicleRepository constructor(private val vehicleService: VehicleService) {
    suspend fun getAllVehicles() = vehicleService.getAllVehicles()

    suspend fun getVehicleByPlateNumber(plateNumber: String) =
        vehicleService.getVehicleByPlateNumber(plateNumber)
}
