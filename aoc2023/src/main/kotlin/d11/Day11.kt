package d11

import aoc.AdventOfCodeDay
import collections.withGridIndex
import sequences.product
import tuples.vectors.Vec2
import tuples.x
import tuples.y
import java.math.BigInteger

typealias InputData = Set<XY>
typealias XY = Vec2<BigInteger>

object Day11 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines
            .withGridIndex()
            .flatten()
            .mapNotNull { (xy, c) -> xy.takeIf { c == '#' } }
            .map { (x, y) -> x.toBigInteger() to y.toBigInteger() }
            .toSet()

    override fun solvePart1(): Any = solve(BigInteger.TWO)
    override fun solvePart2(): Any = solve(1000000.toBigInteger())

    private fun solve(expansionMultiplier: BigInteger) =
        input
            .expandGalaxy(expansionMultiplier)
            .let { it.asSequence().product(it) }
            .filter { (a, b) -> a != b }
            .map { (a, b) -> setOf(a, b) }
            .toSet()
            .sumOf { it.manhattanDistance }

    private fun Set<XY>.expandGalaxy(expansionMultiplier: BigInteger): Set<XY> {
        /*val xMapping =
            asSequence()
                .map { it.x }
                .toSet()
                .let { xs ->
                    xs.map { x ->
                        val xLeftCount = xs.count { it < x }.toBigInteger()
                        x to (x + (x - xLeftCount))
                    }
                }
                .toMap()*/

        return expandGalaxyByX(expansionMultiplier).expandGalaxyByY(expansionMultiplier)
    }

    private fun Set<XY>.expandGalaxyByX(expansionMultiplier: BigInteger): Set<XY> {
        var previousX = BigInteger.ZERO
        var offsetX = BigInteger.ZERO

        return sortedBy { it.x }
            .map { (x, y) ->
                (if (previousX == x) {
                    offsetX
                } else if (x == previousX + BigInteger.ONE) {
                    previousX = x
                    offsetX += BigInteger.ONE
                    offsetX
                } else {
                    val difference = x - previousX - BigInteger.ONE
                    previousX = x
                    offsetX += difference * expansionMultiplier + BigInteger.ONE
                    offsetX
                }) to y
            }
            .toSet()
    }

    private fun Set<XY>.expandGalaxyByY(expansionMultiplier: BigInteger): Set<XY> {
        var previousY = BigInteger.ZERO
        var offsetY = BigInteger.ZERO

        return sortedBy { it.y }
            .map { (x, y) ->
                x to (if (previousY == y) {
                    offsetY
                } else if (previousY + BigInteger.ONE == y) {
                    previousY = y
                    offsetY += BigInteger.ONE
                    offsetY
                } else {
                    val difference = y - previousY - BigInteger.ONE
                    previousY = y
                    offsetY += difference * expansionMultiplier + BigInteger.ONE
                    offsetY
                })
            }
            .toSet()
    }

    private val Set<XY>.manhattanDistance: BigInteger
        get() {
            val (a, b) = toList()
            val (ax, ay) = a
            val (bx, by) = b
            return (ax - bx).abs() + (ay - by).abs()
        }
}
