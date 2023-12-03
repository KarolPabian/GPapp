import controllers.DoctorAPI
import controllers.PatientAPI
import models.Doctor
import models.Patient
import persistence.XMLSerializer
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.util.*
import kotlin.system.exitProcess


val scanner = Scanner(System.`in`)
private val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
private val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

fun main(args: Array<String>) {
    println("Healthcare Management System V1.0")
    runMenu()
}

fun mainMenu(): Int {
    print("""
         > ---------------------------------------------|
         > |        Healthcare Management System        |
         > ---------------------------------------------|
         > |               Choose Your Menu             |
         > |                                            |
         > |              1) Manage Doctors             |
         > |              2) Manage Patients            | 
         > |                                            |
         > |                                            |
         > |               88) Load All                 | 
         > |               99) Save All                 |
         > |--------------------------------------------|                
         > |   0) Exit                                  |
         > ---------------------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> runDoctorMenu()
            2 -> runPatientMenu()
            88 -> loadAll()
            99 -> saveAll()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun runDoctorMenu() {
    do {
        when (val option = doctorMenu()) {
            1 -> addDoctor()
            2 -> listDoctor()
            3 -> listDoctorsBySpecializationMenu()
            4 -> updateDoctor()
            5 -> deleteDoctor()
            6 -> assignPatientToDoctor()
            7 -> unassignPatientFromDoctor()
            8 -> listAvailableDoctorsMenu()
            99 -> saveAll()
            0 -> return
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun doctorMenu(): Int {
    print("""
         > ----------------------------------|
         > |        DOCTOR MENU              |
         > ----------------------------------|
         > |   1) Add a doctor               |
         > |   2) List all doctors           |
         > |   3) List Specialized Doctors   |
         > |   4) Update a Patient           |
         > |   5) Delete a doctor            |
         > |   6) Assign a Patient           |
         > |   7) Unassign a Patient         |
         > |   8) List Available Doctors     |
         > |  99)     Save All               |
         > |---------------------------------|                
         > |   0) Back to Main Menu          |
         > ----------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun addDoctor() {
    println("You chose Add Doctor")

    val doctorID = readNextInt("Enter the doctor's ID: ")
    val doctorName = readNextLine("Enter the doctor's name: ")

    val selectedSpecialization = DoctorInputUtils.readValidSpecialization()

    val phoneNumber = readNextLine("Enter the doctor's phone number: ")

    val newDoctor = Doctor(doctorID, doctorName, selectedSpecialization, phoneNumber)

    val isAdded = doctorAPI.add(newDoctor)

    if (isAdded) {
        println("Doctor Added Successfully")
    } else {
        println("Failed to Add Doctor")
    }
}

fun listDoctor() {
    println(doctorAPI.listAllDoctors())
}
fun listDoctorsBySpecializationMenu() {
    val specialization = DoctorInputUtils.readValidSpecialization()
    println(doctorAPI.listDoctorsBySpecialization(specialization))
}

fun listAvailableDoctorsMenu() {
    println("Available Doctors:")
    val availableDoctors = doctorAPI.listAvailableDoctors()
    if (availableDoctors.isNotEmpty()) {
        availableDoctors.forEachIndexed { index, doctor ->
            println("${index + 1}: ${doctor.name} ID: ${doctor.doctorID} - ${doctor.specialization}")
        }
    } else {
        println("No available doctors.")
    }
}





