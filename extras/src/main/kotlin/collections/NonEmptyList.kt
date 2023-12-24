package collections

data class NonEmptyList<E>(
    val head: E,
    val tail: List<E> = emptyList(),
) : List<E> by (listOf(head) + tail) {
    constructor(head: E, vararg tailElements: E) : this(head, tailElements.toList())

    fun first(): E = head
    val first: E get() = head

    fun last(): E = tail.lastOrNull() ?: head
    val last: E get() = tail.lastOrNull() ?: head

    fun <R> map(transform: (element: E) -> R): NonEmptyList<R> =
        NonEmptyList(transform(head), tail.map(transform))

    val tails: NonEmptyList<List<E>> get() =
        NonEmptyList(
            head = listOf(head) + tail,
            tail = tail.tails
        )

    operator fun plus(element: E): NonEmptyList<E> =
        NonEmptyList(
            head = head,
            tail = tail + element,
        )

    operator fun plus(other: Iterable<E>): NonEmptyList<E> =
        NonEmptyList(
            head = head,
            tail = tail + other,
        )
}

fun <E> Iterable<E>.toNonEmpty(): NonEmptyList<E>? =
    firstOrNull()?.let { NonEmptyList(it, drop(1)) }

fun String.toNonEmpty(): NonEmptyList<Char>? =
    toList().toNonEmpty()
