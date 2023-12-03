package utils

import java.util.*

/**
 * The `PatientInputUtils` object provides utility functions for handling patient-related inputs.
 */
object PatientInputUtils {

    /**
     * Reads and validates a character input for gender.
     *
     * @param prompt The prompt to display to the user.
     * @return The validated gender character ('M' for Male, 'F' for Female).
     */
    @JvmStatic
    fun readNextCharGender(prompt: String?): Char {
        do {
            print(prompt)
            val input = Scanner(System.`in`).next().toUpperCase().firstOrNull()

            if (input == 'M' || input == 'F') {
                return input
            } else {
                System.err.println("\tEnter 'M' for Male or 'F' for Female.")
            }
        } while (true)
    }
}
