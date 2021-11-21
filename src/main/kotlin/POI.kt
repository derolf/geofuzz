import java.io.*
import org.apache.commons.compress.utils.CountingInputStream

/**
 * POI data class
 */
data class POI(val id: Long, val lat: Double, val lon: Double, val amenity: String, val name: String, val street: String, val housenumber: String, val postcode: String, val city: String, val state: String) {
    fun write(dos: DataOutput) {
        dos.apply {
            writeLong(id)
            writeDouble(lat)
            writeDouble(lon)
            writeUTF(amenity)
            writeUTF(name)
            writeUTF(street)
            writeUTF(housenumber)
            writeUTF(postcode)
            writeUTF(city)
            writeUTF(state)
        }
    }

    companion object {
        fun read(dis: DataInput) : POI {
            return dis.run {
                val id = readLong()
                val lat = readDouble()
                val lon = readDouble()
                val amenity = readUTF()
                val name = readUTF()
                val street = readUTF()
                val housenumber = readUTF()
                val postcode = readUTF()
                val city = readUTF()
                val state = readUTF()
                return@run POI(id, lat, lon, amenity, name, street, housenumber, postcode, city, state)
            }
        }
    }
}

class POIFileReader(filename: String) : Iterator<POI>, Closeable {
    val cis = CountingInputStream(BufferedInputStream(FileInputStream(filename), 1024*1024))
    val dis = DataInputStream(cis)

    fun position() : Long = cis.bytesRead
    override fun hasNext() : Boolean = dis.available() > 0
    override fun next(): POI = POI.read(dis)
    
    override fun close() = dis.close()
}

class POILookupFileReader(filename: String) : Closeable {
    val file = RandomAccessFile(filename, "r")

    fun read(position: Long) : POI {
        file.seek(position)
        return POI.read(file)
    }
   
    override fun close() = file.close()
}