package utils

import models.Doctor
import models.Patient

object Utilities {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}

