package sequences

fun <K, V> Sequence<Pair<K, V>>.groupByFirst(): Map<K, List<Pair<K, V>>> =
    groupBy { it.first }

fun <K, V> Sequence<Pair<V, K>>.groupBySecond(): Map<K, List<Pair<V, K>>> =
    groupBy { it.second }

fun <K, V> Sequence<Pair<K, V>>.groupByFirstAndSeparateValue(): Map<K, List<V>> =
    groupBy(
        keySelector = { it.first },
        valueTransform = { it.second },
    )

fun <K, V> Sequence<Pair<V, K>>.groupBySecondAndSeparateValue(): Map<K, List<V>> =
    groupBy(
        keySelector = { it.second },
        valueTransform = { it.first },
    )

inline fun <K, V, R> Sequence<Pair<K, V>>.groupByFirstAndMapSeparatedValue(
    transformValue: (key: K, values: List<V>) -> R,
): Map<K, R> =
    groupByFirstAndSeparateValue().mapValues { (key, values) ->
        transformValue(key, values)
    }

inline fun <K, V, R> Sequence<Pair<V, K>>.groupBySecondAndMapSeparatedValue(
    transformValue: (key: K, values: List<V>) -> R,
): Map<K, R> =
    groupBySecondAndSeparateValue().mapValues { (key, values) ->
        transformValue(key, values)
    }
