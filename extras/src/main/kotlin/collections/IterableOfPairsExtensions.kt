package collections

import tuples.mapFirst
import tuples.mapSecond

inline fun <A, B, R> Iterable<Pair<A, B>>.mapFirsts(transform: (A) -> R): List<Pair<R, B>> =
    map { it.mapFirst(transform) }

inline fun <A, B, R: Any> Iterable<Pair<A, B>>.mapFirstsNotNull(transform: (A) -> R?): List<Pair<R, B>> =
    map { it.mapFirst(transform) }.filterFirstNotNull()

inline fun <A, B, R> Iterable<Pair<A, B>>.mapSeconds(transform: (B) -> R): List<Pair<A, R>> =
    map { it.mapSecond(transform) }

inline fun <A, B, R: Any> Iterable<Pair<A, B>>.mapSecondsNotNull(transform: (B) -> R?): List<Pair<A, R>> =
    map { it.mapSecond(transform) }.filterSecondsNotNull()

fun <A: Any, B> Iterable<Pair<A?, B>>.filterFirstNotNull(): List<Pair<A, B>> =
    mapNotNull { (a, b) -> if (a != null) a to b else null }

fun <A, B: Any> Iterable<Pair<A, B?>>.filterSecondsNotNull(): List<Pair<A, B>> =
    mapNotNull { (a, b) -> if (b != null) a to b else null }