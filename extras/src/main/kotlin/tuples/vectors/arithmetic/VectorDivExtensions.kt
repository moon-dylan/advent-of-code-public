package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

operator fun Vec2<Int>.div(divider: Int): Vec2<Int> =
    first / divider to second / divider

operator fun Vec2<Long>.div(divider: Long): Vec2<Long> =
    first / divider to second / divider

operator fun Vec2<Float>.div(divider: Float): Vec2<Float> =
    first / divider to second / divider

operator fun Vec2<Double>.div(divider: Double): Vec2<Double> =
    first / divider to second / divider

operator fun Vec2<BigInteger>.div(divider: BigInteger): Vec2<BigInteger> =
    first / divider to second / divider

operator fun Vec2<BigDecimal>.div(divider: BigDecimal): Vec2<BigDecimal> =
    first / divider to second / divider

operator fun Int.div(vector: Vec2<Int>): Vec2<Int> =
    this / vector.first to this / vector.second

operator fun Long.div(vector: Vec2<Long>): Vec2<Long> =
    this / vector.first to this / vector.second

operator fun Float.div(vector: Vec2<Float>): Vec2<Float> =
    this / vector.first to this / vector.second

operator fun Double.div(vector: Vec2<Double>): Vec2<Double> =
    this / vector.first to this / vector.second

operator fun BigInteger.div(vector: Vec2<BigInteger>): Vec2<BigInteger> =
    (this / vector.first) to (this / vector.second)

operator fun BigDecimal.div(vector: Vec2<BigDecimal>): Vec2<BigDecimal> =
    (this / vector.first) to (this / vector.second)

operator fun Vec3<Int>.div(divider: Int): Vec3<Int> =
    Triple(first / divider, second / divider, third / divider)

operator fun Vec3<Long>.div(divider: Long): Vec3<Long> =
    Triple(first / divider, second / divider, third / divider)

operator fun Vec3<Float>.div(divider: Float): Vec3<Float> =
    Triple(first / divider, second / divider, third / divider)

operator fun Vec3<Double>.div(divider: Double): Vec3<Double> =
    Triple(first / divider, second / divider, third / divider)

operator fun Vec3<BigInteger>.div(divider: BigInteger): Vec3<BigInteger> =
    Triple(first / divider, second / divider, third / divider)

operator fun Vec3<BigDecimal>.div(divider: BigDecimal): Vec3<BigDecimal> =
    Triple(first / divider, second / divider, third / divider)

operator fun Int.div(vector: Vec3<Int>): Vec3<Int> =
    Triple(this / vector.first, this / vector.second, this / vector.third)

operator fun Long.div(vector: Vec3<Long>): Vec3<Long> =
    Triple(this / vector.first, this / vector.second, this / vector.third)

operator fun Float.div(vector: Vec3<Float>): Vec3<Float> =
    Triple(this / vector.first, this / vector.second, this / vector.third)

operator fun Double.div(vector: Vec3<Double>): Vec3<Double> =
    Triple(this / vector.first, this / vector.second, this / vector.third)

operator fun BigInteger.div(vector: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(this / vector.first, this / vector.second, this / vector.third)

operator fun BigDecimal.div(vector: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(this / vector.first, this / vector.second, this / vector.third)
