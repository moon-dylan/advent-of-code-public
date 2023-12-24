package sequences

import java.math.BigInteger

fun <E> Sequence<E>.countBigInt(): BigInteger =
    mapBigIndexed { index, _ -> index + BigInteger.ONE }.lastOrNull() ?: BigInteger.ZERO

fun <E> Sequence<E>.countBigInt(predicate: (E) -> Boolean): BigInteger =
    takeWhile(predicate).countBigInt()