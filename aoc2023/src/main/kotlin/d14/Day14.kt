package d14

import aoc.AdventOfCodeDay
import collections.getFirstRepetitionPatternIndexAndSize
import collections.withGridIndex
import tuples.x
import tuples.y

typealias XY = Pair<Int, Int>

object Day14 : AdventOfCodeDay<Day14.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val indexed = lines.withGridIndex().flatten()

        return InputData(
            roundRocks = indexed.filter { (_, c) -> c == 'O' }.map { it.first }.toSet(),
            squareRocks = indexed.filter { (_, c) -> c == '#' }.map { it.first }.toSet(),
            height = lines.size,
            width = lines[0].length,
        )
    }

    override fun solvePart1(): Any = input.roundRocks.tiltNorth().sumOf { (_, y) -> input.height - y }
    override fun solvePart2(): Any = input.roundRocks.iterateWhileChanging()

    private const val PART_2_CYCLE_COUNT = 1_000_000_000

    private fun Set<XY>.iterateWhileChanging(): Int {
        val previousResults = mutableListOf<Int>()

        return (0 ..< PART_2_CYCLE_COUNT).fold(this) { acc, i ->
            val result = acc.tiltNorth().tiltWest().tiltSouth().tiltEast()

            previousResults.add(result.sumOf { (_, y) -> input.height - y })
            previousResults.getFirstRepetitionPatternIndexAndSize()
                ?.let { (patternStart, patternSize) ->
                    val cyclesLeftAtPatternStart = PART_2_CYCLE_COUNT - (patternStart + 1)
                    return previousResults[patternStart + (cyclesLeftAtPatternStart % patternSize)]
                }

            if (result == acc) return result.sumOf { (_, y) -> input.height - y }
            result
        }.sumOf { (_, y) -> input.height - y }
    }

    private fun Set<XY>.tiltNorth(): Set<XY> =
        sortedBy { it.y }.fold(emptySet()) { acc, (x, y) ->
            (0 .. y).reversed()
                .map { x to it }
                .takeWhile { it !in acc && it !in input.squareRocks }
                .last()
                .let { acc + it }
        }

    private fun Set<XY>.tiltWest(): Set<XY> =
        sortedBy { it.x }.fold(emptySet()) { acc, (x, y) ->
            (0 .. x).reversed()
                .map { it to y }
                .takeWhile { it !in acc && it !in input.squareRocks }
                .last()
                .let { acc + it }
        }

    private fun Set<XY>.tiltSouth(): Set<XY> =
        sortedByDescending { it.y }.fold(emptySet()) { acc, (x, y) ->
            (y ..< input.height)
                .map { x to it }
                .takeWhile { it !in acc && it !in input.squareRocks }
                .last()
                .let { acc + it }
        }

    private fun Set<XY>.tiltEast(): Set<XY> =
        sortedByDescending { it.x }.fold(emptySet()) { acc, (x, y) ->
            (x ..< input.width)
                .map { it to y }
                .takeWhile { it !in acc && it !in input.squareRocks }
                .last()
                .let { acc + it }
        }

    data class InputData(
        val roundRocks: Set<XY>,
        val squareRocks: Set<XY>,
        val width: Int,
        val height: Int,
    )
}