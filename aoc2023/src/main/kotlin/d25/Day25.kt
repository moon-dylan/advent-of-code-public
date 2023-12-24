package d25

import aoc.AdventOfCodeDay
import aoc.trimLinesAndFilterNotBlank

typealias InputData = List<String>

object Day25 : AdventOfCodeDay<InputData>() {
    override val inputFile = this::class.getInputFile(isActualInput = false)
//    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {

        return lines.trimLinesAndFilterNotBlank()
    }

    override fun solvePart1(): Any {

        return TODO()
    }

    override fun solvePart2(): Any {

        return TODO()
    }
}
