import java.io.*
import java.nio.channels.FileChannel
import kotlin.collections.mutableListOf
import kotlin.math.*

/**
 * Binary-search on a memory mapped file.
 */
class IndexQuerier(filename: String) {
    private val fileSize = File(filename).length()
    private val map = RandomAccessFile(filename, "r").channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize).asLongBuffer()
    private val size = map.limit()

    fun get(hash: Int, limit: Int) : List<Int> {
        val entry = hash.toLong() shl 32

        // good old binary search to find the leftmost element
        var l = 0
        var r = size
        while(l < r) {
            val m = l + (r - l) / 2
            if (map[m] < entry) {
                l = m + 1
            } else {
                r = m
            }

        }

        val result = mutableListOf<Int>()
        r = min(l + limit, size)
        while (l < r) {
            result.add(map[l].toInt())
            l++
        }
        return result
    }
}
