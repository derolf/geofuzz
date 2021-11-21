import java.io.FileOutputStream
import java.nio.ByteBuffer

/**
 * Builder for a binary searchable file.
 */
class IndexBuilder() {
    private val list = mutableListOf<ULong>()

    fun add(hash: UInt, id: UInt) {
        val entry = (hash.toULong() shl 32) or id.toULong()
        list.add(entry)
    }

    fun write(filename: String) {
        // sort and build buffer
        list.sort()
        val buffer = ByteBuffer.allocate(list.size * 8)
        val longBuffer = buffer.asLongBuffer()
        for(entry in list) {
            longBuffer.put(entry.toLong())
        }

        // write buffer
        FileOutputStream(filename).use { fos ->
            fos.channel.write(buffer)
        }
   }
}
