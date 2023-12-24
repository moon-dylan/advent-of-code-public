package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

operator fun Vec2<Int>.times(multiplier: Int): Vec2<Int> =
    first * multiplier to second * multiplier

operator fun Vec2<Long>.times(multiplier: Long): Vec2<Long> =
    first * multiplier to second * multiplier

operator fun Vec2<Float>.times(multiplier: Float): Vec2<Float> =
    first * multiplier to second * multiplier

operator fun Vec2<Double>.times(multiplier: Double): Vec2<Double> =
    first * multiplier to second * multiplier

operator fun Vec2<BigInteger>.times(multiplier: BigInteger): Vec2<BigInteger> =
    first * multiplier to second * multiplier

operator fun Vec2<BigDecimal>.times(multiplier: BigDecimal): Vec2<BigDecimal> =
    first * multiplier to second * multiplier

operator fun Int.times(vector: Vec2<Int>): Vec2<Int> =
    vector.first * this to vector.second * this

operator fun Long.times(vector: Vec2<Long>): Vec2<Long> =
    vector.first * this to vector.second * this

operator fun Float.times(vector: Vec2<Float>): Vec2<Float> =
    vector.first * this to vector.second * this

operator fun Double.times(vector: Vec2<Double>): Vec2<Double> =
    vector.first * this to vector.second * this

operator fun BigInteger.times(vector: Vec2<BigInteger>): Vec2<BigInteger> =
    vector.first * this to vector.second * this

operator fun BigDecimal.times(vector: Vec2<BigDecimal>): Vec2<BigDecimal> =
    vector.first * this to vector.second * this

operator fun Vec3<Int>.times(multiplier: Int): Vec3<Int> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Vec3<Long>.times(multiplier: Long): Vec3<Long> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Vec3<Float>.times(multiplier: Float): Vec3<Float> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Vec3<Double>.times(multiplier: Double): Vec3<Double> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Vec3<BigInteger>.times(multiplier: BigInteger): Vec3<BigInteger> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Vec3<BigDecimal>.times(multiplier: BigDecimal): Vec3<BigDecimal> =
    Triple(first * multiplier, second * multiplier, third * multiplier)

operator fun Int.times(vector: Vec3<Int>): Vec3<Int> =
    Triple(vector.first * this, vector.second * this, vector.third * this)

operator fun Long.times(vector: Vec3<Long>): Vec3<Long> =
    Triple(vector.first * this, vector.second * this, vector.third * this)

operator fun Float.times(vector: Vec3<Float>): Vec3<Float> =
    Triple(vector.first * this, vector.second * this, vector.third * this)

operator fun Double.times(vector: Vec3<Double>): Vec3<Double> =
    Triple(vector.first * this, vector.second * this, vector.third * this)

operator fun BigInteger.times(vector: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(vector.first * this, vector.second * this, vector.third * this)

operator fun BigDecimal.times(vector: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(vector.first * this, vector.second * this, vector.third * this)
