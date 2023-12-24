package d03

import aoc.AdventOfCodeDay

typealias InputData = Day3.InputData

object Day3 : AdventOfCodeDay<InputData>() {
    override val inputFile = this::class.getInputFile(isActualInput = false)
//    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        InputData(
            lines = lines,
            symbolCoords = lines.flatMapIndexed { y, line ->
                "[^0-9.]".toRegex().findAll(line).map { y to it.range.single() }
            },
            numberCoords = lines.flatMapIndexed { y, line ->
                "[0-9]+".toRegex().findAll(line).map { y to it.range }
            },
        )

    override fun solvePart1(): Any =
        input.numberCoords
            .filter { (y, range) ->
                input.symbolCoords
                    .filter { it.first in y - 1..y + 1 }
                    .any { it.second in range.first - 1..range.last + 1 }
            }
            .sumOf { (y, range) -> input.lines[y].substring(range).toInt() }

    override fun solvePart2(): Any =
        input.symbolCoords
            .filter { (y, x) -> input.lines[y][x] == '*' }
            .sumOf { (y, x) ->
                input.numberCoords
                    .filter { it.first in y - 1..y + 1 }
                    .filter { (_, range) -> x in range.first - 1..range.last + 1 }
                    .map { input.lines[it.first].substring(it.second).toInt() }
                    .takeIf { it.size == 2 }
                    ?.let { (a, b) -> a * b } ?: 0
            }

    data class InputData(
        val lines: List<String>,
        val symbolCoords: List<Pair<Int, Int>>,
        val numberCoords: List<Pair<Int, IntRange>>,
    )
}