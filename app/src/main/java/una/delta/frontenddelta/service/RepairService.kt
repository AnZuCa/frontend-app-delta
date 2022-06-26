package una.delta.frontenddelta.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import una.delta.frontenddelta.model.RepairInput
import una.delta.frontenddelta.model.RepairResult

interface RepairService {
    @GET("v1/repairs")
    suspend fun getAllRepairs(): Response<List<RepairResult>>

    @POST("v1/repairs")
    suspend fun saveRepair(@Body repair: RepairInput): Response<RepairResult>

    @GET("v1/users/{id}/repairs")
    suspend fun getRepairsByMechanic(@Path("id")id : Long): Response<List<RepairResult>>

    companion object {
        private var repairService: RepairService? = null

        fun getInstance(): RepairService {
            if (repairService == null) {
                repairService = ServiceBuilder.buildService(RepairService::class.java)
            }
            return repairService!!
        }
    }
}
