package strings

val String.head: Char? get() = firstOrNull()
val String.tail: String get() = drop(1)
val String.tails: List<String> get() = indices.map { drop(it) }
val String.tailsSequence: Sequence<String> get() = indices.asSequence().map { drop(it) }