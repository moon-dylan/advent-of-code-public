package tuples.vectors

inline fun <T, R> Vec2<T>.map(transform: (T) -> R): Vec2<R> =
    transform(first) to transform(second)

inline fun <T, R> Vec3<T>.map(transform: (T) -> R): Vec3<R> =
    Triple(transform(first), transform(second), transform(third))