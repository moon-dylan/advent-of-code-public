package collections.arithmetic

import java.math.BigDecimal
import java.math.BigInteger

fun Iterable<Int>.division(): Int =
    fold(0) { acc, x -> acc / x }

fun Iterable<Long>.division(): Long =
    fold(0L)  { acc, x -> acc / x }

fun Iterable<Float>.division(): Float =
    fold(0F)  { acc, x -> acc / x }

fun Iterable<Double>.division(): Double =
    fold(0.0)  { acc, x -> acc / x }

fun Iterable<BigInteger>.division(): BigInteger =
    fold(BigInteger.ZERO)  { acc, x -> acc / x }

fun Iterable<BigDecimal>.division(): BigDecimal =
    fold(BigDecimal.ZERO)  { acc, x -> acc / x }