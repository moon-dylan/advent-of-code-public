package graphs

import collections.NonEmptyList
import collections.toNonEmpty

object UnweightedGraphMapExtensions {
    @JvmName("getShortestPathBetweenGraphMapStartEnd")
    fun <Node> getShortestPathBetween(
        graph: Map<Node, Collection<Node>>,
        start: Node,
        end: Node,
    ): NonEmptyList<Node>? =
        graph.getShortestPathBetween(start, end)

    // TODO: perhaps can optimise?
    @JvmName("getShortestPathBetweenGraphMapStartEndExtension")
    fun <Node> Map<Node, Collection<Node>>.getShortestPathBetween(start: Node, end: Node): NonEmptyList<Node>? {
        val nodeToPrevious = mutableMapOf<Node, Node>()
        val working = ArrayDeque(listOf(start))

        while (working.isNotEmpty()) {
            val currentNode = working.removeFirst()
            working.remove(currentNode)

            val nextNodes =
                get(currentNode).orEmpty()
                    .filter { it !in nodeToPrevious && it != start }
                    .onEach { nodeToPrevious[it] = currentNode }

            if (end in nextNodes) break

            working.addAll(nextNodes)
        }

        return nodeToPrevious.toPath(end)
    }

    // TODO: perhaps can optimise?
    @JvmName("getShortestPathsFromGraphMapStartExtension")
    fun <Node> Map<Node, Collection<Node>>.getShortestPathsFrom(start: Node): Map<Node, NonEmptyList<Node>> {
        val nodeToPrevious = mutableMapOf<Node, Node>()
        val working = ArrayDeque(listOf(start))

        while (working.isNotEmpty()) {
            val currentNode = working.removeFirst()
            working.remove(currentNode)

            val nextNodes =
                get(currentNode).orEmpty()
                    .filter { it !in nodeToPrevious && it != start }
                    .onEach { nodeToPrevious[it] = currentNode }

            working.addAll(nextNodes)
        }

        return nodeToPrevious.mapValues { (end, _) -> nodeToPrevious.toPath(end)!! }
    }

    /** This extension assumes that start does not map to anything!!! */
    private fun <Node> Map<Node, Node>.toPath(end: Node): NonEmptyList<Node>? {
        if (end !in this) return null
        val output = mutableListOf<Node>()

        var current: Node? = end
        while (current != null) {
            output.add(current)
            current = get(current)
        }

        return output.reversed().toNonEmpty()
    }
}
