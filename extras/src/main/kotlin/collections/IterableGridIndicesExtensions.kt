package collections

fun Iterable<String>.withGridIndex(): List<List<Pair<Pair<Int, Int>, Char>>> =
    mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            (x to y) to c
        }
    }