package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

operator fun Vec2<Int>.rem(modulus: Int): Vec2<Int> =
    first % modulus to second % modulus

operator fun Vec2<Long>.rem(modulus: Long): Vec2<Long> =
    first % modulus to second % modulus

operator fun Vec2<Float>.rem(modulus: Float): Vec2<Float> =
    first % modulus to second % modulus

operator fun Vec2<Double>.rem(modulus: Double): Vec2<Double> =
    first % modulus to second % modulus

operator fun Vec2<BigInteger>.rem(modulus: BigInteger): Vec2<BigInteger> =
    first % modulus to second % modulus

operator fun Vec2<BigDecimal>.rem(modulus: BigDecimal): Vec2<BigDecimal> =
    first % modulus to second % modulus

operator fun Int.rem(vector: Vec2<Int>): Vec2<Int> =
    this % vector.first to this % vector.second

operator fun Long.rem(vector: Vec2<Long>): Vec2<Long> =
    this % vector.first to this % vector.second

operator fun Float.rem(vector: Vec2<Float>): Vec2<Float> =
    this % vector.first to this % vector.second

operator fun Double.rem(vector: Vec2<Double>): Vec2<Double> =
    this % vector.first to this % vector.second

operator fun BigInteger.rem(vector: Vec2<BigInteger>): Vec2<BigInteger> =
    (this % vector.first) to (this % vector.second)

operator fun BigDecimal.rem(vector: Vec2<BigDecimal>): Vec2<BigDecimal> =
    (this % vector.first) to (this % vector.second)

@JvmName("intVectorRem")
operator fun Vec2<Int>.rem(modulus: Vec2<Int>): Vec2<Int> =
    first % modulus.first to second % modulus.second

@JvmName("longVectorRem")
operator fun Vec2<Long>.rem(modulus: Vec2<Long>): Vec2<Long> =
    first % modulus.first to second % modulus.second

@JvmName("floatVectorRem")
operator fun Vec2<Float>.rem(modulus: Vec2<Float>): Vec2<Float> =
    first % modulus.first to second % modulus.second

@JvmName("doubleVectorRem")
operator fun Vec2<Double>.rem(modulus: Vec2<Double>): Vec2<Double> =
    first % modulus.first to second % modulus.second

@JvmName("bigIntegerVectorRem")
operator fun Vec2<BigInteger>.rem(modulus: Vec2<BigInteger>): Vec2<BigInteger> =
    first % modulus.first to second % modulus.second

@JvmName("bigDecimalVectorRem")
operator fun Vec2<BigDecimal>.rem(modulus: Vec2<BigDecimal>): Vec2<BigDecimal> =
    first % modulus.first to second % modulus.second

operator fun Vec3<Int>.rem(modulus: Int): Vec3<Int> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Vec3<Long>.rem(modulus: Long): Vec3<Long> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Vec3<Float>.rem(modulus: Float): Vec3<Float> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Vec3<Double>.rem(modulus: Double): Vec3<Double> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Vec3<BigInteger>.rem(modulus: BigInteger): Vec3<BigInteger> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Vec3<BigDecimal>.rem(modulus: BigDecimal): Vec3<BigDecimal> =
    Triple(first % modulus, second % modulus, third % modulus)

operator fun Int.rem(vector: Vec3<Int>): Vec3<Int> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

operator fun Long.rem(vector: Vec3<Long>): Vec3<Long> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

operator fun Float.rem(vector: Vec3<Float>): Vec3<Float> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

operator fun Double.rem(vector: Vec3<Double>): Vec3<Double> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

operator fun BigInteger.rem(vector: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

operator fun BigDecimal.rem(vector: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(this % vector.first, this % vector.second, this % vector.third)

@JvmName("intVectorRem")
operator fun Vec3<Int>.rem(modulus: Vec3<Int>): Vec3<Int> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)

@JvmName("longVectorRem")
operator fun Vec3<Long>.rem(modulus: Vec3<Long>): Vec3<Long> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)

@JvmName("floatVectorRem")
operator fun Vec3<Float>.rem(modulus: Vec3<Float>): Vec3<Float> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)

@JvmName("doubleVectorRem")
operator fun Vec3<Double>.rem(modulus: Vec3<Double>): Vec3<Double> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)

@JvmName("bigIntegerVectorRem")
operator fun Vec3<BigInteger>.rem(modulus: Vec3<BigInteger>): Vec3<BigInteger> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)

@JvmName("bigDecimalVectorRem")
operator fun Vec3<BigDecimal>.rem(modulus: Vec3<BigDecimal>): Vec3<BigDecimal> =
    Triple(first % modulus.first, second % modulus.second, third % modulus.third)
