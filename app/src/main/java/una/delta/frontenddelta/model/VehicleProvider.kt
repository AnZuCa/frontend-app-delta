package una.delta.frontenddelta.model

class VehicleProvider {
    companion object {
        fun findAllVehicles(): List<Vehicle> {
            return vehicleList
        }

        fun findVehicleByPlateNumber(plateNumber: String) : Vehicle? {
            return vehicleList.find { it.plateNumber == plateNumber }
        }

        private val vehicleList = listOf(
            Vehicle(1, "871720", "906ZSQ", "AFB6701", "Mitsubishi", "Todo Terreno 4x4", "Diesel"),
            Vehicle(2, "483277", "113WIB", "ZAE9095", "Nissan", "Pickup 4x2", "Diesel"),
            Vehicle(3, "PMH726", "325GFD", "KLN8311", "Jeep", "Todo Terreno 4x4", "Gasolina"),
            Vehicle(4, "577380", "791YMA", "PVQ6179", "Toyota", "Pickup 4x4", "Gasolina"),
            Vehicle(5, "546697", "372CCX", "TXY5523", "Toyota", "Pickup 4x4", "Gasolina"),
        )
    }
}