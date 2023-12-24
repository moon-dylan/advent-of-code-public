package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

operator fun Vec2<Int>.minus(subtractable: Int): Vec2<Int> =
    first - subtractable to second - subtractable

operator fun Vec2<Long>.minus(subtractable: Long): Vec2<Long> =
    first - subtractable to second - subtractable

operator fun Vec2<Float>.minus(subtractable: Float): Vec2<Float> =
    first - subtractable to second - subtractable

operator fun Vec2<Double>.minus(subtractable: Double): Vec2<Double> =
    first - subtractable to second - subtractable

operator fun Vec2<BigInteger>.minus(subtractable: BigInteger): Vec2<BigInteger> =
    first - subtractable to second - subtractable

operator fun Vec2<BigDecimal>.minus(subtractable: BigDecimal): Vec2<BigDecimal> =
    first - subtractable to second - subtractable

operator fun Int.minus(vector: Vec2<Int>): Vec2<Int> =
    this - vector.first to this - vector.second

operator fun Long.minus(vector: Vec2<Long>): Vec2<Long> =
    this - vector.first to this - vector.second

operator fun Float.minus(vector: Vec2<Float>): Vec2<Float> =
    this - vector.first to this - vector.second

operator fun Double.minus(vector: Vec2<Double>): Vec2<Double> =
    this - vector.first to this - vector.second

operator fun BigInteger.minus(vector: Vec2<BigInteger>): Vec2<BigInteger> =
    this - vector.first to this - vector.second

operator fun BigDecimal.minus(vector: Vec2<BigDecimal>): Vec2<BigDecimal> =
    this - vector.first to this - vector.second

@JvmName("intVec2Minus")
operator fun Vec2<Int>.minus(other: Vec2<Int>): Vec2<Int> =
    first - other.first to second - other.second

@JvmName("longVec2Minus")
operator fun Vec2<Long>.minus(other: Vec2<Long>): Vec2<Long> =
    first - other.first to second - other.second

@JvmName("floatVec2Minus")
operator fun Vec2<Float>.minus(other: Vec2<Float>): Vec2<Float> =
    first - other.first to second - other.second

@JvmName("doubleVec2Minus")
operator fun Vec2<Double>.minus(other: Vec2<Double>): Vec2<Double> =
    first - other.first to second - other.second

@JvmName("bigIntegerVec2Minus")
operator fun Vec2<BigInteger>.minus(other: Vec2<BigInteger>): Vec2<BigInteger> =
    first - other.first to second - other.second

@JvmName("bigDecimalVec2Minus")
operator fun Vec2<BigDecimal>.minus(other: Vec2<BigDecimal>): Vec2<BigDecimal> =
    first - other.first to second - other.second

operator fun Vec3<Int>.minus(subtractable: Int): Vec3<Int> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Vec3<Long>.minus(subtractable: Long): Vec3<Long> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Vec3<Float>.minus(subtractable: Float): Vec3<Float> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Vec3<Double>.minus(subtractable: Double): Vec3<Double> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Vec3<BigInteger>.minus(subtractable: BigInteger): Vec3<BigInteger> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Vec3<BigDecimal>.minus(subtractable: BigDecimal): Vec3<BigDecimal> =
    Triple(first - subtractable, second - subtractable, third - subtractable)

operator fun Int.minus(vector: Vec3<Int>): Vec3<Int> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

operator fun Long.minus(vector: Vec3<Long>): Vec3<Long> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

operator fun Float.minus(vector: Vec3<Float>): Vec3<Float> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

operator fun Double.minus(vector: Vec3<Double>): Vec3<Double> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

operator fun BigInteger.minus(vector: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

operator fun BigDecimal.minus(vector: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(this - vector.first, this - vector.second, this - vector.third)

@JvmName("intVec3Minus")
operator fun Vec3<Int>.minus(other: Vec3<Int>): Vec3<Int> =
    Triple(first - other.first, second - other.second, third - other.third)

@JvmName("longVec3Minus")
operator fun Vec3<Long>.minus(other: Vec3<Long>): Vec3<Long> =
    Triple(first - other.first, second - other.second, third - other.third)

@JvmName("floatVec3Minus")
operator fun Vec3<Float>.minus(other: Vec3<Float>): Vec3<Float> =
    Triple(first - other.first, second - other.second, third - other.third)

@JvmName("doubleVec3Minus")
operator fun Vec3<Double>.minus(other: Vec3<Double>): Vec3<Double> =
    Triple(first - other.first, second - other.second, third - other.third)

@JvmName("bigIntegerVec3Minus")
operator fun Vec3<BigInteger>.minus(other: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(first - other.first, second - other.second, third - other.third)

@JvmName("bigDecimalVec3Minus")
operator fun Vec3<BigDecimal>.minus(other: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(first - other.first, second - other.second, third - other.third)
