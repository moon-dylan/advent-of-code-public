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
import kotlin.math.pow

@JvmName("intVector2DistanceToIntVector2")
fun Vec2<Int>.distanceTo(other: Vec2<Int>): BigDecimal =
    map { it.toBigDecimal() }.distanceTo(other.map { it.toBigDecimal() })

@JvmName("longVector2DistanceToLongVector2")
fun Vec2<Long>.distanceTo(other: Vec2<Long>): BigDecimal =
    map { it.toBigDecimal() }.distanceTo(other.map { it.toBigDecimal() })

@JvmName("floatVector2DistanceToFloatVector2")
fun Vec2<Float>.distanceTo(other: Vec2<Float>): BigDecimal =
    map { it.toBigDecimal() }.distanceTo(other.map { it.toBigDecimal() })

@JvmName("doubleVector2DistanceToDoubleVector2")
fun Vec2<Double>.distanceTo(other: Vec2<Double>): BigDecimal =
    map { it.toBigDecimal() }.distanceTo(other.map { it.toBigDecimal() })

@JvmName("doubleVector2DistanceToInDoubleDoubleVector2")
fun Vec2<Double>.distanceToInDouble(other: Vec2<Double>): Double =
    ((other.x - x).pow(2) + (other.y - y).pow(2)).pow(0.5)

@JvmName("bigIntegerVector2DistanceToBigIntegerVector2")
fun Vec2<BigInteger>.distanceTo(other: Vec2<BigInteger>): BigDecimal =
    map { it.toBigDecimal() }.distanceTo(other.map { it.toBigDecimal() })

@JvmName("bigDecimalVector2DistanceToBigDecimalVector2")
fun Vec2<BigDecimal>.distanceTo(other: Vec2<BigDecimal>): BigDecimal =
    ((other.x - x).pow(2) + (other.y - y).pow(2)).sqrt(MathContext.DECIMAL128)

@JvmName("bigDecimalVector3DistanceToBigDecimalVector3")
fun Vec3<BigDecimal>.distanceTo(other: Vec3<BigDecimal>): BigDecimal =
    ((other.x - x).pow(2) + (other.y - y).pow(2) + (other.z - z).pow(2)).sqrt(MathContext.DECIMAL128)