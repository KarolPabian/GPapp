package controllers

import models.Patient

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
}
