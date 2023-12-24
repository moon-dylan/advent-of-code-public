package sequences.arithmetic

import java.math.BigDecimal
import java.math.BigInteger

fun Sequence<Int>.division(): Int =
    fold(0) { acc, x -> acc / x }

fun Sequence<Long>.division(): Long =
    fold(0L)  { acc, x -> acc / x }

fun Sequence<Float>.division(): Float =
    fold(0F)  { acc, x -> acc / x }

fun Sequence<Double>.division(): Double =
    fold(0.0)  { acc, x -> acc / x }

fun Sequence<BigInteger>.division(): BigInteger =
    fold(BigInteger.ZERO)  { acc, x -> acc / x }

fun Sequence<BigDecimal>.division(): BigDecimal =
    fold(BigDecimal.ZERO)  { acc, x -> acc / x }