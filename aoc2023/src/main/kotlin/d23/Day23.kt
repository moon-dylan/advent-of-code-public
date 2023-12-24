package d23

import aoc.AdventOfCodeDay
import aoc.trimLinesAndFilterNotBlank
import collections.filterSecondsNotNull
import collections.withGridIndex
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.plus
import tuples.y

typealias XY = Vec2<Int>

object Day23 : AdventOfCodeDay<Day23.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val grid = lines.trimLinesAndFilterNotBlank().withGridIndex().flatten()

        val walkable =
            grid.filter { (_, c) -> c in ".><v^" }
                .map { (xy, _) -> xy }
                .toSet()

        val coordToSlope =
            grid
                .map { (xy, c) ->
                    xy to when (c) {
                        '<' -> -1 to 0
                        '^' -> 0 to -1
                        '>' -> 1 to 0
                        'v' -> 0 to 1
                        else -> null
                    }
                }
                .filterSecondsNotNull()
                .toMap()

        return InputData(
            walkable = walkable,
            coordToSlopeDirection = coordToSlope,
            start = walkable.minBy { it.y },
            end = walkable.maxBy { it.y },
        )
    }

    override fun solvePart1(): Any {
        val working = ArrayDeque(listOf(input.start to listOf(input.start)))
        var longestFound: Pair<XY, List<XY>>? = null

        while (working.isNotEmpty()) {
            val (currentCoord, currentPath) = working.removeFirst()

            if (currentCoord == input.end) {
                if (longestFound?.second.orEmpty().size < currentPath.size) {
                    longestFound = currentCoord to currentPath
                }
                continue
            }

            val nextStates =
                currentCoord.neighbourhood
                    .intersect(input.walkable)
                    .filter { it !in currentPath }
                    .map { nextCoord ->
                        listOfNotNull(nextCoord, input.coordToSlopeDirection[nextCoord]?.let { it + nextCoord })
                    }
                    .filter { it.last() !in currentPath }
                    .map { it.last() to (currentPath + it) }

            working.addAll(nextStates)
        }

        return (longestFound?.second?.size ?: 0) - 1
    }

    override fun solvePart2(): Any {
        val xyToEdges = getEdgeMap()

        val working = ArrayDeque(listOf(input.start to listOf(input.start)))
        var longestFound: Pair<XY, Set<XY>>? = null

        while (working.isNotEmpty()) {
            val (currentCoord, currentIntersectionPath) = working.removeFirst()

            if (currentCoord == input.end) {
                val actualPath =
                    currentIntersectionPath.zipWithNext()
                        .fold(setOf<XY>()) { acc, (source, target) ->
                            acc + xyToEdges[source]!!.first { it.target == target }.coordinates
                        }
                if (longestFound?.second.orEmpty().size < actualPath.size) {
                    longestFound = currentCoord to actualPath
                }
                continue
            }

            val nextStates =
                xyToEdges[currentCoord]!!
                    .filter { it.target !in currentIntersectionPath }
                    .map { it.target to currentIntersectionPath + it.target }

            working.addAll(nextStates)
        }

        return (longestFound?.second?.size ?: 0) - 1
    }

    private fun getEdgeMap(): Map<XY, Set<DirectedEdge>> {
        val intersections =
            input.walkable
                .filter { 2 < it.neighbourhood.intersect(input.walkable).size }
                .toSet() + input.start + input.end

        val exploredIntersections = mutableSetOf<XY>()
        val xyToEdges = mutableMapOf<XY, Set<DirectedEdge>>()

        val working = ArrayDeque(listOf(input.start))

        while (working.isNotEmpty()) {
            val currentCoord = working.removeFirst()

            if (currentCoord in exploredIntersections) continue
            exploredIntersections += currentCoord

            val exploredSteps = xyToEdges[currentCoord].orEmpty().flatMap { it.coordinates }.toSet()

            currentCoord.neighbourhood.intersect(input.walkable).subtract(exploredSteps)
                .mapNotNull { newStep ->
                    val path = mutableListOf(currentCoord, newStep)
                    while (path.last() !in intersections) {
                        path +=
                            path.last().neighbourhood.intersect(input.walkable)
                                .firstOrNull { it !in path }
                                ?: break
                    }
                    path.takeIf { it.last() in intersections }?.toList()
                }
                .forEach { path ->
                    val other = path.last()
                    val asSet = path.toSet()
                    xyToEdges[other] = xyToEdges[other].orEmpty() + DirectedEdge(currentCoord, asSet)
                    xyToEdges[currentCoord] = xyToEdges[currentCoord].orEmpty() + DirectedEdge(other, asSet)
                    working.add(other)
                }
        }

        return xyToEdges
    }

    private data class DirectedEdge(
        val target: XY,
        val coordinates: Set<XY>,
    )

    private val DIRECTIONS = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    private val XY.neighbourhood get() = DIRECTIONS.map { it + this }

    data class InputData(
        val walkable: Set<XY>,
        val coordToSlopeDirection: Map<XY, XY>,
        val start: XY,
        val end: XY,
    )
}
