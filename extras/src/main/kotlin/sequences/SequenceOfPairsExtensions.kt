package sequences

import tuples.mapFirst
import tuples.mapSecond

inline fun <A, B, R> Sequence<Pair<A, B>>.mapFirsts(
    crossinline transform: (A) -> R,
): Sequence<Pair<R, B>> =
    map { it.mapFirst(transform) }

inline fun <A, B, R: Any> Sequence<Pair<A, B>>.mapFirstsNotNull(
    crossinline transform: (A) -> R?,
): Sequence<Pair<R, B>> =
    map { it.mapFirst(transform) }.filterFirstNotNull()

inline fun <A, B, R> Sequence<Pair<A, B>>.mapSeconds(
    crossinline transform: (B) -> R,
): Sequence<Pair<A, R>> =
    map { it.mapSecond(transform) }

inline fun <A, B, R : Any> Sequence<Pair<A, B>>.mapSecondsNotNull(
    crossinline transform: (B) -> R?,
): Sequence<Pair<A, R>> =
    map { it.mapSecond(transform) }.filterSecondsNotNull()

fun <A: Any, B> Sequence<Pair<A?, B>>.filterFirstNotNull(): Sequence<Pair<A, B>> =
    mapNotNull { (a, b) -> if (a != null) a to b else null }

fun <A, B: Any> Sequence<Pair<A, B?>>.filterSecondsNotNull(): Sequence<Pair<A, B>> =
    mapNotNull { (a, b) -> if (b != null) a to b else null }