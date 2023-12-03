package controllers
import models.Doctor
import models.Patient
import persistence.Serializer
import utils.Utilities.isValidListIndex

class DoctorAPI(serializerType: Serializer) {

    private var doctors = ArrayList<Doctor>()
    private var serializer: Serializer = serializerType

    fun assignPatient(doctorIndex: Int, patient: Patient): Boolean {
        return if (isValidListIndex(doctorIndex, doctors)) {
            val doctor = doctors[doctorIndex]
            doctor.assignPatient(patient)
            patient.assignedDoctor = doctor
            true
        } else {
            false
        }
    }


    fun add(doctor: Doctor): Boolean {
        return doctors.add(doctor)
    }

    fun listAllDoctors(): String {
        return if (doctors.isEmpty()) {
            "No doctors stored"
        } else {
            var listOfDoctors = ""
            for (i in doctors.indices) {
                listOfDoctors += "${i + 1}: ${doctors[i]}\n"
            }
            listOfDoctors
        }
    }


    fun numberOfDoctors(): Int {
        return doctors.size
    }

    fun deleteDoctor(indexToDelete: Int): Doctor? {
        return if (isValidListIndex(indexToDelete, doctors)) {
            doctors.removeAt(indexToDelete)
        } else null
    }

    fun findDoctor(index: Int): Doctor? {
        return if (isValidListIndex(index, doctors)) {
            doctors[index]
        } else null
    }

    fun updateDoctor(indexToUpdate: Int, updatedDoctor: Doctor?): Boolean {

        val foundDoctor = findDoctor(indexToUpdate)


        if (foundDoctor != null && updatedDoctor != null) {
            foundDoctor.name = updatedDoctor.name
            foundDoctor.specialization = updatedDoctor.specialization
            foundDoctor.phoneNumber = updatedDoctor.phoneNumber
            return true
        }

        return false
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, doctors)
    }


    @Throws(Exception::class)
    fun load() {
        doctors = serializer.read() as ArrayList<Doctor>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(doctors)
    }
}
