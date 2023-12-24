package sequences

import java.math.BigDecimal
import java.math.BigInteger

fun infiniteSequence() = generateSequence { }

fun infiniteIntSequence(start: Int = 0, step: Int = 1) =
    generateSequence { }.mapIndexed { index, _ -> start + step * index }

fun infiniteLongSequence(start: Long = 0, step: Long = 1) =
    generateSequence { }.mapIndexed { index, _ -> start + step * index }

fun infiniteFloatSequence(start: Float = 0F, step: Float = 1F) =
    generateSequence { }.mapIndexed { index, _ -> start + step * index }

fun infiniteDoubleSequence(start: Double = 0.0, step: Double = 1.0) =
    generateSequence { }.mapIndexed { index, _ -> start + step * index }

fun infiniteBigIntegerSequence(
    start: BigInteger = BigInteger.ZERO,
    step: BigInteger = BigInteger.ONE,
) =
    generateSequence { }.mapBigIndexed { index, _ ->
        start + step * index
    }

fun infiniteBigDecimalSequence(
    start: BigDecimal = BigDecimal.ZERO,
    step: BigDecimal = BigDecimal.ONE,
) =
    generateSequence { }.mapBigIndexed { index, _ ->
        start + step * index.toBigDecimal()
    }
