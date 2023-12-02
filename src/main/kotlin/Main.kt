import controllers.patientAPI
import models.Patient
import java.lang.System.exit
import java.util.*


val scanner = Scanner(System.`in`)

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
         > |   1) Manage Doctors                        |
         > |   2) Manage Patients                       |
         > |--------------------------------------------|                
         > |   0) Exit                                  |
         > ---------------------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> runDoctorMenu()
            2 -> runPatientMenu()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun runDoctorMenu() {
    do {
        val option = doctorMenu()
        when (option) {
            1 -> addDoctor()
            2 -> listDoctor()
            3 -> updateDoctor()
            4 -> deleteDoctor()
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
         > |   1) Add a doctor              |
         > |   2) List all doctors          |
         > |   3) Update a doctor           |
         > |   4) Delete a doctor           |
         > |-------------------------------|                
         > |   0) Back to Main Menu         |
         > ----------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun addDoctor() {
    println("You chose Add Doctor")
}

fun listDoctor() {
    println("You chose List Doctor")
}

fun updateDoctor() {
    println("You chose Update Doctor")
}

fun deleteDoctor() {
    println("You chose Delete Doctor")
}

fun runPatientMenu() {
    do {
        val option = patientMenu()
        when (option) {
            1 -> addPatient()
            2 -> listPatient()
            3 -> updatePatient()
            4 -> deletePatient()
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
         > |   1) Add a patient             |
         > |   2) List all patients         |
         > |   3) Update a patient          |
         > |   4) Delete a patient          |
         > |-------------------------------|                
         > |   0) Back to Main Menu         |
         > ----------------------------------|
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun addPatient() {




}



fun listPatient() {
    println("You chose List Patient")
}

fun updatePatient() {
    println("You chose Update Patient")
}

fun deletePatient() {
    println("You chose Delete Patient")
}

fun exitApp() {
    println("Exiting..")
    exit(0)
}
