import Constants.indexCount
import Constants.maxLocationDepth
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*

/**
 * Indexes POIs
 */
object Index {
    @JvmStatic
    fun main(args: Array<String>) {
        val filename = args[0]
        println("Indexing $filename")
        println()
        
        val limiter = Semaphore(Runtime.getRuntime().availableProcessors())

        val jobs = (0 until indexCount).map { 
            GlobalScope.launch {
                limiter.withPermit {
                    val seed1 = it
                    val seed2 = 0
                    println("Indexing ${it+1}/$indexCount...")
                    createIndex(filename, seed1, seed2, it * maxLocationDepth / indexCount)
                    println("Indexing ${it+1}/$indexCount finished")
                }
            }
        }

        runBlocking {        
            jobs.joinAll()
        }
    }

    private fun createIndex(filename: String, seed1: Int, seed2: Int, locationDepth: Int) {
        val index = IndexBuilder()
        val reader = POIFileReader(filename)
        while(reader.hasNext()) {
            val position = reader.position().toInt()
            val poi = reader.next()
            val location = Vec3d.fromLatLon(poi.lat, poi.lon)

            // amenity
            index.add(Hash.hash(poi.amenity, location, seed1, seed2, locationDepth), position)

            // name
            index.add(Hash.hash(poi.name, location, seed1, seed2, locationDepth), position)

            if (poi.street.isNotEmpty()) {
                // amenity street
                index.add(Hash.hash(poi.amenity + " " + poi.street, location, seed1, seed2, locationDepth), position)
            }

            if (poi.city.isNotEmpty()) {
                // amenity city
                index.add(Hash.hash(poi.amenity + " " + poi.city, location, seed1, seed2, locationDepth), position)
            }
            
            if (poi.city.isNotEmpty()) {
                // name city
                index.add(Hash.hash(poi.name + " " + poi.city, location, seed1, seed2, locationDepth), position)
            }
        }
        index.write("$filename.$seed1.$seed2.idx")        
    }
}