import controllers.DoctorAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import models.Doctor
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DoctorAPITest {

    @Test
    fun `adding a doctor to a populated list adds to ArrayList`() {

        val doctorAPI = DoctorAPI()
        val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")


        assertTrue(doctorAPI.add(newDoctor))


        assertEquals(1, doctorAPI.numberOfDoctors())
        assertEquals(newDoctor, doctorAPI.findDoctor(0))
    }

    @Test
    fun `adding a doctor to an empty list adds to ArrayList`() {

        val doctorAPI = DoctorAPI()
        val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")


        assertTrue(doctorAPI.add(newDoctor))


        assertEquals(1, doctorAPI.numberOfDoctors())
        assertEquals(newDoctor, doctorAPI.findDoctor(0))
    }
    @Test
    fun `listAllDoctors returns No Doctors Stored message when ArrayList is empty`() {

        val emptyDoctorAPI = DoctorAPI()


        assertEquals(0, emptyDoctorAPI.numberOfDoctors())
        assertTrue(emptyDoctorAPI.listAllDoctors().lowercase().contains("no doctors"))
    }

    @Test
    fun `listAllDoctors returns Doctors when ArrayList has doctors stored`() {

        val doctorAPI = DoctorAPI()
        val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
        doctorAPI.add(newDoctor)


        val doctorsString = doctorAPI.listAllDoctors().lowercase()


        assertEquals(1, doctorAPI.numberOfDoctors())
        assertTrue(doctorsString.contains("dr. smith"))
    }
    @Test
    fun `updating a doctor that does not exist returns false`() {

        val doctorAPI = DoctorAPI()


        assertFalse(
            doctorAPI.updateDoctor(
                0,
                Doctor(1, "Updating Doctor", "In-Progress", "123-456-7890")
            )
        )
    }

    @Test
    fun `updating a doctor that exists returns true and updates`() {

        val doctorAPI = DoctorAPI()
        val doctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
        doctorAPI.add(doctor)


        val updated = doctorAPI.updateDoctor(
            0,
            Doctor(1, "Updating Doctor", "In-Progress", "987-654-3210")
        )


        assertTrue(updated)
        assertEquals("Updating Doctor", doctorAPI.findDoctor(0)?.name)
        assertEquals("987-654-3210", doctorAPI.findDoctor(0)?.phoneNumber)
    }
    @Test
    fun `deleting a Doctor that does not exist returns null`() {

        val emptyDoctorAPI = DoctorAPI()


        assertNull(emptyDoctorAPI.deleteDoctor(0))
    }

    @Test
    fun `deleting a doctor that exists delete and returns deleted object`() {

        val doctorAPI = DoctorAPI()
        val doctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
        doctorAPI.add(doctor)


        val deletedDoctor = doctorAPI.deleteDoctor(0)


        assertEquals(0, doctorAPI.numberOfDoctors())
        assertEquals(doctor, deletedDoctor)
    }
}



