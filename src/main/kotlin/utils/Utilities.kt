package utils

/**
 * The `Utilities` object provides utility functions.
 */
object Utilities {

    /**
     * Checks if the given index is a valid index for the provided list.
     *
     * @param index The index to be checked.
     * @param list The list to check the index against.
     * @return `true` if the index is valid, `false` otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}
