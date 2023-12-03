package models

data class Patient(
    val patientID: Int,
    var name: String,
    var dateOfBirth: String,
    var gender: Char,
    var phoneNumber: String
) {


    var assignedDoctor: Doctor? = null

    override fun toString(): String {
        return """
            Patient ID:       🏥 $patientID
            Name:             👤 $name
            Date of Birth:    📅 $dateOfBirth
            Gender:           ⚥ $gender
            Phone Number:     ☎ $phoneNumber
            Assigned Doctor:  ${assignedDoctor?.name ?: "Not assigned"}
        """
    }
}
