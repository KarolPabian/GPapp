package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Doctor
import models.Patient
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

/**
 * The `XMLSerializer` class implements the Serializer interface for reading and writing objects to XML files
 * using the XStream library.
 *
 * @param file The File object representing the XML file to read from or write to.
 */
class XMLSerializer(private val file: File) : Serializer {

    /**
     * Reads an object from the XML file using XStream.
     *
     * @return The object read from the XML file.
     * @throws Exception If an error occurs during the reading process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Doctor::class.java))
        xStream.allowTypes(arrayOf(Patient::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes the provided object to the XML file using XStream.
     *
     * @param obj The object to be written.
     * @throws Exception If an error occurs during the writing process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
