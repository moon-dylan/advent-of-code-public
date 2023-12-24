package strings

fun String.zipStrictOrNull(other: String): List<Pair<Char, Char>>? =
    if (length == other.length) zip(other) else null