import kotlin.math.*

/**
 * 3D vector data class
 */
data class Vec3d(val x: Double, val y: Double, val z: Double) {
    fun scalarProduct(other: Vec3d) : Double {
        return x * other.x + y * other.y + z * other.z
    }

    fun distanceTo(other: Vec3d) : Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    companion object {
        fun fromLatLon(latDeg: Double, lonDeg: Double) : Vec3d {
            val lat = latDeg / 180 * PI
            val lon = lonDeg / 180 * PI
            val x = cos(lat) * cos(lon)
            val y = cos(lat) * sin(lon)
            val z = sin(lat)
            return Vec3d(x, y, z)
        }
    }
}
