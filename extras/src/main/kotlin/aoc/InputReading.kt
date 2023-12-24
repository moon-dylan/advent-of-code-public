package aoc

fun List<String>.trimLinesAndFilterNotBlank(): List<String> = trimLines().filterNotBlank()
fun List<String>.trimLines(): List<String> = map { it.trim() }
fun List<String>.filterNotBlank(): List<String> = filter { it.isNotBlank() }
