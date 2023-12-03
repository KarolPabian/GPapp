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
            5 -> assignPatientToDoctor()
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
            3 -> updateDoctor()
            4 -> deleteDoctor()
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
         > |   3) Update a doctor            |
         > |   4) Delete a doctor            |
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
    val specialization = readNextLine("Enter the doctor's specialization: ")
    val phoneNumber = readNextLine("Enter the doctor's phone number: ")

    val newDoctor = Doctor(doctorID, doctorName, specialization, phoneNumber)

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

fun updateDoctor() {
    listDoctor()
    if (doctorAPI.numberOfDoctors() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Doctor to update: ")
        if (doctorAPI.isValidIndex(indexToUpdate)) {
            val updatedName = readNextLine("Enter the updated name: ")
            val updatedSpecialization = readNextLine("Enter the updated specialization: ")
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
            3 -> updatePatient()
            4 -> deletePatient()
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
         > |   3) Update a patient           |
         > |   4) Delete a patient           |
         > |  99)    Save All
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

    // Display doctors
    println("Doctors:")
    listDoctor()

    // Get user input for doctor index
    val doctorIndex = readNextInt("Enter the index of the doctor: ")

    // Display patients
    listPatient()

    // Get user input for patient index
    val patientIndex = readNextInt("Enter the index of the patient: ")

    // Retrieve the patient and doctor
    val patient = patientAPI.findPatient(patientIndex)
    val doctor = doctorAPI.findDoctor(doctorIndex)

    // Check if both patient and doctor are not null before assigning
    if (patient != null && doctor != null) {
        // Assign the patient to the doctor
        if (doctorAPI.assignPatient(doctorIndex, patient)) {
            println("Patient assigned to the doctor successfully.")
        } else {
            println("Failed to assign patient to the doctor.")
        }
    } else {
        println("Invalid indices or patient/doctor not found.")
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
