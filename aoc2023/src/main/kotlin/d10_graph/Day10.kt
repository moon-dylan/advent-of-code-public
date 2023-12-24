package d10_graph

import aoc.AdventOfCodeDay
import collections.*
import sequences.filterSecondsNotNull
import sequences.mapSeconds
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.plus

typealias XY = Vec2<Int>

// TODO: part 2 is broken
object Day10 : AdventOfCodeDay<Day10.InputData>() {
    //        override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val tilesWithIndices = lines.withGridIndex().flatten()

        val start = tilesWithIndices.first { (_, c) -> c == 'S' }.first

        val connectedPipes =
            tilesWithIndices
                .asSequence()
                .mapSeconds { c ->
                    when (c) {
                        '|' -> listOf(0 to 1, 0 to -1)
                        '-' -> listOf(-1 to 0, 1 to 0)
                        'L' -> listOf(1 to 0, 0 to -1)
                        'J' -> listOf(-1 to 0, 0 to -1)
                        '7' -> listOf(-1 to 0, 0 to 1)
                        'F' -> listOf(1 to 0, 0 to 1)
                        'S' -> listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
                        else -> null
                    }
                }
                .filterSecondsNotNull()
                .flatMap { (xy, directions) -> directions.map { xy to xy + it } }
                .map { (source, destination) -> setOf(source, destination) }
                .groupBy { it }
                .filter { it.value.size == 2 }
                .keys

        val coordinateToAdjacent =
            connectedPipes
                .flatten()
                .distinct()
                .associateWith { coord ->
                    connectedPipes.filter { coord in it }.flatten().toSet().minus(coord)
                }

        val mainLoopPipeConnections =
            coordinateToAdjacent
                .getMainLoopPath(start)
                .let { it + it.last }
                .zipWithNext { a, b -> setOf(a, b) }
                .toSet()

        return InputData(
            start = tilesWithIndices.first { (_, c) -> c == 'S' }.first,
            mainLoopPipeConnections = mainLoopPipeConnections,
            coordinateToAdjacent = coordinateToAdjacent,
        )
    }

    override fun solvePart1(): Any = input.mainLoopPipeConnections.size / 2

    override fun solvePart2(): Any {
        val mainLoopCoordinates = input.mainLoopPipeConnections.asSequence().flatten().toSet()
        val maxX = mainLoopCoordinates.maxOf { it.first }
        val maxY = mainLoopCoordinates.maxOf { it.second }

        return (0 .. maxX).product(0 .. maxY).toSet()
            .minus(getAccessibleCoordinates()) // TODO: fix this
            .minus(mainLoopCoordinates)
            .size
    }

    private fun getAccessibleCoordinates(): Set<XY> {
        val mainLoopCoordinates = input.mainLoopPipeConnections.asSequence().flatten().toSet()
        val xRange = -1 .. mainLoopCoordinates.maxOf { it.first }
        val yRange = -1 .. mainLoopCoordinates.maxOf { it.second }

        val collected = buildSet {
            tailrec fun rec(adding: NonEmptyList<XY>) {
                addAll(adding)
                rec(
                    adding
                        .map { getAccessibleTiles(it) }
                        .flatten()
                        .toSet()
                        .filter { (x, y) -> x in xRange && y in yRange }
                        .minus(this)
                        .toNonEmpty()
                        ?: return
                )
            }

            rec(NonEmptyList(0 to 0))
        }

        return collected
            .asSequence()
            .filter { topLeft ->
                collected.containsAll(
                    listOf(0 to 0, 1 to 0, 0 to 1, 1 to 1).map { topLeft + it }
                )
            }
            .toSet()
    }

    private fun getAccessibleTiles(from: XY): Set<XY> =
        setOfNotNull(
            (from + (1 to 0)).takeIf {
                setOf(from, from + (0 to -1)) !in input.mainLoopPipeConnections
            },
            (from + (-1 to 0)).takeIf { left ->
                setOf(left, left + (0 to -1)) !in input.mainLoopPipeConnections
            },
            (from + (0 to 1)).takeIf {
                setOf(from + (-1 to 0), from) !in input.mainLoopPipeConnections
            },
            (from + (0 to -1)).takeIf { up ->
                setOf(up + (-1 to 0), up) !in input.mainLoopPipeConnections
            },
        )

    private fun Map<XY, Set<XY>>.getMainLoopPath(start: XY): NonEmptyList<XY> {
        tailrec fun rec(currentPaths: NonEmptyList<NonEmptyList<XY>>): NonEmptyList<XY> {
            val newPaths =
                currentPaths.flatMap { path ->
                    val (previous, current) = path.takeLast(2)
                    get(current)
                        .orEmpty()
                        .filter { it !in path || it == start && it != previous }
                        .map { path to it }
                }

            return newPaths.firstOrNull { it.second == start }?.first
                ?: rec(newPaths.toNonEmpty()!!.map { (path, added) -> path + added })
        }

        return rec(get(start)!!.toNonEmpty()!!.map { NonEmptyList(start, it) })
    }

    data class InputData(
        val start: XY,
        val mainLoopPipeConnections: Set<Set<XY>>,
        val coordinateToAdjacent: Map<XY, Set<XY>>,
    )
}