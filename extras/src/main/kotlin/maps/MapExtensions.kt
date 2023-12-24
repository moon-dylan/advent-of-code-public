package maps

import collections.filterKeysNotNull
import collections.filterValuesNotNull

fun <K, V, RK> Map<K, V>.mapFirsts(transform: (K) -> RK): Map<RK, V> =
    mapKeys { transform(it.key) }

fun <K, V, RV> Map<K, V>.mapSeconds(transform: (V) -> RV): Map<K, RV> =
    mapValues { transform(it.value) }

fun <K: Any, V> Map<K?, V>.filterKeysNotNull(): Map<K, V> =
    entries.filterKeysNotNull().toMap()

fun <K, V: Any> Map<K, V?>.filterValuesNotNull(): Map<K, V> =
    entries.filterValuesNotNull().toMap()