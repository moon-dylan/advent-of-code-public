package d25

import aoc.AdventOfCodeDay
import aoc.trimLinesAndFilterNotBlank
import collections.headAndTail
import collections.product
import graphs.UndirectedUnweightedGraphSetExtensions.getShortestPathBetween
import tuples.vectors.Vec2
import tuples.vectors.map

typealias Edge = Vec2<String>

object Day25 : AdventOfCodeDay<Day25.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines.trimLinesAndFilterNotBlank()
            .asSequence()
            .map { line -> line.split(": ").map { it.trim() } }
            .map { (source, targetsPart) -> source to targetsPart.split(" ").map { it.trim() } }
            .flatMap { (source, targets) -> listOf(source).product(targets) }
            .map { it.toEdge() }
            .toSet()
            .let { edges -> InputData(edges.flatMap { it.toList() }.toSet(), edges) }

    override fun solvePart1(): Any {
        val (start, ends) = input.nodes.headAndTail!!

        return ends
            .partition { end ->
                (1 .. 4).fold(input.edges as Set<Edge>?) { remainingEdges, _ ->
                    remainingEdges
                        ?.getShortestPathBetween(start, end)
                        ?.zipWithNext { a, b -> (a to b).toEdge() }
                        ?.toSet()
                        ?.let { remainingEdges - it }
                } != null
            }
            .map { it.size.toLong() }
            .let { (sizeA, sizeB) -> (1L + sizeA) * sizeB }
    }

    private fun Pair<String, String>.toEdge(): Edge =
        if (first < second) this else second to first

    override fun solvePart2(): Any = "[Push The Big Red Button Again]"

    data class InputData(val nodes: Set<String>, val edges: Set<Edge>)
}
