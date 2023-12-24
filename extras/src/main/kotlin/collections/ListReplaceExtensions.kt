package collections

inline fun <E> List<E>.replaceWhere(replacement: E, filter: (E) -> Boolean): List<E> =
    map { if (filter(it)) replacement else it }

inline fun <E> List<E>.mapWhere(transform: (E) -> E, filter: (E) -> Boolean): List<E> =
    map { if (filter(it)) transform(it) else it }
