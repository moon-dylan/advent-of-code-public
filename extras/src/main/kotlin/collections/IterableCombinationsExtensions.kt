package collections

import tuples.vectors.Vec2

fun <T> Iterable<T>.combinations(): List<Vec2<T>> =
    flatMapIndexed { i, a ->
        drop(i + 1).map { a to it }
    }
