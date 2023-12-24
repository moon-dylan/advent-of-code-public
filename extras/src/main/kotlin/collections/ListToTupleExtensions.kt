package collections

fun <E> List<E>.toPairOrNull(): Pair<E, E>? =
    takeIf { it.size == 2 }?.let { (a, b) -> a to b }

fun <E> List<E>.toPair(): Pair<E, E> =
    let { (a, b) -> a to b }

fun <E> List<E>.toTripleOrNull(): Triple<E, E, E>? =
    takeIf { it.size == 2 }?.let { (a, b, c) -> Triple(a, b, c) }

fun <E> List<E>.toTriple(): Triple<E, E, E> =
    let { (a, b, c) -> Triple(a, b, c) }