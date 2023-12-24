package collections

inline fun <E, R : Comparable<R>> Iterable<List<E>>.sortedByInner(
    crossinline selector: (E) -> R,
): List<List<E>> =
    sortedWith(listComparatorOf(selector))

inline fun <E, R : Comparable<R>> Iterable<List<E>>.sortedByInnerDescending(
    crossinline selector: (E) -> R,
): List<List<E>> =
    sortedWith(listComparatorOf(selector).reversed())

inline fun <E, R : Comparable<R>> Iterable<E>.sortedByList(
    crossinline selector: (E) -> List<R>,
): List<E> =
    sortedWith(listComparatorOf(selector))

inline fun <E, R : Comparable<R>> Iterable<E>.sortedByListDescending(
    crossinline selector: (E) -> List<R>,
): List<E> =
    sortedWith(listComparatorOf(selector).reversed())

fun <E : Comparable<E>> Iterable<List<E>>.sorted(): List<List<E>> =
    sortedByInner { it }