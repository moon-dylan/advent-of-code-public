package collections

fun <A, B> List<A>.zipStrictOrNull(other: List<B>): List<Pair<A, B>>? =
    if (size != other.size) null else zip(other)