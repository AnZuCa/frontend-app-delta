package una.delta.frontenddelta.service

import retrofit2.Response
import retrofit2.http.GET
import una.delta.frontenddelta.model.ServiceDetails

interface SearchServiceService {
    @GET("v1/services")
    suspend fun getAllServices() : Response<List<ServiceDetails>>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        var searchServiceService : SearchServiceService? = null
        fun getInstance() : SearchServiceService {
            if (searchServiceService == null) {
                searchServiceService = ServiceBuilder.buildService(SearchServiceService::class.java)
            }
            return searchServiceService!!
        }
    }
}