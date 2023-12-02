package models

data class Doctor(
    val doctorID: Int,
    var name: String,
    var specialization: String,
    var phoneNumber: String,
    var patientList: ArrayList<Patient> = ArrayList()
)
