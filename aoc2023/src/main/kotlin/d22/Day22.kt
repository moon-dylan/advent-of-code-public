package d22

import aoc.AdventOfCodeDay
import collections.toPair
import collections.toTriple
import collections.zipStrictOrNull
import ranges.intersectRange
import ranges.minusOffset
import tuples.vectors.Vec3
import tuples.vectors.map
import tuples.x
import tuples.y
import tuples.z

typealias Brick = Vec3<IntRange>

object Day22 : AdventOfCodeDay<Day22.InputData>() {
    // override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines.asSequence()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { line ->
                line.split("~")
                    .map { endPart -> endPart.trim().split(",").map { it.trim().toInt() } }
                    .toPair()
            }
            .map { (a, b) ->
                a.zipStrictOrNull(b)!!.toTriple().map { it.toList() }.map { it.min() .. it.max() }
            }
            .toSet()
            .let(::settleFalling)
            .let { settledBricks ->
                InputData(
                    settledBricks = settledBricks,
                    brickToSupporters = settledBricks.associateWith { brick ->
                        val bottomZ = brick.z.first - 1
                        settledBricks.filter {
                            it.z.last == bottomZ &&
                                brick.x.intersectRange(it.x) != null && brick.y.intersectRange(it.y) != null
                        }.toSet()
                    },
                    brickToSupporting = settledBricks.associateWith { brick ->
                        val topZ = brick.z.last + 1
                        settledBricks.filter {
                            it.z.first == topZ &&
                                brick.x.intersectRange(it.x) != null && brick.y.intersectRange(it.y) != null
                        }.toSet()
                    },
                )
            }

    override fun solvePart1(): Any =
        input.settledBricks
            .map { input.brickToSupporting[it].orEmpty() }
            .count { supports ->
                supports.isEmpty() || supports.all { 1 < input.brickToSupporters[it].orEmpty().size }
            }

    override fun solvePart2(): Any =
        input.settledBricks.sumOf { it.getFallingCount().toBigInteger() }

    private fun settleFalling(snapshot: Set<Brick>): Set<Brick> =
        snapshot
            .groupBy { it.z.first }
            .entries
            .sortedBy { it.key }
            .fold(setOf()) { settledBricks, (bottomZ, bricks) ->
                val newSettledBricks = bricks.map { brick ->
                    val highestUnder =
                        settledBricks
                            .filter { it.z.last < bottomZ }
                            .filter { it.x.intersectRange(brick.x) != null && it.y.intersectRange(brick.y) != null }
                            .maxOfOrNull { it.z.last } ?: 0

                    Triple(brick.x, brick.y, brick.z.minusOffset(bottomZ - highestUnder - 1))
                }

                settledBricks + newSettledBricks
            }

    private fun Brick.getFallingCount(): Int {
        val falling = mutableSetOf<Brick>()
        val working = mutableSetOf<Brick>()
        working.addAll(input.brickToSupporting[this].orEmpty())

        while (working.isNotEmpty()) {
            val current = working.minBy { it.z.first }.also { working.remove(it) }

            if ((input.brickToSupporters[current].orEmpty() - this - falling).isNotEmpty()) continue

            falling.add(current)
            working.addAll(input.brickToSupporting[current].orEmpty())
        }

        return falling.size
    }

    data class InputData(
        val settledBricks: Set<Brick>,
        val brickToSupporters: Map<Brick, Set<Brick>>,
        val brickToSupporting: Map<Brick, Set<Brick>>,
    )
}
