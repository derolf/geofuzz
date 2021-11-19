import org.xml.sax.helpers.DefaultHandler
import org.apache.commons.compress.compressors.bzip2.*
import org.xml.sax.Attributes
import java.io.*
import java.time.Instant
import javax.xml.parsers.SAXParserFactory
import kotlin.math.roundToLong
import kotlin.text.toDouble

/**
 * Extracts named POIS (amenity) from osm-xml-bz2 data
 */
object Extract {
    @JvmStatic
    fun main(args: Array<String>) {
        val lengthMB = Math.round(File(args[0]).length()/1024.0/1024)
        val fis = BZip2CompressorInputStream(BufferedInputStream(FileInputStream(args[0]),1024*1024))
        val fos = DataOutputStream(BZip2CompressorOutputStream(BufferedOutputStream(FileOutputStream(args[1]),1024*1024)))
        var lastMB = 0L
        val start = Instant.now().epochSecond
        var count = 0

        val handler= object : DefaultHandler() {
            val nodeAttrs = mutableMapOf<String, String>()
            val nodeTags = mutableMapOf<String, String>()

            override fun startElement(uri: String?, localName: String?, qName: String, attributes: Attributes) {
                super.startElement(uri, localName, qName, attributes)
                when(qName) {
                    "node" -> {
                        nodeAttrs.clear()
                        nodeAttrs.putAll(IntRange(0, attributes.length - 1).associate { attributes.getQName(it) to attributes.getValue(it) })
                        nodeTags.clear()
                    }
                    "tag" -> {
                        nodeTags[attributes.getValue("k")] = attributes.getValue("v")
                    }
                }
            }

            override fun endElement(uri: String?, localName: String?, qName: String?) { 
                when(qName) {
                    "node" -> processNode()
                }
                val MB = (fis.compressedCount / 1024.0 / 1024).roundToLong()
                if (lastMB < MB) {
                    val now = Instant.now().epochSecond
                    lastMB = MB
                    println("${lastMB}MB ${lastMB/(now-start+1)}MB/s ${lastMB*100/lengthMB}% ${lengthMB}MB $count POIs")
                }
                super.endElement(uri, localName, qName)
            }

            fun processNode() {
                val amenity = nodeTags["amenity"] ?: return
                val name = nodeTags["name"] ?: return
                val id = nodeAttrs["id"]?.toLong() ?: return
                val lat = nodeAttrs["lat"]?.toDouble() ?: return
                val lon = nodeAttrs["lon"]?.toDouble() ?: return
                val city = nodeTags["addr:city"] ?: ""
                val state = nodeTags["addr:state"] ?: ""
                val housenumber = nodeTags["addr:housenumber"] ?: ""
                val postcode = nodeTags["addr:postcode"] ?: ""
                val street = nodeTags["addr:street"] ?: ""
                POI(id, lat, lon, amenity, name, street, housenumber, postcode, city, state).write(fos)
                count += 1
            }
        }

        fos.use { _ -> 
            fis.use { fis ->
                SAXParserFactory.newInstance().newSAXParser().parse(fis, handler)
            }
        }
    }
}
