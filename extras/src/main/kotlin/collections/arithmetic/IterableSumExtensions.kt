package collections.arithmetic

private fun Iterable<String>.sum(): String =
    fold("") { acc, s -> acc + s}