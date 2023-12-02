package controllers

import models.Doctor

class DoctorAPI {

    private var doctors = ArrayList<Doctor>()

    fun add(doctor: Doctor): Boolean {
        return doctors.add(doctor)
    }
}
