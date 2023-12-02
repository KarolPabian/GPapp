package controllers

import models.Doctor

class DoctorAPI {

    private var doctors = ArrayList<Doctor>()

    fun add(doctor: Doctor): Boolean {
        return doctors.add(doctor)
    }

    fun listAllDoctors(): String {
        return if (doctors.isEmpty()) {
            "No doctors stored"
        } else {
            var listOfDoctors = ""
            for (i in doctors.indices) {
                listOfDoctors += "${i + 1}: ${doctors[i]} \n"
            }
            listOfDoctors

        }
    }
}
