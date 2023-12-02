import controllers.DoctorAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import models.Doctor

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
}

