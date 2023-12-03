package models

/**
 * The `Doctor` class represents a healthcare professional with information such as ID, name,
 * specialization, phone number, and a list of assigned patients.
 *
 * @property doctorID The unique identifier for the doctor.
 * @property name The name of the doctor.
 * @property specialization The medical specialization of the doctor.
 * @property phoneNumber The phone number of the doctor.
 * @property patientList The list of patients assigned to the doctor.
 * @constructor Creates a `Doctor` instance with the specified properties.
 *
 */

val specializationChoice = listOf("Cardiology", "Orthopedics", "Pediatrics", "Dermatology", "Neurology", "Kotlinology")
data class Doctor(
    val doctorID: Int,
    var name: String,
    var specialization: String,
    var phoneNumber: String,
    var patientList: ArrayList<Patient> = ArrayList()
) {

    /**
     * Assigns a patient to the doctor.
     *
     * @param patient The patient to be assigned.
     * @return `true` if the patient is assigned successfully, `false` otherwise.
     */
    fun assignPatient(patient: Patient): Boolean {
        return patientList.add(patient)
    }

    /**
     * Unassigns a patient from the doctor.
     *
     * @param patient The patient to be unassigned.
     * @return `true` if the patient is unassigned successfully, `false` otherwise.
     */
    fun unassignPatient(patient: Patient): Boolean {
        return patientList.remove(patient)
    }

    /**
     * Provides a string representation of the doctor, including ID, name, specialization,
     * phone number, and the list of assigned patients.
     *
     * @return A formatted string representing the doctor.
     */
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
