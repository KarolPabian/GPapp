package models

data class Doctor(
    val doctorID: Int,
    var name: String,
    var specialization: String,
    var phoneNumber: String,
    var patientList: ArrayList<Patient> = ArrayList()
) {

    val assignedPatients: MutableList<Patient> = mutableListOf()

    override fun toString(): String {
        return """
            Doctor ID:        🩺 $doctorID
            Name:             👨‍ $name
            Specialization:   🌐 $specialization
            Phone Number:     ☎ $phoneNumber
            Assigned Patients: ${assignedPatients.joinToString { it.name }}
        """
    }
}