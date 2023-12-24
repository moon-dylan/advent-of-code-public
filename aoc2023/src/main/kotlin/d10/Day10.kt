package d10

import aoc.AdventOfCodeDay
import collections.*
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.div
import tuples.vectors.arithmetic.minus
import tuples.vectors.arithmetic.plus
import tuples.vectors.arithmetic.times
import java.util.*
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull


typealias XY = Vec2<Int>

object Day10 : AdventOfCodeDay<Day10.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        fun Char.toStartOrPipe(): Optional<Pipe>? =
            if (this == 'S')
                Optional.empty()
            else
                Pipe.entries.firstOrNull { it.symbol == this }?.let { Optional.of(it) }

        val allPipes =
            lines
                .toGridIndexed()
                .mapSecondsNotNull { it.toStartOrPipe() }
                .toMap()

        val start = allPipes.entries.first { it.value.isEmpty }.key

        return InputData(
            gridWidth = lines.firstOrNull().orEmpty().length,
            gridHeight = lines.size,
            startCoordinate = start,
            allPipes = allPipes,
            mainLoopCoordinates = allPipes.getMainLoopPath(start),
        )
    }

    private fun List<String>.toGridIndexed(): List<Pair<XY, Char>> =
        flatMapIndexed { y, line ->
            line.mapIndexed { x, symbol -> (x to y) to symbol }
        }

    private fun Map<XY, Optional<Pipe>>.getMainLoopPath(start: XY): NonEmptyList<XY> {
        tailrec fun rec(currentPaths: List<NonEmptyList<XY>>): NonEmptyList<XY> {
            val newPaths =
                currentPaths.flatMap { path ->
                    val (previous, current) = path.takeLast(2)
                    getConnectedAdjacentCoordinates(current)
                        .filter { it !in path || it == start && it != previous }
                        .map { path to it }
                }

            return newPaths.firstOrNull { it.second == start }?.first
                ?: rec(newPaths.map { (path, added) -> path + added })
        }

        return rec(getConnectedAdjacentCoordinates(start).map { NonEmptyList(start, it) })
    }

    override fun solvePart1(): Any = input.mainLoopCoordinates.size / 2

    override fun solvePart2(): Any {
        val expandedToInitial = getExpandedToInitialMap()
        val expandedPipeCoords = expandedToInitial.keys.flatten().toSet()
        val allExpandedCoords =
            (-1 .. input.gridWidth * 3 + 1)
                .product(-1 .. input.gridHeight * 3 + 1)

        val accessibleFromOutsideCoords =
            allExpandedCoords.minus(expandedPipeCoords).toSet().takeAccessibleFrom(-1 to -1)

        return allExpandedCoords
            .minus(accessibleFromOutsideCoords)
            .map { it / 3 }
            .toSet()
            .minus(expandedToInitial.values.toSet())
            .size
    }

    private fun Set<XY>.takeAccessibleFrom(from: XY): Set<XY> {
        val offsets = listOf(1 to 0, -1 to 0, 0 to -1, 0 to 1)
        val nonPipes = this@takeAccessibleFrom
        var working = setOf(from)

        return buildSet {
            while (working.isNotEmpty()) {
                addAll(working)
                working = working
                    .flatMap { xy -> offsets.map { it + xy } }
                    .intersect(nonPipes)
                    .minus(this)
            }
        }
    }

    private fun getExpandedToInitialMap(): Map<List<XY>, XY> =
        input.mainLoopCoordinates
            .map { it to (input.allPipes[it]!!.getOrNull() ?: input.startPipeType) }
            .associate { (xy, pipe) ->
                val centerOffset = xy * 3 + (1 to 1)
                val expandedShape =
                    (listOf(0 to 0) + pipe.accessibleOffset).map { centerOffset + it }

                expandedShape to xy
            }

    private fun Map<XY, Optional<Pipe>>.getConnectedAdjacentCoordinates(from: XY): List<XY> =
        get(from)!!.accessibleOffset
            .map { offset -> from + offset }
            .map { it to get(it) }
            .filterSecondsNotNull()
            .filter { (xy, value) -> value.accessibleOffset.any { from == xy + it } }
            .map { it.first }

    private val Optional<Pipe>.accessibleOffset
        get() = map { it.accessibleOffset }.getOrElse { listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1) }

    data class InputData(
        val gridWidth: Int,
        val gridHeight: Int,
        val startCoordinate: XY,
        val allPipes: Map<XY, Optional<Pipe>>,
        val mainLoopCoordinates: NonEmptyList<XY>,
    ) {
        val startPipeType: Pipe by lazy {
            val previous = input.mainLoopCoordinates.last
            val next = input.mainLoopCoordinates[1]
            val offsets = setOf(previous - input.startCoordinate, next - input.startCoordinate)
            Pipe.entries.first { it.accessibleOffset.toSet() == offsets }
        }
    }

    enum class Pipe(val symbol: Char, val accessibleOffset: List<XY>) {
        VERTICAL('|', listOf(0 to -1, 0 to 1)),
        HORIZONTAL('-', listOf(1 to 0, -1 to 0)),
        BR('F', listOf(1 to 0, 0 to 1)),
        BL('7', listOf(-1 to 0, 0 to 1)),
        TR('L', listOf(1 to 0, 0 to -1)),
        TL('J', listOf(-1 to 0, 0 to -1)),
    }
}