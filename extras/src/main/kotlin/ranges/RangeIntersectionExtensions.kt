package ranges

fun IntRange.intersectRange(other: IntRange): IntRange? =
    (maxOf(first, other.first) .. minOf(last, other.last))
        .takeUnless { other.last < first || last < other.first }

fun UIntRange.intersectRange(other: UIntRange): UIntRange? =
    (maxOf(first, other.first) .. minOf(last, other.last))
        .takeUnless { other.last < first || last < other.first }

fun LongRange.intersectRange(other: LongRange): LongRange? =
    (maxOf(first, other.first) .. minOf(last, other.last))
        .takeUnless { other.last < first || last < other.first }

fun ULongRange.intersectRange(other: ULongRange): ULongRange? =
    (maxOf(first, other.first) .. minOf(last, other.last))
        .takeUnless { other.last < first || last < other.first }
