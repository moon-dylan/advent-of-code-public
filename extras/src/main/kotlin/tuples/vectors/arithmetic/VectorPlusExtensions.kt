package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

operator fun Vec2<Int>.plus(addable: Int): Vec2<Int> =
    first + addable to second + addable

operator fun Vec2<Long>.plus(addable: Long): Vec2<Long> =
    first + addable to second + addable

operator fun Vec2<Float>.plus(addable: Float): Vec2<Float> =
    first + addable to second + addable

operator fun Vec2<Double>.plus(addable: Double): Vec2<Double> =
    first + addable to second + addable

operator fun Vec2<BigInteger>.plus(addable: BigInteger): Vec2<BigInteger> =
    first + addable to second + addable

operator fun Vec2<BigDecimal>.plus(addable: BigDecimal): Vec2<BigDecimal> =
    first + addable to second + addable

operator fun Int.plus(vector: Vec2<Int>): Vec2<Int> =
    this + vector.first to this + vector.second

operator fun Long.plus(vector: Vec2<Long>): Vec2<Long> =
    this + vector.first to this + vector.second

operator fun Float.plus(vector: Vec2<Float>): Vec2<Float> =
    this + vector.first to this + vector.second

operator fun Double.plus(vector: Vec2<Double>): Vec2<Double> =
    this + vector.first to this + vector.second

operator fun BigInteger.plus(vector: Vec2<BigInteger>): Vec2<BigInteger> =
    this + vector.first to this + vector.second

operator fun BigDecimal.plus(vector: Vec2<BigDecimal>): Vec2<BigDecimal> =
    this + vector.first to this + vector.second

@JvmName("intVec2Plus")
operator fun Vec2<Int>.plus(other: Vec2<Int>): Vec2<Int> =
    first + other.first to second + other.second

@JvmName("longVec2Plus")
operator fun Vec2<Long>.plus(other: Vec2<Long>): Vec2<Long> =
    first + other.first to second + other.second

@JvmName("floatVec2Plus")
operator fun Vec2<Float>.plus(other: Vec2<Float>): Vec2<Float> =
    first + other.first to second + other.second

@JvmName("doubleVec2Plus")
operator fun Vec2<Double>.plus(other: Vec2<Double>): Vec2<Double> =
    first + other.first to second + other.second

@JvmName("bigIntegerVec2Plus")
operator fun Vec2<BigInteger>.plus(other: Vec2<BigInteger>): Vec2<BigInteger> =
    first + other.first to second + other.second

@JvmName("bigDecimalVec2Plus")
operator fun Vec2<BigDecimal>.plus(other: Vec2<BigDecimal>): Vec2<BigDecimal> =
    first + other.first to second + other.second

@JvmName("stringVec2Plus")
operator fun Vec2<String>.plus(other: Vec2<String>): Vec2<String> =
    first + other.first to second + other.second

operator fun Vec3<Int>.plus(addable: Int): Vec3<Int> =
    Triple(first + addable, second + addable, third + addable)

operator fun Vec3<Long>.plus(addable: Long): Vec3<Long> =
    Triple(first + addable, second + addable, third + addable)

operator fun Vec3<Float>.plus(addable: Float): Vec3<Float> =
    Triple(first + addable, second + addable, third + addable)

operator fun Vec3<Double>.plus(addable: Double): Vec3<Double> =
    Triple(first + addable, second + addable, third + addable)

operator fun Vec3<BigInteger>.plus(addable: BigInteger): Vec3<BigInteger> =
    Triple(first + addable, second + addable, third + addable)

operator fun Vec3<BigDecimal>.plus(addable: BigDecimal): Vec3<BigDecimal> =
    Triple(first + addable, second + addable, third + addable)

operator fun Int.plus(vector: Vec3<Int>): Vec3<Int> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

operator fun Long.plus(vector: Vec3<Long>): Vec3<Long> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

operator fun Float.plus(vector: Vec3<Float>): Vec3<Float> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

operator fun Double.plus(vector: Vec3<Double>): Vec3<Double> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

operator fun BigInteger.plus(vector: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

operator fun BigDecimal.plus(vector: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(this + vector.first, this + vector.second, this + vector.third)

@JvmName("intVec3Plus")
operator fun Vec3<Int>.plus(other: Vec3<Int>): Vec3<Int> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("longVec3Plus")
operator fun Vec3<Long>.plus(other: Vec3<Long>): Vec3<Long> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("floatVec3Plus")
operator fun Vec3<Float>.plus(other: Vec3<Float>): Vec3<Float> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("doubleVec3Plus")
operator fun Vec3<Double>.plus(other: Vec3<Double>): Vec3<Double> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("bigIntegerVec3Plus")
operator fun Vec3<BigInteger>.plus(other: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("bigDecimalVec3Plus")
operator fun Vec3<BigDecimal>.plus(other: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(first + other.first, second + other.second, third + other.third)

@JvmName("stringVec3Plus")
operator fun Vec3<String>.plus(other: Vec3<String>): Vec3<String> =
    Triple(first + other.first, second + other.second, third + other.third)
