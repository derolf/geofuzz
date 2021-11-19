import kotlin.math.*

/**
 * Algorithmical core
 */
object Hash {
    /**
     * Hash text and location with given seeds and depth.
     */
    fun hash(text: String, location: Vec3d, seed1: Int, seed2: Int, locationDepth: Int) : Int {
        // tokenize text
        val tokens = tokenize(text)

        // create unit vector and project
        val s = run {
            val phi = jenkins(123456789, seed1, seed2) / 1000000.0
            val z = jenkins(987654321, seed1, seed2) / 1000000.0 % 1
            val n = sqrt(1 - z * z)
            (Vec3d(n * cos(phi), n * sin(phi), z).scalarProduct(location) + 1.0)/2
        }

        // add location tokens (they will be negative)
        for (i in 0 until locationDepth) {
            tokens.add(-(s * 2.0.pow(i)).toInt())
        }

        // compute the hashes of all tokens and sort them
        val hashes = tokens.map { jenkins(it, seed1, seed2)}.sorted()

        // extract 3-min-hash
        val first = hashes[0]
        val second = hashes.getOrNull(1) ?: 0
        val third = hashes.getOrNull(2) ?: 0

        // fold'em
        return ((first and 0xFFFF) shl 16) or (second and 0xFF) shl 8 or (third and 0xFF)
    }

    /**
     * Robert Jenkin's mix function
     */ 
    private fun jenkins(A: Int, B: Int, C: Int): Int {
        var a = A
        var b = B
        var c = C
        a -= b
        a -= c
        a = a xor (c ushr 13)
        b -= c
        b -= a
        b = b xor (a shl 8)
        c -= a
        c -= b
        c = c xor (b ushr 13)
        a -= b
        a -= c
        a = a xor (c ushr 12)
        b -= c
        b -= a
        b = b xor (a shl 16)
        c -= a
        c -= b
        c = c xor (b ushr 5)
        a -= b
        a -= c
        a = a xor (c ushr 3)
        b -= c
        b -= a
        b = b xor (a shl 10)
        c -= a
        c -= b
        c = c xor (b ushr 15)
        return c
    }

    /**
     * Tokenize string into sliding words of maximum length 3.
     * 
     * ABCDEF -> [A, AB, ABC, BCD, CDE, DEF]
     */
    private fun tokenize(text: String) : MutableList<Int> {
        val result = ArrayList<Int>()
        var token = 0
        for(ch in (text.lowercase())) {
            token  = (token shl 8) and 0xFFFFFF
            token = token or ch.code
            result.add(token)
        }
        return result
    }
}