import java.io.FileOutputStream
import java.nio.ByteBuffer

/**
 * Builder for a binary searchable file.
 */
class IndexBuilder() {
    private val list = mutableListOf<Long>()

    fun add(hash: Int, id: Int) {
        val entry = (hash.toLong() shl 32) or id.toLong()
        list.add(entry)
    }

    fun write(filename: String) {
        list.sort()
        val buffer = ByteBuffer.allocate(list.size * 8)
        val longBuffer = buffer.asLongBuffer()
        for(entry in list) {
            longBuffer.put(entry)
        }
        FileOutputStream(filename).use { fos ->
            fos.channel.write(buffer)
        }
   }
}
