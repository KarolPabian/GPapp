import models.specializationChoice
import utils.ScannerInput
import utils.Utilities

object DoctorInputUtils {

    fun readValidSpecialization(): String {
        do {
            println("Choose a specialization:")
            specializationChoice.forEachIndexed { index, specialization ->
                println("${index + 1}: $specialization")
            }

            val specializationIndex = ScannerInput.readNextInt("Enter the index of the specialization: ")

            if (Utilities.isValidListIndex(specializationIndex - 1, specializationChoice)) {
                return specializationChoice[specializationIndex - 1]
            } else {
                println("Invalid specialization index. Please choose a valid option.")
            }

        } while (true)
    }
}
