package collections

fun <E> List<E>.indexOfOrNull(element: E): Int? =
    indexOf(element).takeUnless { it < 0 }

inline fun <E> List<E>.indexOfFirstOrNull(predicate: (E) -> Boolean): Int? =
    indexOfFirst(predicate).takeUnless { it < 0 }

inline fun <E> List<E>.indexOfLastOrNull(predicate: (E) -> Boolean): Int? =
    indexOfLast(predicate).takeUnless { it < 0 }

fun <E> List<E>.indexOfAll(element: E): List<Int> =
    withIndex().filter { it.value == element }.map { it.index }