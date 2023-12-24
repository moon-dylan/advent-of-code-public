package collections

import sequences.infiniteSequence

val <E> Iterable<E>.cycled: Sequence<E>
    get() = infiniteSequence().flatMap { this@cycled }