fun updateDoctor() {
    listDoctor()
    if (doctorAPI.numberOfDoctors() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Doctor to update: ")
        if (doctorAPI.isValidIndex(indexToUpdate)) {
            val updatedName = readNextLine("Enter the updated name: ")

            val updatedSpecialization = DoctorInputUtils.readValidSpecialization()

            val updatedPhoneNumber = readNextLine("Enter the updated phone number: ")

            if (doctorAPI.updateDoctor(
                    indexToUpdate,
                    Doctor(-1, updatedName, updatedSpecialization, updatedPhoneNumber)
                )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no doctors for this index number")
        }
    }
}



fun deleteDoctor() {
    listDoctor()
    if (doctorAPI.numberOfDoctors() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Doctor to delete: ")
        val doctorToDelete = doctorAPI.deleteDoctor(indexToDelete)
        if (doctorToDelete != null) {
            println("Delete Successful! Deleted Doctor: ${doctorToDelete.name} From The System")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun runPatientMenu() {
    do {
        when (val option = patientMenu()) {
            1 -> addPatient()
            2 -> listPatient()
            3 -> listPatientWaitingList()
            4 -> updatePatient()
            5 -> deletePatient()
            6 -> assignPatientToDoctor()
            7 ->unassignPatientFromDoctor()
            99 -> saveAll()
            0 -> return
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun patientMenu(): Int {
    print("""
         > ----------------------------------|
         > |       PATIENT MENU              |
         > ----------------------------------|
         > |   1) Add a patient              |
         > |   2) List all patients          |
         > |   3) Patient Waiting List       |
         > |   4) Update a patient           |
         > |   5) Delete a patient           |
         > |   6) Assign a Patient           |
         > |   7) Unassign a Patient         |
         > |  99)    Save All                |
         > |---------------------------------|                
         > |   0) Back to Main Menu          |
         > ----------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun addPatient() {
    val patientID = readNextInt("Enter the patient's ID: ")
    val patientName = readNextLine("Enter the patient's name: ")
    val dateOfBirth = readNextLine("Enter the patient's date of birth: ")
    val gender = readNextChar("Enter the patient's gender (M/F): ")
    val phoneNumber = readNextLine("Enter the patient's phone number: ")

    val isAdded = patientAPI.add(Patient(patientID, patientName, dateOfBirth, gender, phoneNumber))

    if (isAdded) {
        println("Patient Added Successfully")
    } else {
        println("Failed to Add Patient")
    }
}

fun listPatient() {
    println(patientAPI.listAllPatients())
}

fun listPatientWaitingList() {
    println("Patient Waiting List:")
    val availablePatients = patientAPI.listPatientsOnWaitingList()
    if (availablePatients.isNotEmpty()) {
        availablePatients.forEachIndexed { index, patient ->
            println("${index + 1}: ${patient.name} ID: ${patient.patientID}")
        }
    } else {
        println("No Patients On Waiting List.")
    }
}


fun updatePatient() {
    listPatient()
    if (patientAPI.numberOfPatients() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Patient to update: ")
        if (patientAPI.isValidIndex(indexToUpdate)) {
            val updatedName = readNextLine("Enter the updated name: ")
            val updatedDateOfBirth = readNextLine("Enter the updated date of birth: ")
            val updatedGender = readNextChar("Enter the updated gender (M/F): ")
            val updatedPhoneNumber = readNextLine("Enter the updated phone number: ")

            if (patientAPI.updatePatient(
                    indexToUpdate,
                    Patient(-1, updatedName, updatedDateOfBirth, updatedGender, updatedPhoneNumber)
                )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no patients for this index number")
        }
    }
}

fun deletePatient() {
    listPatient()
    if (patientAPI.numberOfPatients() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Patient to delete: ")
        val patientToDelete = patientAPI.deletePatient(indexToDelete)
        if (patientToDelete != null) {
            println("Delete Successful! Deleted Patient: ${patientToDelete.name} From The System")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun assignPatientToDoctor() {
    println("Assign Patient to Doctor")

    println("Doctors:")

    listDoctor()

    val doctorIndex = readNextInt("Enter the index of the doctor: ")

    listPatient()

    val patientIndex = readNextInt("Enter the index of the patient: ")

    val patient = patientAPI.findPatient(patientIndex)
    val doctor = doctorAPI.findDoctor(doctorIndex)

    if (patient != null && doctor != null) {

        if (doctorAPI.assignPatient(doctorIndex, patient)) {
            println("Patient assigned to the doctor successfully.")
        } else {
            println("Failed to assign patient to the doctor.")
        }
    } else {
        println("Invalid indices or patient/doctor not found.")
    }
}

fun unassignPatientFromDoctor() {
    println("Unassign Patient from Doctor")

    println("Doctors:")
    listDoctor()

    val doctorIndex = readNextInt("Enter the index of the doctor: ")

    val doctor = doctorAPI.findDoctor(doctorIndex)
    if (doctor != null && doctor.patientList.isNotEmpty()) {
        println("Patients assigned to ${doctor.name}:")
        doctor.patientList.forEachIndexed { index, patient ->
            println("${index + 1}: ${patient.name}")
        }

        val patientIndex = readNextInt("Enter the index of the patient to unassign: ")

        val patient = doctor.patientList.getOrNull(patientIndex - 1)

        if (patient != null && doctorAPI.unassignPatient(doctorIndex, patient)) {
            println("Patient unassigned from the doctor successfully.")
        } else {
            println("Failed to unassign patient from the doctor.")
        }
    } else {
        println("No patients assigned to the selected doctor.")
    }
}



@Throws(Exception::class)
fun loadAll() {
    try {
        patientAPI.load()
        doctorAPI.load()
        println("Data loaded successfully.")
    } catch (e: Exception) {
        System.err.println("Error loading data from file: $e")
    }
}

@Throws(Exception::class)
fun saveAll() {
    try {
        patientAPI.store()
        doctorAPI.store()
        println("Data saved successfully.")
    } catch (e: Exception) {
        System.err.println("Error saving data to file: $e")
    }
}

fun exitApp() {
    println("Exiting..")
    exitProcess(0)
}
