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


    private val dummyDoctor1 = Doctor(1, "Dr. Smith", "General Medicine", "123-456-7890")
    private val dummyDoctor2 = Doctor(2, "Dr. Johnson", "Pediatrics", "987-654-3210")

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
            assertTrue(populatedDoctors.add(dummyDoctor1))

            assertEquals(1, populatedDoctors.numberOfDoctors())
            assertEquals(dummyDoctor1, populatedDoctors.findDoctor(0))
        }

        @Test
        fun `adding a doctor to an empty list adds to ArrayList`() {
            assertTrue(emptyDoctors.add(dummyDoctor1))

            assertEquals(1, emptyDoctors.numberOfDoctors())
            assertEquals(dummyDoctor1, emptyDoctors.findDoctor(0))
        }
    }

    @Nested
    inner class ListDoctors {

        @Test
        fun `listAllDoctors returns No Doctors Stored message when ArrayList is empty`() {
            val emptyDoctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertEquals(0, emptyDoctorAPI.numberOfDoctors())
            assertTrue(emptyDoctorAPI.listAllDoctors().lowercase().contains("no doctors"))
        }

        @Test
        fun `listAllDoctors returns Doctors when ArrayList has doctors stored`() {
            populatedDoctors.add(dummyDoctor1)

            val doctorsString = populatedDoctors.listAllDoctors().lowercase()

            assertEquals(1, populatedDoctors.numberOfDoctors())
            assertTrue(doctorsString.contains("dr. smith"))
        }
    }

    @Nested
    inner class ListDoctorsBySpecialization {

        @Test
        fun `listDoctorsBySpecialization returns empty list when no doctors match specialization`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

            val result: String = doctorAPI.listDoctorsBySpecialization("Nonexistent Specialization")

            assertTrue(result.contains("No doctors found for the specialization"))
            assertTrue(result.contains("Nonexistent Specialization"))
        }

        @Test
        fun `listDoctorsBySpecialization returns list of doctors with matching specialization`() {
            populatedDoctors.add(dummyDoctor1)
            populatedDoctors.add(dummyDoctor2)

            val result: String = populatedDoctors.listDoctorsBySpecialization("General Medicine")

            assertTrue(result.contains("Dr. Smith"))
            assertFalse(result.contains("Dr. Johnson"))
        }
    }

    @Nested
    inner class UpdateDoctors {

        @Test
        fun `updating a doctor that does not exist returns false`() {
            val doctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertFalse(
                doctorAPI.updateDoctor(
                    0,
                    Doctor(1, "Updating Doctor", "In-Progress", "123-456-7890")
                )
            )
        }

        @Test
        fun `updating a doctor that exists returns true and updates`() {
            populatedDoctors.add(dummyDoctor1)

            val updated = populatedDoctors.updateDoctor(
                0,
                Doctor(1, "Updating Doctor", "In-Progress", "987-654-3210")
            )

            assertTrue(updated)
            assertEquals("Updating Doctor", populatedDoctors.findDoctor(0)?.name)
            assertEquals("In-Progress", populatedDoctors.findDoctor(0)?.specialization)
            assertEquals("987-654-3210", populatedDoctors.findDoctor(0)?.phoneNumber)
        }
    }

    @Nested
    inner class DeleteDoctors {

        @Test
        fun `deleting a Doctor that does not exist returns null`() {
            val emptyDoctorAPI = DoctorAPI(XMLSerializer(File("doctors.xml")))

            assertNull(emptyDoctorAPI.deleteDoctor(0))
        }

        @Test
        fun `deleting a doctor that exists delete and returns deleted object`() {
            populatedDoctors.add(dummyDoctor1)

            val deletedDoctor = populatedDoctors.deleteDoctor(0)

            assertEquals(0, populatedDoctors.numberOfDoctors())
            assertEquals(dummyDoctor1, deletedDoctor)
        }
    }

    @Nested
    inner class AssignPatientToDoctor {

        @Test
        fun `assigning a patient to a doctor returns true`() {


            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            populatedDoctors.add(dummyDoctor1)
            assertTrue(populatedDoctors.assignPatient(0, newPatient))
        }

        @Test
        fun `assigning a patient to a non-existing doctor returns false`() {


            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            assertFalse(populatedDoctors.assignPatient(0, newPatient))
        }

        @Test
        fun `assigning a patient to a doctor with invalid index returns false`() {


            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            populatedDoctors.add(dummyDoctor1)
            assertFalse(populatedDoctors.assignPatient(1, newPatient))
        }
    }

    @Nested
    inner class UnassignPatientFromDoctor {

        @Test
        fun `unassigning a patient from a doctor returns true`() {
            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            populatedDoctors.add(dummyDoctor1)
            populatedDoctors.assignPatient(0, newPatient)

            val unassigned = populatedDoctors.unassignPatient(0, newPatient)

            assertTrue(unassigned)
            assertNull(newPatient.assignedDoctor)
        }

        @Test
        fun `unassigning a non-existing patient from a doctor returns false`() {
            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            val unassigned = populatedDoctors.unassignPatient(0, newPatient)

            assertFalse(unassigned)
        }

        @Test
        fun `unassigning a patient from a doctor with an invalid index returns false`() {
            val newPatient = Patient(1, "John Doe", "2000-01-01", 'M', "555-1234")

            populatedDoctors.add(dummyDoctor1)
            populatedDoctors.assignPatient(0, newPatient)

            val unassigned = populatedDoctors.unassignPatient(1, newPatient)

            assertFalse(unassigned)
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
