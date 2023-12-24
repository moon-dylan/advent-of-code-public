package collections

val <E> List<E>.mostFrequentOrNull: E? get() =
    if (isEmpty()) null else toFrequencyMap().maxByOrNull { it.value }?.key

val <E> List<E>.leastFrequentOrNull: E? get() =
    if (isEmpty()) null else toFrequencyMap().minByOrNull { it.value }?.key