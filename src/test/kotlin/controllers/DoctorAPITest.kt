import controllers.DoctorAPI
import controllers.PatientAPI
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import models.Doctor
import models.Patient
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DoctorAPITest {


    private lateinit var populatedDoctors: DoctorAPI
    private lateinit var emptyDoctors: DoctorAPI

    @BeforeEach
    fun setup() {
        val xmlSerializer = XMLSerializer(File("doctors.xml"))
        populatedDoctors = DoctorAPI(xmlSerializer)
        emptyDoctors = DoctorAPI(xmlSerializer)
    }
    @Nested
    inner class AddDoctors {

        @Test
        fun `adding a doctor to a populated list adds to ArrayList`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))
            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")

            assertTrue(doctorAPI.add(newDoctor))

            assertEquals(1, doctorAPI.numberOfDoctors())
            assertEquals(newDoctor, doctorAPI.findDoctor(0))
        }

        @Test
        fun `adding a doctor to an empty list adds to ArrayList`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))
            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")

            assertTrue(doctorAPI.add(newDoctor))

            assertEquals(1, doctorAPI.numberOfDoctors())
            assertEquals(newDoctor, doctorAPI.findDoctor(0))
        }
    }

    @Nested
    inner class ListDoctors {

        @Test
        fun `listAllDoctors returns No Doctors Stored message when ArrayList is empty`() {
            val emptyDoctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertEquals(0, emptyDoctorAPI.numberOfDoctors())
            assertTrue(emptyDoctorAPI.listAllDoctors().lowercase().contains("no doctors"))
        }

        @Test
        fun `listAllDoctors returns Doctors when ArrayList has doctors stored`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))
            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
            doctorAPI.add(newDoctor)

            val doctorsString = doctorAPI.listAllDoctors().lowercase()

            assertEquals(1, doctorAPI.numberOfDoctors())
            assertTrue(doctorsString.contains("dr. smith"))
        }
    }

    @Nested
    inner class UpdateDoctors {

        @Test
        fun `updating a doctor that does not exist returns false`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertFalse(
                doctorAPI.updateDoctor(
                    0,
                    Doctor(1, "Updating Doctor", "In-Progress", "123-456-7890")
                )
            )
        }

        @Test
        fun `updating a doctor that exists returns true and updates`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))
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
    }

    @Nested
    inner class DeleteDoctors {

        @Test
        fun `deleting a Doctor that does not exist returns null`() {
            val emptyDoctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertNull(emptyDoctorAPI.deleteDoctor(0))
        }

        @Test
        fun `deleting a doctor that exists delete and returns deleted object`() {
            val doctorAPI =  DoctorAPI(XMLSerializer(File("doctors.xml")))
            val doctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
            doctorAPI.add(doctor)

            val deletedDoctor = doctorAPI.deleteDoctor(0)

            assertEquals(0, doctorAPI.numberOfDoctors())
            assertEquals(doctor, deletedDoctor)
        }
    }

    @Nested
    inner class FindDoctor {

        @Test
        fun `finding a doctor by index returns the correct doctor`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))
            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
            doctorAPI.add(newDoctor)

            val foundDoctor = doctorAPI.findDoctor(0)

            assertNotNull(foundDoctor)
            assertEquals(newDoctor, foundDoctor)
        }

        @Test
        fun `finding a doctor by invalid index returns null`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

            val foundDoctor = doctorAPI.findDoctor(0)

            assertNull(foundDoctor)
        }
    }

    @Nested
    inner class AssignPatientToDoctor {

        @Test
        fun `assigning a patient to a doctor returns true`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            doctorAPI.add(newDoctor)
            patientAPI.add(newPatient)

            val assigned = doctorAPI.assignPatient(0, newPatient)

            assertTrue(assigned)
        }

        @Test
        fun `assigning a patient to a non-existing doctor returns false`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")
            patientAPI.add(newPatient)

            val assigned = doctorAPI.assignPatient(0, newPatient)

            assertFalse(assigned)
        }

        @Test
        fun `assigning a patient to a doctor with invalid index returns false`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            val newDoctor = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            doctorAPI.add(newDoctor)
            patientAPI.add(newPatient)

            val assigned = doctorAPI.assignPatient(1, newPatient)

            assertFalse(assigned)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingDoctors = DoctorAPI(XMLSerializer(File("doctors.xml")))
            storingDoctors.store()


            val loadedDoctors = DoctorAPI(XMLSerializer(File("doctors.xml")))
            loadedDoctors.load()


            assertEquals(0, storingDoctors.numberOfDoctors())
            assertEquals(0, loadedDoctors.numberOfDoctors())
            assertEquals(storingDoctors.numberOfDoctors(), loadedDoctors.numberOfDoctors())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {
            // Storing 3 doctors to the doctors.xml file.
            val storingDoctors = DoctorAPI(XMLSerializer(File("doctors.xml")))
            storingDoctors.add(Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890"))
            storingDoctors.add(Doctor(2, "Dr. Johnson", "Pediatrics", "987-654-3210"))
            storingDoctors.add(Doctor(3, "Dr. White", "Dermatology", "555-555-5555"))
            storingDoctors.store()


            val loadedDoctors = DoctorAPI(XMLSerializer(File("doctors.xml")))
            loadedDoctors.load()


            assertEquals(3, storingDoctors.numberOfDoctors())
            assertEquals(3, loadedDoctors.numberOfDoctors())
            assertEquals(storingDoctors.numberOfDoctors(), loadedDoctors.numberOfDoctors())


            assertEquals(storingDoctors.findDoctor(0), loadedDoctors.findDoctor(0))
            assertEquals(storingDoctors.findDoctor(1), loadedDoctors.findDoctor(1))
            assertEquals(storingDoctors.findDoctor(2), loadedDoctors.findDoctor(2))
        }
    }
}

