package una.delta.frontenddelta.model

data class VehicleFormState(
    val plateNumberError: Int? = null,
    val vinNumberError: Int? = null,
    val brandError: Int? = null,
    val engineNumberError: Int? = null,
    val vehicleTypeError: Int? = null,
    val engineTypeError: Int? = null,
    val isDataValid: Boolean = false
)

data class ClientFormState(
    val idCardError: Int? = null,
    val nameError: Int? = null,
    val lastNameError: Int? = null,
    val phoneNumberError: Int? = null,
    val addressError: Int? = null,
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)
