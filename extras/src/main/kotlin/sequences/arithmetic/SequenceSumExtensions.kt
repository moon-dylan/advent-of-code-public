package sequences.arithmetic

private fun Sequence<String>.sum(): String =
    fold("") { acc, s -> acc + s}