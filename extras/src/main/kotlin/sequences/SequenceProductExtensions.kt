package sequences

fun <L, R> Sequence<L>.product(other: Iterable<R>): Sequence<Pair<L, R>> =
    product(other) { l, r -> l to r }

@JvmName("sequenceProduct")
fun <L, R> Sequence<L>.product(other: Sequence<R>): Sequence<Pair<L, R>> =
    product(other) { l, r -> l to r }

inline fun <L, R, Result> Sequence<L>.product(
    other: Iterable<R>,
    crossinline transform: (L, R) -> Result,
): Sequence<Result> =
    flatMap { l ->
        other.map { r ->
            transform(l, r)
        }
    }

@JvmName("sequenceProduct")
inline fun <L, R, Result> Sequence<L>.product(
    other: Sequence<R>,
    crossinline transform: (L, R) -> Result,
): Sequence<Result> =
    flatMap { l ->
        other.map { r ->
            transform(l, r)
        }
    }

fun <L, R> L.product(other: Sequence<R>): Sequence<Pair<L, R>> =
    sequenceOf(this).product(other) { l, r -> l to r }

fun <L, R> Sequence<L>.product(head: R, vararg other: R): Sequence<Pair<L, R>> =
    product(listOf(head) + other.toList()) { l, r -> l to r }
