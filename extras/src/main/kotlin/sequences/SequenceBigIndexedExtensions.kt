package sequences

import java.math.BigInteger


// <T> Sequence<T>.filterIndexed
@JvmName("sequenceFilterBigIndexed")
inline fun <E> Sequence<E>.filterBigIndexed(
    crossinline predicate: (index: BigInteger, element: E) -> Boolean,
): Sequence<E> {
    var index = BigInteger.ZERO
    return filter { predicate(index++, it) }
}

// <T, R> Sequence<T>.flatMapIndexed
@JvmName("sequenceFlatMapBigIndexed")
inline fun <E, R> Sequence<E>.flatMapBigIndexed(
    crossinline transform: (index: BigInteger, element: E) -> Iterable<R>,
): Sequence<R> {
    var index = BigInteger.ZERO
    return flatMap { transform(index++, it) }
}

// <T, R> Sequence<T>.flatMapIndexed
@JvmName("sequenceFlatMapBigIndexedSequence")
inline fun <E, R> Sequence<E>.flatMapBigIndexed(
    crossinline transform: (index: BigInteger, element: E) -> Sequence<R>,
): Sequence<R> {
    var index = BigInteger.ZERO
    return flatMap { transform(index++, it) }
}

// <T, R> Sequence<T>.mapIndexed
@JvmName("sequenceMapBigIndexed")
inline fun <E, R> Sequence<E>.mapBigIndexed(
    crossinline transform: (index: BigInteger, element: E) -> R,
): Sequence<R> {
    var index = BigInteger.ZERO
    return map { transform(index++, it) }
}

// <T, R : Any> Sequence<T>.mapIndexedNotNull
@JvmName("sequenceMapBigIndexedNotNull")
inline fun <E, R: Any> Sequence<E>.mapBigIndexedNotNull(
    crossinline transform: (index: BigInteger, element: E) -> R?,
): Sequence<R> {
    var index = BigInteger.ZERO
    return mapNotNull { transform(index++, it) }
}

// <T> Sequence<T>.withIndex(): Sequence<IndexedValue

// <T, R> Sequence<T>.foldIndexed
@JvmName("sequenceFoldBigIndexed")
inline fun <E, R> Sequence<E>.foldBigIndexed(
    initial: R,
    operation: (index: BigInteger, acc: R, E) -> R,
): R {
    var index = BigInteger.ZERO
    return fold(initial) { acc, element -> operation(index++, acc, element) }
}

// <T> Sequence<T>.forEachIndexed
@JvmName("sequenceForEachBigIndexed")
inline fun <E> Sequence<E>.forEachBigIndexed(
    action: (index: BigInteger, element: E) -> Unit,
) {
    var index = BigInteger.ZERO
    forEach { action(index++, it) }
}

// <T> Sequence<T>.onEachIndexed
@JvmName("sequenceOnEachBigIndexed")
inline fun <E> Sequence<E>.onEachBigIndexed(
    crossinline action: (index: BigInteger, element: E) -> Unit,
): Sequence<E> {
    var index = BigInteger.ZERO
    return onEach { action(index++, it) }
}

// <S, T : S> Sequence<T>.reduceIndexed
@JvmName("sequenceReduceBigIndexed")
inline fun <R, E : R> Sequence<E>.reduceBigIndexed(
    operation: (index: BigInteger, acc: R, E) -> R,
): R {
    var index = BigInteger.ZERO
    return reduce<R, E> { acc, element -> operation(index++, acc, element) }
}

// <S, T : S> Sequence<T>.reduceIndexedOrNull
@JvmName("sequenceReduceBigIndexedOrNull")
inline fun <R, E : R> Sequence<E>.reduceBigIndexedOrNull(
    operation: (index: BigInteger, acc: R, E) -> R,
): R? {
    var index = BigInteger.ZERO
    return reduceOrNull<R, E> { acc, element -> operation(index++, acc, element) }
}

// <T, R> Sequence<T>.runningFoldIndexed
@JvmName("sequenceRunningFoldBigIndexed")
inline fun <R, E> Sequence<E>.runningFoldBigIndexed(
    initial: R,
    crossinline operation: (index: BigInteger, acc: R, E) -> R,
): Sequence<R> {
    var index = BigInteger.ZERO
    return runningFold(initial) { acc, element -> operation(index++, acc, element) }
}

// <S, T : S> Sequence<T>.runningReduceIndexed
@JvmName("sequenceRunningReduceBigIndexed")
inline fun <R, E : R> Sequence<E>.runningReduceBigIndexed(
    crossinline operation: (index: BigInteger, acc: R, E) -> R,
): Sequence<R> {
    var index = BigInteger.ZERO
    return runningReduce<R, E> { acc, element -> operation(index++, acc, element) }
}

// <T, R> Sequence<T>.scanIndexed
@JvmName("sequenceScanBigIndexed")
inline fun <E, R> Sequence<E>.scanBigIndexed(
    initial: R,
    crossinline operation: (index: BigInteger, acc: R, E) -> R,
): Sequence<R> {
    return runningFoldBigIndexed(initial, operation)
}