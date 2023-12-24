package collections

val <T> List<T>.tails: List<List<T>> get() = indices.map { drop(it) }
val <T> List<T>.tailsSequence: Sequence<List<T>> get() = indices.asSequence().map { drop(it) }