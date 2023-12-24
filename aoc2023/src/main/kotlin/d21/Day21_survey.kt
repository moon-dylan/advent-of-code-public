package d21

import aoc.AdventOfCodeDay
import collections.product
import collections.withGridIndex
import tuples.vectors.arithmetic.plus

object Day21_survey : AdventOfCodeDay<Day21_survey.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val grid = lines.map { it.trim() }.filter { it.isNotBlank() }
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

    override fun solvePart1(): Any = -1

    data class TileHistory(
        val startIterationIndex: Int,
        val states: List<Set<XY>>,
    )

    override fun solvePart2(): Any {
        val filledTiles =
            listOf(0, 1)
                .map { remainder ->
                    input.xRange.product(input.yRange)
                        .filter { (x, y) -> (x + y) % 2 == remainder }
                        .toSet()
                }
                .map { it - input.rocks }

        val tileCoordToHistory = mutableMapOf<XY, TileHistory>()
        val filledTileCoords = mutableSetOf<XY>()

        val iterationCount = 900
        (1 .. iterationCount)
            .fold(
                mapOf(input.start to setOf(0 to 0)),
            ) { coordsToTileCoords, iterationIndex ->
                /*if (iterationIndex % 100 == 0) {
                    println("At iteration %,d".format(iterationIndex))
                }*/
                val iterated = coordsToTileCoords.iterate()

                iterated.values.flatten().distinct().filter { it !in filledTileCoords }.forEach { tileCoord ->
                    val history =
                        tileCoordToHistory.computeIfAbsent(tileCoord) {
                            TileHistory(startIterationIndex = iterationIndex, states = emptyList())
                        }

                    val collectedCoords =
                        iterated.entries
                            .filter { tileCoord in it.value }
                            .map { it.key }
                            .toSet()

                    if (collectedCoords in filledTiles) filledTileCoords.add(tileCoord)

                    tileCoordToHistory[tileCoord] = history.copy(states = history.states.plusElement(collectedCoords))
                }

                iterated
            }

        println("Performed $iterationCount iterations")
        println("Calculated history for ${tileCoordToHistory.keys.size} tiles: ${tileCoordToHistory.keys}")
        println("Found ${tileCoordToHistory.values.map { it.states }.toSet().size} different histories")
        println("Found ${tileCoordToHistory.values.map { it.states.size }.toSet().size} different history sizes")
        println("Found ${tileCoordToHistory.values.map { it.startIterationIndex }.toSet().size} different start indices")

        println("Found following groupings by history:")
        tileCoordToHistory.entries
            .groupBy { it.value.states }
            .mapValues { (_, pairs) ->
                pairs
                    .groupBy { (_, history) -> history.startIterationIndex }
                    .mapValues { (_, pairs) ->
                        pairs.map { (tileCoord, _) -> tileCoord }.toSet()
                    }
            }
            .onEachIndexed { i, (history, startIndexToTileCoords) ->
                println(
                    "%d: %, 4d(size) %s:".format(
                        i,
                        history.size,
                        if (startIndexToTileCoords.values.flatten().any { it in filledTileCoords })
                            "FILLED"
                        else
                            "NOT FILLED",
                    )
                )

                startIndexToTileCoords.forEach { (start, tileCoords) ->
                    println("    %, 5d(start) group=$tileCoords".format(start))
                }
            }

        return tileCoordToHistory.values.size
    }

    private fun Map<XY, Set<XY>>.iterate(): Map<XY, Set<XY>> =
        flatMap { (xy, repetitionCoords) ->
            DIRECTIONS
                .map { it + xy }
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

    data class InputData(
        val rocks: Set<XY>,
        val start: XY,
        val width: Int,
        val height: Int,
        val xRange: IntRange,
        val yRange: IntRange,
    )
}
