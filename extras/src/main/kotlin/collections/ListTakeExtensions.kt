package collections

inline fun <E> List<E>.takeLastWhileInclusive(predicate: (E) -> Boolean): List<E> {
    var isPreviousAccepted = true
    return takeLastWhile { element ->
        isPreviousAccepted.also { isPreviousAccepted = predicate(element) }
    }
}
