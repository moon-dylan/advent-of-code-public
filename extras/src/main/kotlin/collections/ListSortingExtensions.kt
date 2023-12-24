package collections

@JvmName("listComparatorOfSelector")
inline fun <E, R : Comparable<R>> listComparatorOf(
    crossinline selector: (E) -> R,
): Comparator<List<E>> =
    Comparator { xs, ys ->
        if (xs.size < ys.size) return@Comparator -1
        if (ys.size < xs.size) return@Comparator 1

        xs.zip(ys)
            .asSequence()
            .map { (x, y) -> selector(x).compareTo(selector(y)) }
            .firstOrNull { it != 0 } ?: 0
    }

@JvmName("listComparatorOfListSelector")
inline fun <E, R: Comparable<R>> listComparatorOf(
    crossinline selector: (E) -> List<R>,
) : Comparator<E> =
    Comparator { xElement, yElement ->
        val xs = selector(xElement)
        val ys = selector(yElement)

        if (xs.size < ys.size) return@Comparator -1
        if (ys.size < xs.size) return@Comparator 1

        xs.zip(ys)
            .asSequence()
            .map { (x, y) -> x.compareTo(y) }
            .firstOrNull { it != 0 } ?: 0
    }