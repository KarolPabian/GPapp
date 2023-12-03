package persistence

/**
 * The `Serializer` interface defines methods for reading and writing objects to a data source.
 */
interface Serializer {

    /**
     * Writes the provided object to a data source.
     *
     * @param obj The object to be written.
     * @throws Exception If an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads an object from a data source.
     *
     * @return The object read from the data source.
     * @throws Exception If an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun read(): Any?
}
