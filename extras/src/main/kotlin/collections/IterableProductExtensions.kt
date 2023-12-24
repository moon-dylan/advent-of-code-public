package collections

fun <L, R> Iterable<L>.product(other: Iterable<R>): List<Pair<L, R>> =
    product(other) { l, r -> l to r }

inline fun <L, R, Result> Iterable<L>.product(
    other: Iterable<R>,
    transform: (L, R) -> Result,
): List<Result> =
    flatMap { l ->
        other.map { r ->
            transform(l, r)
        }
    }

fun <L, R> L.product(other: Iterable<R>): List<Pair<L, R>> =
    listOf(this).product(other) { l, r -> l to r }

fun <L, R> Iterable<L>.product(head: R, vararg other: R): List<Pair<L, R>> =
    product(listOf(head) + other.toList()) { l, r -> l to r }
