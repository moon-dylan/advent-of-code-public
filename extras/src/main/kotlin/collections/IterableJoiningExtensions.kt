package collections

fun <E> Iterable<E>.joinCharsToString(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: (E) -> Char,
): String =
    map(transform).joinToString(separator, prefix, postfix, limit, truncated)