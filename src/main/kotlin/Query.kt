import Constants.earthRadius
import Constants.indexCount
import Constants.maxLocationBits

/**
 * Interactive query tools for indexed POIs.
 */
object Query {
    @JvmStatic
    fun main(args: Array<String>) {
        val filename = args[0]

        println()
        println("Querying $filename")
        println()
        
        val reader = POILookupFileReader(filename)

        val indexes= (0 until indexCount).map { 
            val seed1 = it
            val seed2 = 0
            IndexQuerier("$filename.$seed1.$seed2.idx")
        }

        println("Start typing. If you like the first result, hit 'ENTER' to move your location there:")

        var text = ""
        var location = Vec3d(0.0, 0.0, 0.0)
        var best: POI? = null
        val queryLimit = 1000

        val stdin = System.`in`

        while(true) {
            println()
            print(text)
            
            val ch = stdin.read()

            if (ch == 10) {
                text = ""
                if (best != null) {
                    println()
                    println("You are now located at ${best.name}. Start typing to explore the area and hit ENTER to move again:")
                    location = Vec3d.fromLatLon(best.lat, best.lon)
                }
                continue
            } else if (ch == 127) {
                text = if(text.isNotEmpty()) text.substring(0, text.length - 1) else ""
                if (text.isEmpty())
                    continue
            } else {
                text += Char(ch)
            }

            println()
            println()

            val ids = (0 until indexCount).map {
                val seed1 = it.toUInt()
                val seed2 = 0u
                val hash = Hash.hash(text, location, seed1, seed2,  it * maxLocationBits / indexCount)
                indexes[it].get(hash, queryLimit)
            }
            val idFreq = ids.flatten().groupBy { it }.mapValues { it.value.size }.toList().sortedBy { -it.second }

            best = reader.read(idFreq.first().first.toLong())

            for((id, freq) in (idFreq.take((10)))) {
                val poi = reader.read(id.toLong())
                val distance = location.distanceTo(Vec3d.fromLatLon(poi.lat, poi.lon)) * earthRadius
                println("${freq*100/indexCount}% ${poi.amenity.padEnd(20)}\t${poi.name.padEnd(50)} ${poi.street.padEnd(40)} ${poi.city.padEnd(40)} ${distance.toInt()}km")
            }
        }        
    }

}