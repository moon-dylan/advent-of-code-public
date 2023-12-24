package tuples.arithmetic

@JvmName("collectionPairPlusCollectionPair")
operator fun <L, R, LC : Collection<L>, RC : Collection<R>> Pair<LC, RC>.plus(
    other: Pair<LC, RC>,
): Pair<List<L>, List<R>> =
    (first + other.first) to (second + other.second)

@JvmName("setPairPlusSetPair")
operator fun <L, R> Pair<Set<L>, Set<R>>.plus(
    other: Pair<Set<L>, Set<R>>,
): Pair<Set<L>, Set<R>> =
    (first + other.first) to (second + other.second)

@JvmName("listAndSetPairPlusListAndSetPair")
operator fun <L, R> Pair<List<L>, Set<R>>.plus(
    other: Pair<List<L>, Set<R>>,
): Pair<List<L>, Set<R>> =
    (first + other.first) to (second + other.second)
