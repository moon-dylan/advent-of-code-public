package uncategorized

tailrec fun <T> fixpoint(x: T, function: (T) -> T): T {
    val fx = function(x)
    return if (x == fx) x else fixpoint(fx, function)
}

fun <T> takeFixpointOnAdded(x: Set<T>, function: (Set<T>) -> Set<T>): Set<T> {
    var xNew = x
    var fx = function(xNew)

    while (xNew != fx) {
        val added = fx.subtract(xNew)
        xNew = fx
        fx = fx + function(added)
    }

    return fx
}