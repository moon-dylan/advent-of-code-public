package collections

inline fun <E, R : Any> Iterable<E>.mapWhileNotNull(
    crossinline transform: (E) -> R?,
): List<R> =
    asSequence().map { transform(it) }.takeWhile { it != null }.map { it!! }.toList()
