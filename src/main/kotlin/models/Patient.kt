package models

/**
 * The `Patient` class represents an individual receiving healthcare, with information
 * such as ID, name, date of birth, gender, phone number, and an assigned doctor.
 *
 * @property patientID The unique identifier for the patient.
 * @property name The name of the patient.
 * @property dateOfBirth The date of birth of the patient.
 * @property gender The gender of the patient ('M' for male, 'F' for female).
 * @property phoneNumber The phone number of the patient.
 * @property assignedDoctor The doctor assigned to the patient, or `null` if not assigned.
 * @constructor Creates a `Patient` instance with the specified properties.
 */
data class Patient(
    val patientID: Int,
    var name: String,
    var dateOfBirth: String,
    var gender: Char,
    var phoneNumber: String
) {

    /**
     * The doctor assigned to the patient, or `null` if not assigned.
     */
    var assignedDoctor: Doctor? = null

    /**
     * Provides a string representation of the patient, including ID, name, date of birth,
     * gender, phone number, and the assigned doctor's name (or "Not assigned" if no doctor
     * is assigned).
     *
     * @return A formatted string representing the patient.
     */
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
