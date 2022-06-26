package una.delta.frontenddelta.model

import java.util.*

class ServiceProvider {
    companion object {
        fun findServiceById(id: Int) : ServiceDetails {
            return serviceList[id]
        }

        fun findAllService() : List<ServiceDetails> {
            return serviceList
        }

        val serviceList = listOf<ServiceDetails>(
            ServiceDetails(
                1,
                "Cambio de aceite",
                "Cambio de aceite"
            ),
            ServiceDetails(
                2,
                "Cambio de motor",
                "Cambio de motor"
            ),
            ServiceDetails(
                3,
                "Cambio de pintura",
                "Cambio de pintura"
            ),
            ServiceDetails(
                4,
                "Cambio de llantas",
                "Cambio de llantas"
            )
        )
    }

}