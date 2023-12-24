package d21

import aoc.AdventOfCodeDay
import aoc.trimLinesAndFilterNotBlank
import collections.withGridIndex
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.plus
import tuples.vectors.map
import tuples.x
import tuples.y
import java.math.BigInteger
import kotlin.time.measureTimedValue

typealias XY = Pair<Int, Int>

object Day21 : AdventOfCodeDay<Day21.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    // TODO: remove grouping in iterations
    // TODO: replace iterating with computations
    // TODO: add support for test data in part 2 if only minor changes needed

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val grid = lines.trimLinesAndFilterNotBlank()
        val indexed = grid.withGridIndex().flatten()
        val width = grid.first().length
        val height = grid.size
        return InputData(
            rocks = indexed.filter { (_, c) -> c == '#' }.map { (xy, _) -> xy }.toSet(),
            start = indexed.first { (_, c) -> c == 'S' }.first,
            width = width,
            height = height,
            xRange = 0 ..< width,
            yRange = 0 ..< height,
        )
    }

    override fun solvePart1(): Any =
        (1 .. 64).fold(setOf(input.start)) { current, _ -> current.iterate() }.size

    private const val STEPS = 26_501_365

    override fun solvePart2(): Any {
        if (input.width != input.height) error("Not supported, too lazy :/")

        println("Starting analysis")
        val tileHistories =
            measureTimedValue { analyseTileHistories() }
                .also { println("Spent %,d ms for analysis".format(it.duration.inWholeMilliseconds)) }
                .value

        return solveWithLargeSteps(tileHistories)
    }

    private fun solveWithLargeSteps(tileHistories: TileHistorySummary): BigInteger {
        val filledStates = getFilledStates().map { it.size.toBigInteger() }
        val centerSizeAtStep = tileHistories.center.stateSizes.last().let { firstFilledSize ->
            if ((STEPS - tileHistories.center.stateSizes.indices.last) % 2 == 0) {
                firstFilledSize
            } else when (firstFilledSize) {
                filledStates.first -> filledStates.second
                filledStates.second -> filledStates.first
                else -> error("No such filled state size!")
            }
        }

        val summedFilledStates = filledStates.map { it * 4.toBigInteger() }
        fun getOtherFilledState(filledStateSize: BigInteger): BigInteger =
            when (filledStateSize) {
                summedFilledStates.first -> summedFilledStates.second
                summedFilledStates.second -> summedFilledStates.first
                else -> error("No such filled state size!")
            }

        val straightsSizeTotalAtStep = tileHistories.straightSum.let { tileHistory ->
            tileHistory.countTileSizes(
                initialRemainingSteps = STEPS - tileHistory.startIterationIndex,
                stepDelta = input.width,
                firstFilledSize = tileHistory.stateSizes.last(),
                otherFilledSize = getOtherFilledState(tileHistory.stateSizes.last()),
            )
        }

        val diagonalsSizeTotalAtStep = tileHistories.diagonalSum.let { tileHistory ->
            val stepDelta = input.width + input.height
            val firstFilledSize = tileHistory.stateSizes.last()
            val otherFilledSize = getOtherFilledState(tileHistory.stateSizes.last())

            val mainDiagonalSize = tileHistory.countTileSizes(
                initialRemainingSteps = STEPS - tileHistory.startIterationIndex,
                stepDelta = stepDelta,
                firstFilledSize = firstFilledSize,
                otherFilledSize = otherFilledSize,
            )

            val halfSize = kotlin.run {
                var remainingSteps = STEPS - tileHistory.startIterationIndex - input.width
                var totalSize = BigInteger.ZERO

                while (0 <= remainingSteps) {
                    totalSize += tileHistory.countTileSizes(
                        initialRemainingSteps = remainingSteps,
                        stepDelta = stepDelta,
                        firstFilledSize = firstFilledSize,
                        otherFilledSize = otherFilledSize,
                    )
                    remainingSteps -= input.width
                }

                totalSize
            }

            mainDiagonalSize + BigInteger.TWO * halfSize
        }

        return centerSizeAtStep + straightsSizeTotalAtStep + diagonalsSizeTotalAtStep
    }

    private fun TileHistorySummary.Entry.countTileSizes(
        initialRemainingSteps: Int,
        stepDelta: Int,
        firstFilledSize: BigInteger,
        otherFilledSize: BigInteger,
    ): BigInteger {
        val stepsTillOtherFilled = stateSizes.indices.last

        var remainingSteps = initialRemainingSteps
        var totalSize = BigInteger.ZERO

        while (0 <= remainingSteps) {
            totalSize +=
                if (remainingSteps < stepsTillOtherFilled) {
                    stateSizes[remainingSteps]
                } else if ((remainingSteps - stepsTillOtherFilled) % 2 == 0) {
                    firstFilledSize
                } else {
                    otherFilledSize
                }

            remainingSteps -= stepDelta
        }

        return totalSize
    }

    data class TileHistorySummary(
        val center: Entry,
        val straightSum: Entry,
        val diagonalSum: Entry,
    ) {
        data class Entry(
            val startIterationIndex: Int,
            val stateSizes: List<BigInteger>,
        )
    }

    private fun analyseTileHistories(): TileHistorySummary {
        data class TileHistory(
            val startIterationIndex: Int,
            val states: List<Set<XY>>,
        )

        val filledTiles = getFilledStates().toList()

        val tileCoordToHistory = mutableMapOf(
            (0 to 0) to TileHistory(startIterationIndex = 0, states = listOf(setOf(input.start))),
        )
        val filledTileCoords = mutableSetOf<XY>()
        val requiredTileCoords =
            setOf(0 to 0, 1 to 0, -1 to 0, 0 to -1, 0 to 1, 1 to 1, 1 to -1, -1 to 1, -1 to -1)

        val maxIterationsCount = 400
        (1 .. maxIterationsCount)
            .asSequence()
            .takeWhile { (requiredTileCoords - filledTileCoords).isNotEmpty() }
            .fold(
                mapOf(input.start to setOf(0 to 0)),
            ) { coordsToTileCoords, iterationIndex ->
                val iterated = coordsToTileCoords.iterate()

                iterated.values.flatten().distinct()
                    .filter { it !in filledTileCoords }
                    .forEach { tileCoord ->
                        val currentTileHistory =
                            tileCoordToHistory.computeIfAbsent(tileCoord) {
                                TileHistory(
                                    startIterationIndex = iterationIndex,
                                    states = emptyList(),
                                )
                            }

                        val collectedCoords =
                            iterated.entries
                                .filter { tileCoord in it.value }
                                .map { it.key }
                                .toSet()

                        if (collectedCoords in filledTiles) filledTileCoords.add(tileCoord)

                        tileCoordToHistory[tileCoord] =
                            currentTileHistory.copy(
                                states = currentTileHistory.states.plusElement(collectedCoords),
                            )
                    }

                iterated
            }

        fun TileHistory.toEntry() =
            TileHistorySummary.Entry(startIterationIndex, states.map { it.size.toBigInteger() })

        fun List<XY>.toEntrySum() =
            map { tileCoordToHistory[it]!!.toEntry() }.reduce { xs, ys ->
                if (xs.startIterationIndex != ys.startIterationIndex) error("Does not meet assumptions!")
                xs.copy(
                    stateSizes = xs.stateSizes.zip(ys.stateSizes) { x, y -> x + y }
                )
            }

        return TileHistorySummary(
            center = tileCoordToHistory[0 to 0]!!.toEntry(),
            straightSum = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).toEntrySum(),
            diagonalSum = listOf(-1 to -1, 1 to 1, 1 to -1, -1 to 1).toEntrySum(),
        )
    }

    private fun getFilledStates(): Vec2<Set<XY>> {
        var lastTwoStates = setOf(input.start) to setOf(input.start).iterate()

        while (true) {
            val nextState = lastTwoStates.second.iterate()
            if (lastTwoStates.first == nextState) break
            lastTwoStates = lastTwoStates.second to nextState
        }

        return lastTwoStates
    }

    private fun Set<XY>.iterate(): Set<XY> =
        flatMap { it.neighbourhood }
            .filter { it.x in input.xRange && it.y in input.yRange }
            .toSet()
            .minus(input.rocks)

    private fun Map<XY, Set<XY>>.iterate(): Map<XY, Set<XY>> =
        flatMap { (xy, repetitionCoords) ->
            xy.neighbourhood
                .mapNotNull { (stepX, stepY) ->
                    val (newX, repDeltaX) =
                        if (stepX < 0) {
                            stepX + input.width to -1
                        } else if (input.width <= stepX) {
                            stepX % input.width to 1
                        } else {
                            stepX to 0
                        }
                    val (newY, repDeltaY) =
                        if (stepY < 0) {
                            stepY + input.height to -1
                        } else if (input.height <= stepY) {
                            stepY % input.height to 1
                        } else {
                            stepY to 0
                        }
                    val newXy = newX to newY
                    if (newXy in input.rocks) {
                        null
                    } else {
                        newXy to repetitionCoords.map { it + (repDeltaX to repDeltaY) }.toSet()
                    }
                }
                .filter { (xy, _) -> xy !in input.rocks }
        }
            .groupBy(
                keySelector = { (xy, _) -> xy },
                valueTransform = { (_, reps) -> reps },
            )
            .mapValues { (_, repsList) -> repsList.flatten().toSet() }

    private val DIRECTIONS = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    private val XY.neighbourhood get() = DIRECTIONS.map { it + this }

    data class InputData(
        val rocks: Set<XY>,
        val start: XY,
        val width: Int,
        val height: Int,
        val xRange: IntRange,
        val yRange: IntRange,
    )
}
