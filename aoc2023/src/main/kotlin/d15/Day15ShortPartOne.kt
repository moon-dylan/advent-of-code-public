package d15

import aoc.AdventOfCodeDay

object Day15ShortPartOne : AdventOfCodeDay<String>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): String = text


    override fun solvePart1(): Int =
        input.split(",").sumOf { l -> l.fold(0 as Int) { a, c -> (a + c.code) * 17 % 256 } }

    override fun solvePart2(): Any = TODO()
}
