package sequences.arithmetic

import java.math.BigDecimal
import java.math.BigInteger

fun Sequence<Int>.multiplication(): Int =
    fold(0) { acc, x -> acc * x }

fun Sequence<Long>.multiplication(): Long =
    fold(0L)  { acc, x -> acc * x }

fun Sequence<Float>.multiplication(): Float =
    fold(0F)  { acc, x -> acc * x }

fun Sequence<Double>.multiplication(): Double =
    fold(0.0)  { acc, x -> acc * x }

fun Sequence<BigInteger>.multiplication(): BigInteger =
    fold(BigInteger.ZERO)  { acc, x -> acc * x }

fun Sequence<BigDecimal>.multiplication(): BigDecimal =
    fold(BigDecimal.ZERO)  { acc, x -> acc * x }
