package collections

fun <E> Iterable<E>.toFrequencyMap(): Map<E, Int> =
    groupBy { it }.mapValues { it.value.size }