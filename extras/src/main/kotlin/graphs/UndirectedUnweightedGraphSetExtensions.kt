package graphs

import collections.NonEmptyList
import collections.groupByFirstAndMapSeparatedValues
import graphs.UnweightedGraphMapExtensions.getShortestPathBetween
import graphs.UnweightedGraphMapExtensions.getShortestPathsFrom
import tuples.vectors.Vec2

object UndirectedUnweightedGraphSetExtensions {
    @JvmName("getShortestPathBetweenGraphSetStartEnd")
    fun <Node, Edge : Vec2<Node>> getShortestPathBetween(
        edges: Set<Edge>,
        start: Node,
        end: Node,
    ): NonEmptyList<Node>? =
        edges.getShortestPathBetween(start, end)

    @JvmName("getShortestPathBetweenGraphSetStartEndExtension")
    fun <Node, Edge : Vec2<Node>> Set<Edge>.getShortestPathBetween(start: Node, end: Node): NonEmptyList<Node>? =
        flatMap { listOf(it.first to it.second, it.second to it.first) }
            .groupByFirstAndMapSeparatedValues { _, values -> values.toSet() }
            .getShortestPathBetween(start, end)

    @JvmName("getShortestPathsFromGraphSetStartExtension")
    fun <Node, Edge : Vec2<Node>> Set<Edge>.getShortestPathsFrom(start: Node): Map<Node, NonEmptyList<Node>> =
        flatMap { listOf(it.first to it.second, it.second to it.first) }
            .groupByFirstAndMapSeparatedValues { _, values -> values.toSet() }
            .getShortestPathsFrom(start)
}
