package sequences

fun <E> Sequence<List<E>>.transpose(padding: E): List<List<E>> {
    if (isEmpty()) return emptyList()

    val maxWidth = maxOf { it.size }

    return (0 ..< maxWidth).map { columnIndex ->
        map { it.getOrElse(columnIndex) { padding } }.toList()
    }
}