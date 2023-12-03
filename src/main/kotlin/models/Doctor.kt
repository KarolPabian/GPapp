package models

data class Doctor(
    val doctorID: Int,
    var name: String,
    var specialization: String,
    var phoneNumber: String,
    var patientList: ArrayList<Patient> = ArrayList()
) {

    override fun toString(): String {
        return """
            Doctor ID:        🩺 $doctorID
            Name:             👨‍ $name
            Specialization:   🌐 $specialization
            Phone Number:     ☎ $phoneNumber
            Patient List:     📋 $patientList
        """
    }
}