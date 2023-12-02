package controllers

import models.Patient

class PatientAPI {

    private var patients = ArrayList<Patient>()

    fun add(patient: Patient): Boolean {
        return patients.add(patient)
    }
}
