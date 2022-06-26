package una.delta.frontenddelta.repository

import una.delta.frontenddelta.model.RepairInput
import una.delta.frontenddelta.service.RepairService

class RepairRepository constructor(private val repairService: RepairService) {
    suspend fun getAllRepairs() = repairService.getAllRepairs()
    suspend fun getRepairsByMechanic(id : Long) = repairService.getRepairsByMechanic(id)
    suspend fun saveRepair(repair: RepairInput) = repairService.saveRepair(repair)
}
