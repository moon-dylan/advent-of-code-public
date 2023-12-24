package sequences

inline fun <E> Sequence<E>.takeWhileInclusive(
    crossinline predicate: (E) -> Boolean,
): Sequence<E> {
    var isPreviousAccepted = true
    return takeWhile { element ->
        isPreviousAccepted.also { isPreviousAccepted = predicate(element) }
    }
}

fun <E> Sequence<E?>.takeWhileNotNull(): Sequence<E> =
    takeWhile { it != null }.map { it!! }
