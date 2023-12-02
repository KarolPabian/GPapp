package controllers

import models.Patient
import utils.Utilities
import utils.Utilities.isValidListIndex

class PatientAPI {

    private var patients = ArrayList<Patient>()

    fun add(patient: Patient): Boolean {
        return patients.add(patient)
    }

    fun listAllPatients(): String {
        return if (patients.isEmpty()) {
            "No patients stored"
        } else {
            var listOfPatients = ""
            for (i in patients.indices) {
                listOfPatients += "${i + 1}: ${patients[i]} \n"
            }
            listOfPatients
        }
    }

    fun numberOfPatients(): Int {
        return patients.size
    }

    fun deletePatient(indexToDelete: Int): Patient? {
        return if (isValidListIndex(indexToDelete, patients)) {
            patients.removeAt(indexToDelete)
        } else null
    }

    fun findPatient(index: Int): Patient? {
        return if (Utilities.isValidListIndex(index, patients)) {
            patients[index]
        } else null
    }

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

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, patients)
    }
}
