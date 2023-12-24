package d08

import aoc.AdventOfCodeDay
import collections.*
import numbers.lcm
import sequences.countBigInt
import tuples.mapSecond
import java.math.BigInteger

object Day8 : AdventOfCodeDay<Day8.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    private val startingNodes: Set<String> = input.network.keys.filter { it.endsWith("A") }.toSet()

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
//        println(iteratePart2())
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val (directionsPart, networkPart) = text.split("\n\n")

        val directions = directionsPart.trim().toList().map {
            when (it) {
                'L' -> Direction.LEFT
                'R' -> Direction.RIGHT
                else -> error("unknown case")
            }
        }

        val network =
            networkPart.trim()
                .split("\n")
                .map { it.trim() }
                .associate { line ->
                    line.split("\\s*=\\s*".toRegex()).toPair()
                        .mapSecond { it.drop(1).dropLast(1).split(",\\s*".toRegex()).toPair() }
                }

        return InputData(
            directions = directions,
            network = network,
        )
    }

    override fun solvePart1(): Any = countStepsToFirstEnd("AAA")

    override fun solvePart2(): Any =
        startingNodes.map(::countStepsToFirstEnd).reduce { a, b -> a.lcm(b) }

    private fun countStepsToFirstEnd(startNode: String): BigInteger =
        input.directions.cycled
            .scan(startNode, ::getNextNode)
            .countBigInt { !it.endsWith("Z") }

    private fun iteratePart2(): BigInteger =
        input.directions.cycled
            .scan(startingNodes) { currentNodes, direction ->
                currentNodes.map { node -> getNextNode(node, direction) }.toSet()
            }
            .countBigInt { nodes -> !nodes.all { it.endsWith("Z") } }

    private fun getNextNode(node: String, direction: Direction): String {
        val (left, right) = input.network[node]!!
        return when (direction) {
            Direction.LEFT -> left
            Direction.RIGHT -> right
        }
    }

    data class InputData(
        val directions: List<Direction>,
        val network: Map<String, Pair<String, String>>,
    )

    enum class Direction { LEFT, RIGHT }
}
