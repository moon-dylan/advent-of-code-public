package collections

inline fun <E> Iterable<E>.takeWhileInclusive(predicate: (E) -> Boolean): List<E> {
    var isPreviousAccepted = true
    return takeWhile { element ->
        isPreviousAccepted.also { isPreviousAccepted = predicate(element) }
    }
}

fun <E> Iterable<E?>.takeWhileNotNull(): List<E> =
    takeWhile { it != null }.map { it!! }
