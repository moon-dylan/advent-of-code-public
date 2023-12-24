package d18

import aoc.AdventOfCodeDay
import collections.product
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.minus
import tuples.vectors.arithmetic.plus
import tuples.vectors.arithmetic.times
import tuples.vectors.map
import tuples.x
import tuples.y
import java.math.BigInteger

typealias XY = Vec2<Long>

object Day18 : AdventOfCodeDay<Day18.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val (partOneEdges, partTwoEdges) =
            lines
                .filter { it.isNotBlank() }
                .map { partOneStepOf(it) to partTwoStepOf(it) }
                .unzip()
                .map { steps ->
                    steps.fold((0L to 0L) to emptySet<Edge>()) { (lastXy, edges), step ->
                        val nextXy = lastXy + (step.count * step.direction.dXy)
                        val thisEdge = when (step.direction) {
                            Direction.RIGHT, Direction.DOWN -> Edge(lastXy, nextXy)
                            Direction.LEFT, Direction.UP -> Edge(nextXy, lastXy)
                        }
                        nextXy to (edges + thisEdge)
                    }.second
                }

        return InputData(
            partOneEdges = partOneEdges,
            partTwoEdges = partTwoEdges,
        )
    }

    private fun partOneStepOf(line: String): Step {
        val (directionPart, countPart) = line.trim().split(" +".toRegex()).map { it.trim() }
        return Step(
            direction = when (directionPart) {
                "U" -> Direction.UP
                "D" -> Direction.DOWN
                "L" -> Direction.LEFT
                "R" -> Direction.RIGHT
                else -> error("invalid direction: $directionPart")
            },
            count = countPart.toLong(),
        )
    }

    private fun partTwoStepOf(line: String): Step {
        val rgbPart = line.trim().split(" +".toRegex())[2].trim().drop(2).dropLast(1)
        return Step(
            direction = when (rgbPart.takeLast(1)) {
                "0" -> Direction.RIGHT
                "1" -> Direction.DOWN
                "2" -> Direction.LEFT
                "3" -> Direction.UP
                else -> error("invalid direction")
            },
            count = rgbPart.dropLast(1).toLong(16),
        )
    }

    override fun solvePart1(): Any = input.partOneEdges.toDisjointInteriorCubesWithEdges().sumOf { it.size }
    override fun solvePart2(): Any = input.partTwoEdges.toDisjointInteriorCubesWithEdges().sumOf { it.size }

    private fun Set<Edge>.toDisjointInteriorCubesWithEdges(): Set<Cube> {
        val (xs, ys) = kotlin.run {
            val edgeXs = flatMap { listOf(it.topLeftXy.x, it.bottomRightXy.x) }.distinct()
            val edgeYs = flatMap { listOf(it.topLeftXy.y, it.bottomRightXy.y) }.distinct()
            (edgeXs to edgeYs)
                .map { (it + (it.min() - 1) + (it.max() + 1)).sorted() }
        }

        val allCubes =
            (xs to ys)
                .map { it.zipWithNext() }
                .map { pairs ->
                    pairs.flatMap { (start, end) ->
                        val delta = end - start

                        if (delta <= 0) error("are you serious?")

                        when (delta) {
                            1L -> listOf(start to start, end to end)
                            else -> listOf(start to start, start + 1 to end - 1, end to end)
                        }
                    }.toSet()
                }
                .let { (xsPairs, ysPairs) -> xsPairs.product(ysPairs) }
                .map { (leftRight, topBottom) ->
                    val (left, right) = leftRight
                    val (top, bottom) = topBottom
                    Cube(left to top, right to bottom)
                }

        val outsideCubes = buildSet {
            val nonEdgeCubes = allCubes.filterNot { it in this }.toSet()

            val working = kotlin.run {
                val startTopLeft = xs.first() to ys.first()
                val start = nonEdgeCubes.first { it.topLeftXy == startTopLeft }
                add(start)
                mutableSetOf(start)
            }

            val topLeftToCube = nonEdgeCubes.associateBy { it.topLeftXy }
            val bottomRightToCube = nonEdgeCubes.associateBy { it.bottomRightXy }

            while (working.isNotEmpty()) {
                val current = working.first()
                working.remove(current)

                val rightCube = topLeftToCube[current.topRightXy + (1L to 0L)]
                val bottomCube = topLeftToCube[current.bottomLeftXy + (0L to 1L)]
                val leftCube = bottomRightToCube[current.bottomLeftXy - (1L to 0L)]
                val topCube = bottomRightToCube[current.topRightXy - (0L to 1L)]
                val next = listOfNotNull(rightCube, bottomCube, leftCube, topCube).filter { it !in this@buildSet }

                addAll(next)
                working.addAll(next)
            }
        }

        return allCubes.toSet().minus(outsideCubes)
    }

    private operator fun Set<Edge>.contains(cube: Cube): Boolean = any {
        val a = !(cube.bottomRightXy.y < it.topLeftXy.y || it.bottomRightXy.y < cube.topLeftXy.y)
        val b = !(cube.bottomRightXy.x < it.topLeftXy.x || it.bottomRightXy.x < cube.topLeftXy.x)
        a && b
    }

    enum class Direction(val dXy: XY) {
        RIGHT(1L to 0L),
        LEFT(-1L to 0L),
        UP(0L to -1L),
        DOWN(0L to 1L),
    }

    data class Step(val direction: Direction, val count: Long)
    data class Edge(val topLeftXy: XY, val bottomRightXy: XY)
    data class Cube(val topLeftXy: XY, val bottomRightXy: XY) {
        val topRightXy = bottomRightXy.x to topLeftXy.y
        val bottomLeftXy = topLeftXy.x to bottomRightXy.y

        val size: BigInteger =
            (bottomRightXy - topLeftXy + 1).let { (dx, dy) -> (dx.toBigInteger() * dy.toBigInteger()).abs() }
    }

    data class InputData(
        val partOneEdges: Set<Edge>,
        val partTwoEdges: Set<Edge>,
    )
}
