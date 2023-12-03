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
        else patients.joinToString("\n") { "${patients.indexOf(it)}: $it" }

    fun listPatientsOnWaitingList(): List<Patient> {
        return patients.filter { it.assignedDoctor == null }
    }

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
