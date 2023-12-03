package utils

import java.util.*

object PatientInputUtils {

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
