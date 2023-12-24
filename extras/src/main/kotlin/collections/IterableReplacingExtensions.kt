package collections

fun <E> Iterable<E>.replace(element: E, replacement: E): List<E> =
    map { if (it == element) replacement else it }