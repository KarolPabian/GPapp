package models

data class Patient(
    val patientID: Int,
    var name: String,
    var dateOfBirth: String,
    var gender: Char,
    var phoneNumber: String
)