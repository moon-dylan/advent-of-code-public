package sequences

fun <E> Sequence<E>.isEmpty(): Boolean {
    for (ignored in this) return false
    return true
}