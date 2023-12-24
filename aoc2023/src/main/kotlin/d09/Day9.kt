package d09

import aoc.AdventOfCodeDay

typealias InputData = List<List<List<Long>>>

object Day9 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines
            .map { it.split("\\s+".toRegex()) }
            .map { line -> line.map { it.trim().toLong() } }
            .map { getDifferences(it) }

    override fun solvePart1(): Any =
        input.sumOf { diffs ->
            diffs.reversed()
                .map { it.last() }
                .fold(0L) { prevLast, last -> last + prevLast }
        }

    override fun solvePart2(): Any =
        input.sumOf { diffs ->
            diffs.reversed()
                .map { it.first() }
                .fold(0L) { diff, first -> first - diff }
        }

    private tailrec fun getDifferences(
        history: List<Long>,
        collected: List<List<Long>> = emptyList(),
    ): List<List<Long>> {
        val added = collected.plusElement(history)

        return if (history.all { it == 0L }) {
            added
        } else {
            getDifferences(history.zipWithNext { a, b -> b - a }, added)
        }
    }
}