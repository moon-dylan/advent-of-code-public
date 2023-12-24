package collections

fun <E> List<E>.getFirstRepetitionPatternIndexAndSize(minSize: Int = 2): Pair<Int, Int>? =
    (0 ..< size - minSize).firstNotNullOfOrNull { startIndex ->
        val remainingFromPatternStart = drop(startIndex)

        remainingFromPatternStart.indexOfAll(remainingFromPatternStart.head!!).drop(1)
            .firstNotNullOfOrNull { repetitionIndex ->
                val chunks = remainingFromPatternStart.chunked(repetitionIndex)

                chunks
                    .filter { it.size == repetitionIndex }
                    .takeIf { 1 < it.size }
                    ?.distinct()
                    ?.singleOrNull()
                    ?.takeIf { it.startsWith(chunks.last()) }
                    ?.let { startIndex to it.size }
            }
    }

fun main() {
    println("xyzaababcaababc".toList().getFirstRepetitionPatternIndexAndSize())
    println("xyzaababc".toList().getFirstRepetitionPatternIndexAndSize())
}
