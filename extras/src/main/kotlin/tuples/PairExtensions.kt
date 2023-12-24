package tuples

val <A, B> Pair<A, B>.x get() = first
val <A, B> Pair<A, B>.y get() = second

inline fun <A, B, R> Pair<A, B>.mapFirst(transform: (A) -> R): Pair<R, B> =
    transform(first) to second

inline fun <A, B, R> Pair<A, B>.mapSecond(transform: (B) -> R): Pair<A, R> =
    first to transform(second)