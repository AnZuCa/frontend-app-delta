package una.delta.frontenddelta.repository

import una.delta.frontenddelta.service.SearchServiceService

class SearchServiceRepository constructor(
    private val searchServiceService: SearchServiceService
){
    suspend fun getAllService() = searchServiceService.getAllServices()
}

