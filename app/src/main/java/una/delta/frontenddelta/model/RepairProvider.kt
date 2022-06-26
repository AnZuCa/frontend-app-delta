package una.delta.frontenddelta.model

class RepairProvider {
    companion object {
        fun findRepairById(id: Int) : Repair {
            return repairList[id]
        }
        fun findAllReapirs() : List<Repair> {
            return repairList
        }

        val repairList = listOf<Repair>(
            Repair(1,
                "Reparacion de cluth",
            "Andres",
            "Jose",
            "Toyota",
            "En proceso",

            ),
            Repair(2,
                "Reparacion de frenos",
                "Fabian",
                "Diego",
                "Suzuki",
                "terminado",

                ),
            Repair(3,
                "Revision general",
                "Jose",
                "Fabian",
                "Nissan",
                "En proceso",

                )
        )
    }
}