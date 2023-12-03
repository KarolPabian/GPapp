package controllers
import models.Patient
import persistence.Serializer
import utils.Utilities.isValidListIndex


/**
 * The `PatientAPI` class manages operations related to patients, such as adding, listing,
 * updating patient information, and storing/retrieving patient data.
 *
 * @property serializerType The type of serializer used for data persistence.
 * @constructor Creates a `PatientAPI` instance with the specified serializer.
 */

class PatientAPI(serializerType: Serializer) {

    private var patients = ArrayList<Patient>()
    private var serializer: Serializer = serializerType

    /**
     * Adds a new patient to the list.
     *
     * @param patient The patient to be added.
     * @return `true` if the patient is added successfully, `false` otherwise.
     */
    fun add(patient: Patient): Boolean = patients.add(patient)


    /**
     * Lists all patients.
     *
     * @return A string representation of all patients or a message if no patients are stored.
     */

    fun listAllPatients(): String =
        if (patients.isEmpty()) "No patients stored"
        else patients.joinToString("\n") { "${patients.indexOf(it)}: $it" }


    /**
     * Lists patients on the waiting list (without an assigned doctor).
     *
     * @return A list of patients without an assigned doctor.
     */

    fun listPatientsOnWaitingList(): List<Patient> {
        return patients.filter { it.assignedDoctor == null }
    }

    /**
     * Lists patients by gender, along with gender diversity statistics.
     *
     * @return A formatted string with male and female patient details and gender diversity percentages.
     */

    fun listPatientsByGender(): String {

        val allPatients = patients

        if (allPatients.isNotEmpty()) {
            val malePatients = allPatients.filter { it.gender.equals('M', ignoreCase = true) }
            val femalePatients = allPatients.filter { it.gender.equals('F', ignoreCase = true) }

            val malePatientsString = malePatients.joinToString("\n") { "${malePatients.indexOf(it)}: $it" }
            val femalePatientsString = femalePatients.joinToString("\n") { "${femalePatients.indexOf(it)}: $it" }

            val totalPatients = allPatients.size
            val malePercentage = (malePatients.size.toFloat() / totalPatients) * 100
            val femalePercentage = (femalePatients.size.toFloat() / totalPatients) * 100

            return """
            Male Patients:
            $malePatientsString

            Female Patients:
            $femalePatientsString

            Gender Diversity:
            Male Patients: $malePercentage%
            Female Patients: $femalePercentage%
        """.trimIndent()
        } else {
            return "No patients available."
        }
    }

    /**
     * Returns the total number of patients.
     *
     * @return The total number of patients.
     */
    fun numberOfPatients(): Int = patients.size

    /**
     * Deletes a patient from the list.
     *
     * @param indexToDelete The index of the patient to delete.
     * @return The deleted patient, or `null` if deletion is unsuccessful.
     */

    fun deletePatient(indexToDelete: Int): Patient? =
        if (isValidListIndex(indexToDelete, patients)) patients.removeAt(indexToDelete)
        else null


    /**
     * Finds a patient by index in the list.
     *
     * @param index The index of the patient to find.
     * @return The found patient, or `null` if not found.
     */

    fun findPatient(index: Int): Patient? =
        if (isValidListIndex(index, patients)) patients[index] else null

    /**
     * Updates a patient's information.
     *
     * @param indexToUpdate The index of the patient to update.
     * @param updatedPatient The updated patient information.
     * @return `true` if the update is successful, `false` otherwise.
     */


    fun updatePatient(indexToUpdate: Int, updatedPatient: Patient?): Boolean {
        val foundPatient = findPatient(indexToUpdate)

        if (foundPatient != null && updatedPatient != null) {
            foundPatient.name = updatedPatient.name
            foundPatient.dateOfBirth = updatedPatient.dateOfBirth
            foundPatient.gender = updatedPatient.gender
            foundPatient.phoneNumber = updatedPatient.phoneNumber
            return true
        }

        return false
    }

    /**
     * Checks if the given index is valid for the patients list.
     *
     * @param index The index to validate.
     * @return `true` if the index is valid, `false` otherwise.
     */

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, patients)

   /**
    * Loads patients data from the serializer.
    *
    * @throws Exception If an error occurs during data loading.
    */

    @Throws(Exception::class)
    fun load() {
        patients = serializer.read() as ArrayList<Patient>
    }

    /**
     * Stores patients data using the serializer.
     *
     * @throws Exception If an error occurs during data storage.
     */

    @Throws(Exception::class)
    fun store() {
        serializer.write(patients)
    }
}
