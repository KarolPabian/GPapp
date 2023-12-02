package controllers

import models.Doctor
import models.Patient
import utils.Utilities

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
        return if (Utilities.isValidListIndex(indexToDelete, patients)) {
            patients.removeAt(indexToDelete)
        } else null
    }
    fun findPatient(index: Int): Patient? {
        return if (Utilities.isValidListIndex(index, patients)) {
            patients[index]
        } else null
    }
}






