package sequences

inline fun <E, R : Any> Sequence<E>.mapWhileNotNull(
    crossinline transform: (E) -> R?,
): Sequence<R> =
    map { transform(it) }.takeWhile { it != null }.map { it!! }
