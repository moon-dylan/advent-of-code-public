package d04

import aoc.AdventOfCodeDay

typealias InputData = List<Day4.Card>

object Day4 : AdventOfCodeDay<InputData>() {
    override val inputFile = this::class.getInputFile(isActualInput = false)
//    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines
            .map { it.split(":")[1].split("|") }
            .map { (winners, numbers) ->
                Card(
                    winningNumbers = winners.trim().split(" +".toRegex()).map { it.toInt() }.toSet(),
                    cardNumbers = numbers.trim().split(" +".toRegex()).map { it.toInt() }.toSet(),
                )
            }

    override fun solvePart1(): Any =
        input
            .map { it.winningNumbers.intersect(it.cardNumbers).size }
            .sumOf { if (it == 0) 0 else 0x1.shl(it - 1) }

    override fun solvePart2(): Any =
        input.foldIndexed(input.indices.associateWith { 1 }) { index, acc, card ->
            val multiplier = acc[index]!!
            val winningCount = card.winningNumbers.intersect(card.cardNumbers).size
            acc + (1..winningCount).map { it + index }.map { it to acc[it]!! + multiplier }
        }.values.sum()

    data class Card(
        val winningNumbers: Set<Int>,
        val cardNumbers: Set<Int>,
    )
}