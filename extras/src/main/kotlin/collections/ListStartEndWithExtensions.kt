package collections

fun <E> List<E>.startsWith(prefix: List<E>): Boolean {
    if (size < prefix.size) return false
    return zip(prefix).all { (a, b) -> a == b }
}

fun <E> List<E>.endsWith(suffix: List<E>): Boolean {
    if (size < suffix.size) return false
    return takeLast(suffix.size).zip(suffix).all { (a, b) -> a == b }
}