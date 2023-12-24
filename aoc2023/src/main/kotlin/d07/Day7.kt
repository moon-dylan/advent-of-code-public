package d07

import aoc.AdventOfCodeDay
import collections.*

typealias InputData = List<Day7.Hand>

object Day7 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines
            .map { it.split(" ").toPair() }
            .mapFirsts { it.trim().toList() }
            .map { (cards, bidPart) ->
                Hand(
                    cards = cards,
                    cardToCount = cards.toFrequencyMap(),
                    bid = bidPart.trim().toLong(),
                )
            }

    override fun solvePart1(): Any =
        input
            .sortedByList { it.cards.map(RANKS::indexOf) }
            .sortedBy { it.cardToCount.getHandType().ordinal }
            .mapIndexed { i, hand -> (i + 1) * hand.bid }
            .sum()

    override fun solvePart2(): Any =
        input
            .sortedByList { it.cards.replace('J', '1').map(RANKS::indexOf) }
            .sortedBy { it.codeToCountWithWildcardReplaced.getHandType().ordinal }
            .mapIndexed { i, hand -> (i + 1) * hand.bid }
            .sum()

    private const val RANKS = "123456789TJQKA"

    private val Hand.codeToCountWithWildcardReplaced: Map<Char, Int>
        get() {
            if (cardToCount.size == 1) return cardToCount
            val jokerCount = cardToCount['J'] ?: 0
            val cardToCount = cardToCount.minus('J')
            val mostFrequent = cardToCount.maxBy { it.value }
            return cardToCount.plus(mostFrequent.key to mostFrequent.value + jokerCount)
        }

    private fun Map<Char, Int>.getHandType(): HandType =
        when (values.sortedDescending()) {
            listOf(5) -> HandType.FIVE_OF_A_KIND
            listOf(4, 1) -> HandType.FOUR_OF_A_KIND
            listOf(3, 2) -> HandType.FULL_HOUSE
            listOf(3, 1, 1) -> HandType.THREE_OF_A_KIND
            listOf(2, 2, 1) -> HandType.TWO_PAIR
            listOf(2, 1, 1, 1) -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }

    data class Hand(
        val cards: List<Char>,
        val cardToCount: Map<Char, Int>,
        val bid: Long,
    )

    enum class HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }
}
