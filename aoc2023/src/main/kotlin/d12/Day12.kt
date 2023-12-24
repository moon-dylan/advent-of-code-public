package d12

import aoc.AdventOfCodeDay

typealias InputData = List<Pair<String, List<Int>>>

object Day12 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines.map { line ->
            val (springsPart, damagedPart) = line.trim().split(" ")
            val damaged = damagedPart.trim().split(",").map { it.trim().toInt() }
            springsPart.trim() to damaged
        }

    override fun solvePart1(): Any =
        input.sumOf { (springs, damaged) ->
            ArrangementCounter.count(springs, damaged)
        }

    override fun solvePart2(): Any =
        input.sumOf { (springs, damaged) ->
            ArrangementCounter.count(
                (1 .. 5).joinToString("?") { springs },
                (1 .. 5).flatMap { damaged },
            )
        }

    class ArrangementCounter private constructor() {
        private val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

        private fun rec(remainingSprings: String, remainingDamaged: List<Int>): Long {
            cache[remainingSprings to remainingDamaged]?.let { return it }

            if (remainingSprings.isEmpty()) return if (remainingDamaged.isEmpty()) 1 else 0
            if (remainingDamaged.isEmpty()) return if ('#' !in remainingSprings) 1 else 0

            val state = remainingSprings.first()

            if (state == '.') return rec(remainingSprings.drop(1), remainingDamaged)

            val whenDamaged = kotlin.run {
                val nextDamagedSize = remainingDamaged.first()
                val canBeDamaged =
                    (0 ..< nextDamagedSize)
                        .all { remainingSprings.getOrElse(it) { '.' } != '.' }
                        && remainingSprings.getOrNull(nextDamagedSize) != '#'

                if (canBeDamaged)
                    rec(remainingSprings.drop(nextDamagedSize + 1), remainingDamaged.drop(1))
                else
                    0
            }

            if (state == '#') return whenDamaged

            return (whenDamaged + rec(remainingSprings.drop(1), remainingDamaged))
                .also { cache[remainingSprings to remainingDamaged] = it }
        }

        companion object {
            fun count(springs: String, damaged: List<Int>): Long =
                ArrangementCounter().rec(springs, damaged)
        }
    }
}