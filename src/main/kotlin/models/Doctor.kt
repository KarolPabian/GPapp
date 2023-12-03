package models

val specializationChoice = listOf("Cardiology", "Orthopedics", "Pediatrics", "Dermatology", "Neurology", "Kotlinology")
data class Doctor(
    val doctorID: Int,
    var name: String,
    var specialization: String,
    var phoneNumber: String,
    var patientList: ArrayList<Patient> = ArrayList()

) {

    fun assignPatient(patient: Patient): Boolean {
        return patientList.add(patient)
    }

    fun unassignPatient(patient: Patient): Boolean {
        return patientList.remove(patient)
    }

    override fun toString(): String {
        return """
            Doctor ID:        🩺 $doctorID
            Name:             👨‍ $name
            Specialization:   🌐 $specialization
            Phone Number:     ☎ $phoneNumber
            Patient List:     📋 ${patientList.joinToString { it.name }}
        """
    }

}

