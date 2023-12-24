package tuples

val <A, B, C> Triple<A, B, C>.x get() = first
val <A, B, C> Triple<A, B, C>.y get() = second
val <A, B, C> Triple<A, B, C>.z get() = third

val <A, B, C> Triple<A, B, C>.xy get() = first to second
val <A, B, C> Triple<A, B, C>.xz get() = first to third
val <A, B, C> Triple<A, B, C>.yz get() = second to third

inline fun <A, B, C, R> Triple<A, B, C>.mapFirst(transform: (A) -> R): Triple<R, B, C> =
    Triple(transform(first), second, third)

inline fun <A, B, C, R> Triple<A, B, C>.mapSecond(transform: (B) -> R): Triple<A, R, C> =
    Triple(first, transform(second), third)

inline fun <A, B, C, R> Triple<A, B, C>.mapThird(transform: (C) -> R): Triple<A, B, R> =
    Triple(first, second, transform(third))
