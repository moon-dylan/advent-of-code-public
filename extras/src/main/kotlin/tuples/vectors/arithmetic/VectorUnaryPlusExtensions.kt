package tuples.vectors.arithmetic

import tuples.vectors.Vec2
import tuples.vectors.Vec3
import java.math.BigDecimal
import java.math.BigInteger

@JvmName("intUnaryPlus")
operator fun Vec2<Int>.unaryPlus(): Vec2<Int> = this

@JvmName("longUnaryPlus")
operator fun Vec2<Long>.unaryPlus(): Vec2<Long> = this

@JvmName("floatUnaryPlus")
operator fun Vec2<Float>.unaryPlus(): Vec2<Float> = this

@JvmName("doubleUnaryPlus")
operator fun Vec2<Double>.unaryPlus(): Vec2<Double> = this

@JvmName("bigIntegerUnaryPlus")
operator fun Vec2<BigInteger>.unaryPlus(): Vec2<BigInteger> = this

@JvmName("bigDecimalUnaryPlus")
operator fun Vec2<BigDecimal>.unaryPlus(): Vec2<BigDecimal> = this

@JvmName("stringUnaryPlus")
operator fun Vec2<String>.unaryPlus(): Vec2<String> = this

@JvmName("intUnaryPlus")
operator fun Vec3<Int>.unaryPlus(): Vec3<Int> = this

@JvmName("longUnaryPlus")
operator fun Vec3<Long>.unaryPlus(): Vec3<Long> = this

@JvmName("floatUnaryPlus")
operator fun Vec3<Float>.unaryPlus(): Vec3<Float> = this

@JvmName("doubleUnaryPlus")
operator fun Vec3<Double>.unaryPlus(): Vec3<Double> = this

@JvmName("bigIntegerUnaryPlus")
operator fun Vec3<BigInteger>.unaryPlus(): Vec3<BigInteger> = this

@JvmName("bigDecimalUnaryPlus")
operator fun Vec3<BigDecimal>.unaryPlus(): Vec3<BigDecimal> = this

@JvmName("stringUnaryPlus")
operator fun Vec3<String>.unaryPlus(): Vec3<String> = this
