package controllers
import models.Doctor
import models.Patient
import persistence.Serializer
import utils.Utilities.isValidListIndex

class DoctorAPI(serializerType: Serializer) {

    private var doctors = ArrayList<Doctor>()
    private var serializer: Serializer = serializerType

    fun assignPatient(doctorIndex: Int, patient: Patient): Boolean =
        if (isValidListIndex(doctorIndex, doctors)) {
            val doctor = doctors[doctorIndex]
            doctor.assignPatient(patient)
            patient.assignedDoctor = doctor
            true
        } else false

    fun unassignPatient(doctorIndex: Int, patient: Patient): Boolean =
        if (isValidListIndex(doctorIndex, doctors)) {
            val doctor = doctors[doctorIndex]
            val unassigned = doctor.unassignPatient(patient)
            if (unassigned) patient.assignedDoctor = null
            unassigned
        } else false


    fun add(doctor: Doctor): Boolean = doctors.add(doctor)

    fun listAllDoctors(): String {
        return if (doctors.isEmpty()) {
            "No doctors stored"
        } else {
            doctors.joinToString("\n") { "${doctors.indexOf(it) + 1}: $it" }
        }
    }



    fun numberOfDoctors(): Int = doctors.size

    fun deleteDoctor(indexToDelete: Int): Doctor? =
        if (isValidListIndex(indexToDelete, doctors)) doctors.removeAt(indexToDelete)
        else null

    fun findDoctor(index: Int): Doctor? =
        if (isValidListIndex(index, doctors)) doctors[index] else null

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

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, doctors)


    @Throws(Exception::class)
    fun load() {
        doctors = serializer.read() as ArrayList<Doctor>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(doctors)
    }
}
