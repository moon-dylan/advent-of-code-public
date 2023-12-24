package d15

import aoc.AdventOfCodeDay
import collections.replaceWhere
import java.math.BigInteger

typealias InputData = List<Day15.Step>
typealias Box = List<Pair<String, Int>>

object Day15 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        text.split(",")
            .map { step ->
                val label = step.takeWhile { it != '-' && it != '=' }
                if ("-" in step) {
                    Step.Dash(label)
                } else {
                    Step.Equal(label, step.drop(label.length + 1).trim().toInt())
                }
            }

    override fun solvePart1(): Any = input.sumOf { it.string.lavaHash }

    override fun solvePart2(): Any =
        input
            .fold(mapOf<Int, Box>()) { hashMap, step ->
                val hash = step.label.lavaHash
                hashMap.minus(hash).plus(hash to step.updateBox(hashMap[hash]))
            }
            .toFocusingPower()

    private val String.lavaHash: Int
        get() = fold(0) { current, c -> ((current + c.code) * 17) % 256 }

    private fun Step.updateBox(box: Box?): Box =
        when (this) {
            is Step.Dash -> box?.filter { it.first != label } ?: emptyList()
            is Step.Equal -> {
                if (box == null) {
                    listOf(label to focalLength)
                } else if (box.any { it.first == label }) {
                    box.replaceWhere(label to focalLength) { it.first == label }
                } else {
                    box + (label to focalLength)
                }
            }
        }

    private fun Map<Int, Box>.toFocusingPower(): BigInteger =
        values.sumOf { box ->
            box
                .mapIndexed { index, (label, focalLength) ->
                    ((label.lavaHash + 1) * (index + 1) * focalLength)
                }
                .sumOf { it.toBigInteger() }
        }

    sealed interface Step {
        val label: String
        val string: String

        data class Dash(override val label: String) : Step {
            override val string: String = "$label-"
        }

        data class Equal(override val label: String, val focalLength: Int) : Step {
            override val string: String = "$label=$focalLength"
        }
    }
}
