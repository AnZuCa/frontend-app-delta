package una.delta.frontenddelta.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import una.delta.frontenddelta.model.Vehicle

interface VehicleService {
    @GET("v1/vehicles")
    suspend fun getVehicleByPlateNumber(@Query("plate_number") plateNumber: String): Response<Vehicle>

    @GET("v1/vehicles")
    suspend fun getAllVehicles(): Response<List<Vehicle>>

    companion object {
        private var vehicleService: VehicleService? = null

        fun getInstance(): VehicleService {
            if (vehicleService == null) {
                vehicleService = ServiceBuilder.buildService(VehicleService::class.java)
            }
            return vehicleService!!
        }
    }
}
