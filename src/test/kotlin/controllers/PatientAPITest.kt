package controllers
import controllers.PatientAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import models.Patient
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PatientAPITest {

    @Nested
    inner class AddPatients {

        @Test
        fun `adding a patient to a populated list adds to ArrayList`() {
            val patientAPI = PatientAPI()
            val newPatient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")

            assertTrue(patientAPI.add(newPatient))

            assertEquals(1, patientAPI.numberOfPatients())
            assertEquals(newPatient, patientAPI.findPatient(0))
        }

        @Test
        fun `adding a patient to an empty list adds to ArrayList`() {
            val patientAPI = PatientAPI()
            val newPatient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")

            assertTrue(patientAPI.add(newPatient))

            assertEquals(1, patientAPI.numberOfPatients())
            assertEquals(newPatient, patientAPI.findPatient(0))
        }

        @Test
        fun `adding multiple patients to the list`() {
            val patientAPI = PatientAPI()
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
            val emptyPatientAPI = PatientAPI()

            assertEquals(0, emptyPatientAPI.numberOfPatients())
            assertTrue(emptyPatientAPI.listAllPatients().lowercase().contains("no patients"))
        }

        @Test
        fun `listAllPatients returns Patients when ArrayList has patients stored`() {
            val patientAPI = PatientAPI()
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
            val patientAPI = PatientAPI()

            assertFalse(
                patientAPI.updatePatient(
                    0,
                    Patient(1, "Updating Patient", "02/02/1995", 'F', "987-654-3210")
                )
            )
        }

        @Test
        fun `updating a patient that exists returns true and updates`() {
            val patientAPI = PatientAPI()
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
            val emptyPatientAPI = PatientAPI()

            assertNull(emptyPatientAPI.deletePatient(0))
        }

        @Test
        fun `deleting a patient that exists deletes and returns deleted object`() {
            val patientAPI = PatientAPI()
            val patient = Patient(1, "John Doe", "01/01/1990", 'M', "123-456-7890")
            patientAPI.add(patient)

            val deletedPatient = patientAPI.deletePatient(0)

            assertEquals(0, patientAPI.numberOfPatients())
            assertEquals(patient, deletedPatient)
        }
    }
}
