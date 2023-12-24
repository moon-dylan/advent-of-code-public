package d06

import aoc.AdventOfCodeDay

object Day6 : AdventOfCodeDay<Day6.InputData>() {
    //        override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val durationsPart = lines[0].drop("Time:".length).trim()
        val distancesPart = lines[1].drop("Distance:".length).trim()

        return InputData(
            original = durationsPart.split(" +".toRegex()).map { it.toLong() }
                .zip(distancesPart.split(" +".toRegex()).map { it.toLong() })
                .map { (duration, distance) -> Race(duration, distance) },
            alt = Race(
                duration = durationsPart.replace(" ", "").toLong(),
                distance = distancesPart.replace(" ", "").toLong(),
            ),
        )
    }

    override fun solvePart1(): Any =
        input.original
            .map { it.getPossibleRecordStrategiesCount() }
            .fold(1L) { acc, count -> acc * count.toLong() }

    override fun solvePart2(): Any =
        input.alt.getPossibleRecordStrategiesCount()

    private fun Race.getPossibleRecordStrategiesCount(): Int =
        (1 ..< duration + 1)
            .filter { pressTime ->
                val remainingTime = duration.toBigInteger() - pressTime.toBigInteger()
                distance.toBigInteger() < pressTime.toBigInteger() * remainingTime
            }
            .size

    data class InputData(
        val original: List<Race>,
        val alt: Race,
    )

    data class Race(
        val duration: Long,
        val distance: Long,
    )
}