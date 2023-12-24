package collections.arithmetic

import collections.NonEmptyList
import java.math.BigDecimal
import java.math.BigInteger

fun NonEmptyList<Int>.multiplication(): Int =
    fold(1) { acc, x -> acc * x }

fun NonEmptyList<Long>.multiplication(): Long =
    fold(1L)  { acc, x -> acc * x }

fun NonEmptyList<Float>.multiplication(): Float =
    fold(1F)  { acc, x -> acc * x }

fun NonEmptyList<Double>.multiplication(): Double =
    fold(1.0)  { acc, x -> acc * x }

fun NonEmptyList<BigInteger>.multiplication(): BigInteger =
    fold(BigInteger.ONE)  { acc, x -> acc * x }

fun NonEmptyList<BigDecimal>.multiplication(): BigDecimal =
    fold(BigDecimal.ONE)  { acc, x -> acc * x }
