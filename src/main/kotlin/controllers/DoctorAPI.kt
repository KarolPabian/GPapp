package controllers
import models.Doctor
import models.Patient
import persistence.Serializer
import utils.Utilities.isValidListIndex


/**
 * The `DoctorAPI` class manages operations related to doctors, such as assigning patients,
 * listing doctors, updating doctor information, and storing/retrieving data.
 *
 * @property serializerType The type of serializer used for data persistence.
 * @constructor Creates a `DoctorAPI` instance with the specified serializer.
 */
class DoctorAPI(serializerType: Serializer) {

    private var doctors = ArrayList<Doctor>()
    private var serializer: Serializer = serializerType


    /**
     * Assigns a patient to a doctor.
     *
     * @param doctorIndex The index of the doctor in the list.
     * @param patient The patient to be assigned.
     * @return `true` if the patient is assigned successfully, `false` otherwise.
     */

    fun assignPatient(doctorIndex: Int, patient: Patient): Boolean =
        if (isValidListIndex(doctorIndex, doctors)) {
            val doctor = doctors[doctorIndex]
            doctor.assignPatient(patient)
            patient.assignedDoctor = doctor
            true
        } else false


    /**
     * Unassigns a patient from a doctor.
     *
     * @param doctorIndex The index of the doctor in the list.
     * @param patient The patient to be unassigned.
     * @return `true` if the patient is unassigned successfully, `false` otherwise.
     */

    fun unassignPatient(doctorIndex: Int, patient: Patient): Boolean =
        if (isValidListIndex(doctorIndex, doctors)) {
            val doctor = doctors[doctorIndex]
            val unassigned = doctor.unassignPatient(patient)
            if (unassigned) patient.assignedDoctor = null
            unassigned
        } else false


    /**
     * Adds a new doctor to the list.
     *
     * @param doctor The doctor to be added.
     * @return `true` if the doctor is added successfully, `false` otherwise.
     */

    fun add(doctor: Doctor): Boolean = doctors.add(doctor)


    /**
     * Lists all doctors.
     *
     * @return A string representation of all doctors or a message if no doctors are stored.
     */

    fun listAllDoctors(): String =
        if (doctors.isEmpty()) "No doctors stored"
        else doctors.joinToString("\n") { "${doctors.indexOf(it)}: $it" }


    /**
     * Lists doctors with no assigned patients.
     *
     * @return A list of doctors with no assigned patients.
     */

    fun listAvailableDoctors(): List<Doctor> {
        return doctors.filter { it.patientList.isEmpty() }
    }

    /**
     * Lists doctors based on specialization.
     *
     * @param specialization The specialization to filter doctors.
     * @return A string representation of doctors with the specified specialization or a message if none are found.
     */


    fun listDoctorsBySpecialization(specialization: String): String {
        val filteredDoctors = doctors.filter { it.specialization == specialization }

        return if (filteredDoctors.isEmpty()) {
            "No doctors found for the specialization: $specialization"
        } else {
            "Here are the doctors that specialize in: $specialization\n" +
                    filteredDoctors.joinToString("\n") { "${it.doctorID}: $it" }
        }
    }

    /**
     * Returns the total number of doctors.
     *
     * @return The total number of doctors.
     */

    fun numberOfDoctors(): Int = doctors.size

    /**
     * Deletes a doctor from the list.
     *
     * @param indexToDelete The index of the doctor to delete.
     * @return The deleted doctor, or `null` if deletion is unsuccessful.
     */


    fun deleteDoctor(indexToDelete: Int): Doctor? =
        if (isValidListIndex(indexToDelete, doctors)) doctors.removeAt(indexToDelete)
        else null


    /**
     * Finds a doctor by index in the list.
     *
     * @param index The index of the doctor to find.
     * @return The found doctor, or `null` if not found.
     */


    fun findDoctor(index: Int): Doctor? =
        if (isValidListIndex(index, doctors)) doctors[index] else null


    /**
     * Updates a doctor's information.
     *
     * @param indexToUpdate The index of the doctor to update.
     * @param updatedDoctor The updated doctor information.
     * @return `true` if the update is successful, `false` otherwise.
     */


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


    /**
     * Checks if the given index is valid for the doctors list.
     *
     * @param index The index to validate.
     * @return `true` if the index is valid, `false` otherwise.
     */

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, doctors)


    /**
     * Loads doctors data from the serializer.
     *
     * @throws Exception If an error occurs during data loading.
     */

    @Throws(Exception::class)
    fun load() {
        doctors = serializer.read() as ArrayList<Doctor>
    }


    /**
     * Stores doctors data using the serializer.
     *
     * @throws Exception If an error occurs during data storage.
     */

    @Throws(Exception::class)
    fun store() {
        serializer.write(doctors)
    }
}
