package collections

val <T> Iterable<T>.head: T? get() = firstOrNull()
val <T> Iterable<T>.tail: List<T> get() = drop(1)
val <E> Iterable<E>.headAndTail: Pair<E, List<E>>? get() = firstOrNull()?.let { it to drop(1) }