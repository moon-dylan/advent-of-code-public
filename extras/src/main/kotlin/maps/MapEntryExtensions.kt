package maps

val <K, V> Map.Entry<K, V>.x get() = key
val <K, V> Map.Entry<K, V>.y get() = value

inline fun <T, R> Map.Entry<T, T>.map(transform: (T) -> R): Pair<R, R> =
    transform(key) to transform(value)

inline fun <K, V, R> Map.Entry<K, V>.mapKey(transform: (K) -> R): Pair<R, V> =
    transform(key) to value

inline fun <K, V, R> Map.Entry<K, V>.mapValue(transform: (V) -> R): Pair<K, R> =
    key to transform(value)