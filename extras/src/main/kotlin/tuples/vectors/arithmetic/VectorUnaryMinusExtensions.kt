package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

@JvmName("intVectorUnaryMinus")
operator fun Vec2<Int>.unaryMinus(): Vec2<Int> = 0 - this

@JvmName("longVectorUnaryMinus")
operator fun Vec2<Long>.unaryMinus(): Vec2<Long> = 0L - this

@JvmName("floatVectorUnaryMinus")
operator fun Vec2<Float>.unaryMinus(): Vec2<Float> = 0F - this

@JvmName("doubleVectorUnaryMinus")
operator fun Vec2<Double>.unaryMinus(): Vec2<Double> = 0.0 - this

@JvmName("bigIntegerVectorUnaryMinus")
operator fun Vec2<BigInteger>.unaryMinus(): Vec2<BigInteger> = BigInteger.ZERO - this

@JvmName("bigDecimalVectorUnaryMinus")
operator fun Vec2<BigDecimal>.unaryMinus(): Vec2<BigDecimal> = BigDecimal.ZERO - this

@JvmName("intVectorUnaryMinus")
operator fun Vec3<Int>.unaryMinus(): Vec3<Int> = 0 - this

@JvmName("longVectorUnaryMinus")
operator fun Vec3<Long>.unaryMinus(): Vec3<Long> = 0L - this

@JvmName("floatVectorUnaryMinus")
operator fun Vec3<Float>.unaryMinus(): Vec3<Float> = 0F - this

@JvmName("doubleVectorUnaryMinus")
operator fun Vec3<Double>.unaryMinus(): Vec3<Double> = 0.0 - this

@JvmName("bigIntegerVectorUnaryMinus")
operator fun Vec3<BigInteger>.unaryMinus(): Vec3<BigInteger> = BigInteger.ZERO - this

@JvmName("bigDecimalVectorUnaryMinus")
operator fun Vec3<BigDecimal>.unaryMinus(): Vec3<BigDecimal> = BigDecimal.ZERO - this
