package collections

fun <A, B, R> Iterable<Map.Entry<A, B>>.mapFirsts(transform: (A) -> R): List<Pair<R, B>> =
    map { transform(it.key) to it.value }

fun <A, B, R> Iterable<Map.Entry<A, B>>.mapKeys(transform: (A, B) -> R): List<Pair<R, B>> =
    map { transform(it.key, it.value) to it.value }

fun <A, B, R> Iterable<Map.Entry<A, B>>.mapSeconds(transform: (B) -> R): List<Pair<A, R>> =
    map { it.key to transform(it.value) }

fun <A, B, R> Iterable<Map.Entry<A, B>>.mapValues(transform: (A, B) -> R): List<Pair<A, R>> =
    map { it.key to transform(it.key, it.value) }

fun <A: Any, B> Iterable<Map.Entry<A?, B>>.filterFirstNotNull(): List<Pair<A, B>> =
    mapNotNull { (a, b) -> if (a != null) a to b else null }

fun <A: Any, B> Iterable<Map.Entry<A?, B>>.filterKeysNotNull(): List<Pair<A, B>> =
    filterFirstNotNull()

fun <A, B: Any> Iterable<Map.Entry<A, B?>>.filterSecondsNotNull(): List<Pair<A, B>> =
    mapNotNull { (a, b) -> if (b != null) a to b else null }

fun <A, B: Any> Iterable<Map.Entry<A, B?>>.filterValuesNotNull(): List<Pair<A, B>> =
    filterSecondsNotNull()