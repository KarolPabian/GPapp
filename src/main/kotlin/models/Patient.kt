package models

data class Patient(
    val patientID: Int,
    var name: String,
    var dateOfBirth: String,
    var gender: Char,
    var phoneNumber: String
) {

    override fun toString(): String {
        return """
            Patient ID:       👤 $patientID
            Name:             👨 $name
            Date of Birth:    📅 $dateOfBirth
            Gender:           ⚥ $gender
            Phone Number:     ☎️ $phoneNumber
        """
    }
}