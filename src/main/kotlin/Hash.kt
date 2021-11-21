import kotlin.math.*

/**
 * Algorithmical core
 */
object Hash {
    /**
     * Hash text and location with given seeds and depth.
     */
    fun hash(text: String, location: Vec3d, seed1: UInt, seed2: UInt, locationBits: Int) : UInt {
        // tokenize text
        val tokens = tokenize(text)

        // create unit vector and project
        val s = run {
            val phi = jenkins(123456789u, seed1, seed2).toInt() / 1000000.0
            val z = jenkins(987654321u, seed1, seed2).toInt() / 1000000.0 % 1
            val n = sqrt(1 - z * z)
            ((Vec3d(n * cos(phi), n * sin(phi), z).scalarProduct(location) + 0.5) / 2.0 * 0xFFFFFFFFL).toUInt()
        }

        // compute the hashes of all tokens and sort them
        val hashes = tokens.map { jenkins(it, seed1, seed2)}.sorted()

        // extract 3-min-hash
        val first = hashes[0]
        val second = hashes.getOrNull(1) ?: 0u
        val third = hashes.getOrNull(2) ?: 0u
     
        // 32bit
        val firstBits = 12
        val secondBits = 10
        val thirdBits = 10

        // build text-only hash
        var hash = maskLower(first, firstBits)
        hash = (hash shl secondBits) or maskLower(second, secondBits)
        hash = (hash shl thirdBits) or maskLower(third, thirdBits)

        // overwrite lower bits with location
        hash = zeroLower(hash, locationBits) or shift(s, 32 - locationBits)

        // return
        return hash
    }

    private fun maskLower(value: UInt, bits: Int): UInt {
        if (bits == 0) return 0u
        return value and (0xFFFFFFFFu shr (32 - bits))
    }   
        
    private fun zeroLower(value: UInt, bits: Int): UInt {
        if (bits == 0) return value
        return value and (0xFFFFFFFFu shr (32 - bits)).inv()
    }    

    private fun shift(value: UInt, bits: Int): UInt {
        if (bits >= 32) return 0u
        return value shr bits
    }               
  

    /**
     * Robert Jenkin's mix function
     */ 
    private fun jenkins(A: UInt, B: UInt, C: UInt): UInt {
        var a = A
        var b = B
        var c = C
        a -= b
        a -= c
        a = a xor (c shr 13)
        b -= c
        b -= a
        b = b xor (a shl 8)
        c -= a
        c -= b
        c = c xor (b shr 13)
        a -= b
        a -= c
        a = a xor (c shr 12)
        b -= c
        b -= a
        b = b xor (a shl 16)
        c -= a
        c -= b
        c = c xor (b shr 5)
        a -= b
        a -= c
        a = a xor (c shr 3)
        b -= c
        b -= a
        b = b xor (a shl 10)
        c -= a
        c -= b
        c = c xor (b shr 15)
        return c
    }

    /**
     * Tokenize string into sliding words of maximum length 3.
     * 
     * ABCDEF -> [A, AB, ABC, BCD, CDE, DEF]
     */
    private fun tokenize(text: String) : MutableList<UInt> {
        val result = ArrayList<UInt>()
        var token = 0u
        for(ch in (text.lowercase())) {
            token  = (token shl 8) and 0xFFFFFFu
            token = token or (ch.code.toUInt() and 0xFFu)
            result.add(token)
        }
        return result
    }
}