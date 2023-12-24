package d01

import aoc.AdventOfCodeDay
import collections.toNonEmpty
import strings.tails


typealias InputData = List<String>

object Day1 : AdventOfCodeDay<InputData>() {
    override val inputFile = this::class.getInputFile("rasmusActualInput.txt")
//    override val inputFile = this::class.getInputFile(isActualInput = false)
//    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData = lines

    override fun solvePart1() =
        input
            .map { line -> line.filter { it.isDigit() } }
            .mapNotNull { it.toNonEmpty() }
            .map { (it.first.toString() + it.last) }
            .sumOf { it.toInt() }

    override fun solvePart2() =
        input
            .map { digitCharsOf(it) }
            .mapNotNull { it.toNonEmpty() }
            .map { (it.first.toString() + it.last) }
            .sumOf { it.toInt() }

    private fun digitCharsOf(line: String): List<Char> =
        line.tails
            .mapNotNull { "^(\\d|one|two|three|four|five|six|seven|eight|nine)".toRegex().find(it)?.value }
            .map {
                when (it) {
                    "one" -> '1'
                    "two" -> '2'
                    "three" -> '3'
                    "four" -> '4'
                    "five" -> '5'
                    "six" -> '6'
                    "seven" -> '7'
                    "eight" -> '8'
                    "nine" -> '9'
                    else -> it.first()
                }
            }
}
