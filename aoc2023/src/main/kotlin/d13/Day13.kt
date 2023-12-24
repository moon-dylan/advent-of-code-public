package d13

import aoc.AdventOfCodeDay
import collections.transpose
import collections.zipStrictOrNull
import java.math.BigInteger

typealias InputData = List<List<String>>

object Day13 : AdventOfCodeDay<InputData>() {
    //        override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        text
            .trim().split("\n\n+".toRegex())
            .asSequence()
            .map { it.trim() }
            .map { it.split("\n") }
            .toList()

    override fun solvePart1(): Any = solve(requiredErrors = 0)
    override fun solvePart2(): Any = solve(requiredErrors = 1)

    private fun solve(requiredErrors: Int): BigInteger =
        input.sumOf { rows ->
            val topSize = getReflectionSize(rows, requiredErrors)?.toBigInteger()
            val leftSize = getReflectionSize(rows.transpose()!!, requiredErrors)?.toBigInteger()
            (topSize?.times(100.toBigInteger()) ?: BigInteger.ZERO) + (leftSize ?: BigInteger.ZERO)
        }

    private fun getReflectionSize(rows: List<String>, requiredErrors: Int): Int? =
        rows.indices
            .drop(1)
            .firstOrNull { topSize ->
                isReflection(rows.take(topSize).reversed(), rows.drop(topSize), requiredErrors)
            }

    private fun isReflection(top: List<String>, bottom: List<String>, requiredErrors: Int): Boolean =
        top.zip(bottom)
            .flatMap { (topRow, bottomRow) -> topRow.toList().zipStrictOrNull(bottomRow.toList())!! }
            .fold(true to requiredErrors) { (isCorrect, errorsLeft), (topCell, bottomCell) ->
                if (topCell == bottomCell) {
                    isCorrect to errorsLeft
                } else if (0 < errorsLeft) {
                    true to (errorsLeft - 1)
                } else {
                    false to errorsLeft
                }
            }
            .takeIf { (_, requiredErrorsLeft) -> requiredErrorsLeft == 0 }
            ?.first ?: false
}
