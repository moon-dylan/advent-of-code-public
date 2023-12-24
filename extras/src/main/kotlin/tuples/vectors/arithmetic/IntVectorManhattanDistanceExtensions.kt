package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import tuples.vectors.map
import tuples.x
import tuples.y
import tuples.z
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.math.abs
import kotlin.math.pow

@JvmName("intVector2ManhattanDistanceToIntVector2")
fun Vec2<Int>.manhattanDistanceTo(other: Vec2<Int>): Int =
    abs(other.x - x) + abs(other.y - y)

@JvmName("longVector2ManhattanDistanceToLongVector2")
fun Vec2<Long>.manhattanDistanceTo(other: Vec2<Long>): Long =
    abs(other.x - x) + abs(other.y - y)

@JvmName("bigIntegerVector2ManhattanDistanceToBigIntegerVector2")
fun Vec2<BigInteger>.manhattanDistanceTo(other: Vec2<BigInteger>): BigInteger =
    (other.x - x).abs() + (other.y - y).abs()
