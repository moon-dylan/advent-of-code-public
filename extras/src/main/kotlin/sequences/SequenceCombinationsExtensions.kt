package sequences

import tuples.vectors.Vec2

fun <T> Sequence<T>.combinations(): Sequence<Vec2<T>> =
    flatMapIndexed { i, a ->
        drop(i + 1).map { a to it }
    }
