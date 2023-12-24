package sequences

val <E> Sequence<E>.cycled: Sequence<E>
    get() = infiniteSequence().flatMap { this@cycled }