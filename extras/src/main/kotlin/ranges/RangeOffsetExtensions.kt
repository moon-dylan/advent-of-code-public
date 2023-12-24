package ranges

fun IntRange.plusOffset(value: Int): IntRange =
    (first + value) .. (last + value)

fun LongRange.plusOffset(value: Int): LongRange =
    (first + value) .. (last + value)

fun LongRange.plusOffset(value: Long): LongRange =
    (first + value) .. (last + value)

fun IntRange.minusOffset(value: Int): IntRange =
    (first - value) .. (last - value)

fun LongRange.minusOffset(value: Int): LongRange =
    (first - value) .. (last - value)

fun LongRange.minusOffset(value: Long): LongRange =
    (first - value) .. (last - value)
