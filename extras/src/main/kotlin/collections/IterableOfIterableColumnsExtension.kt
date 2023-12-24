package collections

fun <E> List<List<E>>.transpose(padding: E): List<List<E>> {
    if (isEmpty()) return emptyList()

    val maxWidth = maxOf { it.size }

    return (0 ..< maxWidth).map { columnIndex ->
        map { it.getOrElse(columnIndex) { padding } }
    }
}

fun <E> List<List<E>>.transpose(): List<List<E>>? {
    if (isEmpty()) return emptyList()

    val width = map { it.size }.distinct().singleOrNull() ?: return null

    return (0 ..< width).map { columnIndex ->
        map { it[columnIndex] }
    }
}

@JvmName("stringListTranspose")
fun List<String>.transpose(): List<String>? {
    if (isEmpty()) return emptyList()

    val width = map { it.length }.distinct().singleOrNull() ?: return null

    return (0 ..< width).map { columnIndex ->
        map { it[columnIndex] }.joinToString("")
    }
}