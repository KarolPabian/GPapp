package controllers
import controllers.PatientAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import models.Patient
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PatientAPITest {



    private lateinit var patientAPI: PatientAPI

    @BeforeEach
    fun setup() {
        val xmlSerializer = XMLSerializer(File("patients.xml"))
        patientAPI = PatientAPI(xmlSerializer)
    }
    @Nested
    inner class AddPatients {

        @Test
        fun `adding a patient to a populated list adds to ArrayList`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val newPatient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")

            assertTrue(patientAPI.add(newPatient))

            assertEquals(1, patientAPI.numberOfPatients())
            assertEquals(newPatient, patientAPI.findPatient(0))
        }

        @Test
        fun `adding a patient to an empty list adds to ArrayList`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val newPatient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")

            assertTrue(patientAPI.add(newPatient))

            assertEquals(1, patientAPI.numberOfPatients())
            assertEquals(newPatient, patientAPI.findPatient(0))
        }

        @Test
        fun `adding multiple patients to the list`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val patient1 = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")
            val patient2 = Patient(2, "Jane Doe", "02/02/1995", 'F', "987-654-3210")

            assertTrue(patientAPI.add(patient1))
            assertTrue(patientAPI.add(patient2))

            assertEquals(2, patientAPI.numberOfPatients())
            assertEquals(patient1, patientAPI.findPatient(0))
            assertEquals(patient2, patientAPI.findPatient(1))
        }
    }
    @Nested
    inner class ListPatients {

        @Test
        fun `listAllPatients returns No Patients Stored message when ArrayList is empty`() {
            val emptyPatientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            assertEquals(0, emptyPatientAPI.numberOfPatients())
            assertTrue(emptyPatientAPI.listAllPatients().lowercase().contains("no patients"))
        }

        @Test
        fun `listAllPatients returns Patients when ArrayList has patients stored`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val newPatient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")
            patientAPI.add(newPatient)

            val patientsString = patientAPI.listAllPatients().lowercase()

            assertEquals(1, patientAPI.numberOfPatients())
            assertTrue(patientsString.contains("john doe"))
        }
    }
    @Nested
    inner class UpdatePatients {

        @Test
        fun `updating a patient that does not exist returns false`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            assertFalse(
                patientAPI.updatePatient(
                    0,
                    Patient(1, "Updating Patient", "02/02/1995", 'F', "987-654-3210")
                )
            )
        }

        @Test
        fun `updating a patient that exists returns true and updates`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val patient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")
            patientAPI.add(patient)

            val updated = patientAPI.updatePatient(
                0,
                Patient(1, "Updating Patient", "02/02/1995", 'F', "987-654-3210")
            )

            assertTrue(updated)
            assertEquals("Updating Patient", patientAPI.findPatient(0)?.name)
            assertEquals("02/02/1995", patientAPI.findPatient(0)?.dateOfBirth)
            assertEquals('F', patientAPI.findPatient(0)?.gender)
            assertEquals("987-654-3210", patientAPI.findPatient(0)?.phoneNumber)
        }
    }
    @Nested
    inner class DeletePatients {

        @Test
        fun `deleting a Patient that does not exist returns null`() {
            val emptyPatientAPI = PatientAPI(XMLSerializer(File("patients.xml")))

            assertNull(emptyPatientAPI.deletePatient(0))
        }

        @Test
        fun `deleting a patient that exists deletes and returns deleted object`() {
            val patientAPI = PatientAPI(XMLSerializer(File("patients.xml")))
            val patient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")
            patientAPI.add(patient)

            val deletedPatient = patientAPI.deletePatient(0)

            assertEquals(0, patientAPI.numberOfPatients())
            assertEquals(patient, deletedPatient)
        }
    }
    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            patientAPI.store()

            val loadedPatients = PatientAPI(XMLSerializer(File("patients.xml")))
            loadedPatients.load()

            assertEquals(0, loadedPatients.numberOfPatients())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {
            // Storing 3 patients to the patients.xml file.
            patientAPI.add(Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890"))
            patientAPI.add(Patient(2, "Jane Doe", "02/02/1995", 'F', "987-654-3210"))
            patientAPI.add(Patient(3, "Alice", "03/03/2000", 'F', "555-555-5555"))
            patientAPI.store()

            val loadedPatients = PatientAPI(XMLSerializer(File("patients.xml")))
            loadedPatients.load()

            assertEquals(3, loadedPatients.numberOfPatients())

            assertEquals(patientAPI.findPatient(0), loadedPatients.findPatient(0))
            assertEquals(patientAPI.findPatient(1), loadedPatients.findPatient(1))
            assertEquals(patientAPI.findPatient(2), loadedPatients.findPatient(2))
        }
    }
}
