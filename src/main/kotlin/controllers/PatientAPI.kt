package controllers
import models.Patient
import persistence.Serializer
import utils.Utilities.isValidListIndex

class PatientAPI(serializerType: Serializer) {

    private var patients = ArrayList<Patient>()
    private var serializer: Serializer = serializerType

    fun add(patient: Patient): Boolean = patients.add(patient)

    fun listAllPatients(): String =
        if (patients.isEmpty()) "No patients stored"
        else patients.joinToString("\n") { "${patients.indexOf(it) + 1}: $it" }

    fun listPatientsOnWaitingList(): List<Patient> {
        return patients.filter { it.assignedDoctor == null }
    }

    fun numberOfPatients(): Int = patients.size


    fun deletePatient(indexToDelete: Int): Patient? =
        if (isValidListIndex(indexToDelete, patients)) patients.removeAt(indexToDelete)
        else null


    fun findPatient(index: Int): Patient? =
        if (isValidListIndex(index, patients)) patients[index] else null

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

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, patients)

    @Throws(Exception::class)
    fun load() {
        patients = serializer.read() as ArrayList<Patient>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(patients)
    }
}
