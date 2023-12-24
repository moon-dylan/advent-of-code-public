package collections

import java.math.BigInteger

// <T> Iterable<T>.filterIndexed
inline fun <E> Iterable<E>.filterBigIndexed(
    predicate: (index: BigInteger, element: E) -> Boolean,
): List<E> {
    var index = BigInteger.ZERO
    return filter { predicate(index++, it) }
}

// <T, R> Iterable<T>.flatMapIndexed
inline fun <E, R> Iterable<E>.flatMapBigIndexed(
    transform: (index: BigInteger, element: E) -> Iterable<R>,
): List<R> {
    var index = BigInteger.ZERO
    return flatMap { transform(index++, it) }
}

// <T, R> Iterable<T>.flatMapIndexed
@JvmName("flatMapBigIndexedSequence")
inline fun <E, R> Iterable<E>.flatMapBigIndexed(
    transform: (index: BigInteger, element: E) -> Sequence<R>,
): List<R> {
    var index = BigInteger.ZERO
    return flatMap { transform(index++, it) }
}

// <T, R> Iterable<T>.mapIndexed
inline fun <E, R> Iterable<E>.mapBigIndexed(
    transform: (index: BigInteger, element: E) -> R,
): List<R> {
    var index = BigInteger.ZERO
    return map { transform(index++, it) }
}

// <T, R : Any> Iterable<T>.mapIndexedNotNull
inline fun <E, R : Any> Iterable<E>.mapBigIndexedNotNull(
    transform: (index: BigInteger, element: E) -> R?,
): List<R> {
    var index = BigInteger.ZERO
    return mapNotNull { transform(index++, it) }
}

// <T> Iterable<T>.withIndex(): Iterable<IndexedValue

// <T, R> Iterable<T>.foldIndexed
inline fun <E, R> Iterable<E>.foldBigIndexed(
    initial: R,
    operation: (index: BigInteger, acc: R, E) -> R,
): R {
    var index = BigInteger.ZERO
    return fold(initial) { acc, element -> operation(index++, acc, element) }
}

// <T, R> List<T>.foldRightIndexed
inline fun <E, R> List<E>.foldRightBigIndexed(
    initial: R,
    operation: (index: BigInteger, E, acc: R) -> R,
): R {
    var index = BigInteger.ZERO
    return foldRight(initial) { element, acc -> operation(index++, element, acc) }
}

// <T> Iterable<T>.forEachIndexed
inline fun <E> Iterable<E>.forEachBigIndexed(
    action: (index: BigInteger, element: E) -> Unit,
) {
    var index = BigInteger.ZERO
    forEach { action(index++, it) }
}

// <T, C : Iterable<T>> C.onEachIndexed
inline fun <E> Iterable<E>.onEachBigIndexed(
    action: (index: BigInteger, element: E) -> Unit,
): Iterable<E> {
    var index = BigInteger.ZERO
    return onEach { action(index++, it) }
}

// <S, T : S> Iterable<T>.reduceIndexed
inline fun <R, E : R> Iterable<E>.reduceBigIndexed(
    operation: (index: BigInteger, acc: R, E) -> R,
): R {
    var index = BigInteger.ZERO
    return reduce<R, E> { acc, element -> operation(index++, acc, element) }
}

// <S, T : S> Iterable<T>.reduceIndexedOrNull
inline fun <R, E : R> Iterable<E>.reduceBigIndexedOrNull(
    operation: (index: BigInteger, acc: R, E) -> R,
): R? {
    var index = BigInteger.ZERO
    return reduceOrNull<R, E> { acc, element -> operation(index++, acc, element) }
}

// <S, T : S> List<T>.reduceRightIndexed
inline fun <R, E : R> List<E>.reduceRightBigIndexed(
    operation: (index: BigInteger, E, acc: R) -> R,
): R {
    var index = BigInteger.ZERO
    return reduceRight<R, E> { element, acc -> operation(index++, element, acc) }
}

// <S, T : S> List<T>.reduceRightIndexedOrNull
inline fun <R, E : R> List<E>.reduceRightBigIndexedOrNull(
    operation: (index: BigInteger, E, acc: R) -> R,
): R? {
    var index = BigInteger.ZERO
    return reduceRightOrNull<R, E> { element, acc -> operation(index++, element, acc) }
}

// <T, R> Iterable<T>.runningFoldIndexed
inline fun <R, E> Iterable<E>.runningFoldBigIndexed(
    initial: R,
    operation: (index: BigInteger, acc: R, E) -> R,
): List<R> {
    var index = BigInteger.ZERO
    return runningFold(initial) { acc, element -> operation(index++, acc, element) }
}

// <S, T : S> Iterable<T>.runningReduceIndexed
inline fun <R, E : R> Iterable<E>.runningReduceBigIndexed(
    operation: (index: BigInteger, acc: R, E) -> R,
): List<R> {
    var index = BigInteger.ZERO
    return runningReduce<R, E> { acc, element -> operation(index++, acc, element) }
}

// <T, R> Iterable<T>.scanIndexed
inline fun <E, R> Iterable<E>.scanIndexed(
    initial: R,
    operation: (index: BigInteger, acc: R, E) -> R,
): List<R> {
    return runningFoldBigIndexed(initial, operation)
}