package d16

import aoc.AdventOfCodeDay
import collections.filterSecondsNotNull
import collections.product
import collections.withGridIndex
import d16.Day16.BeamDirection.*
import sequences.infiniteIntSequence
import sequences.takeWhileInclusive
import tuples.arithmetic.plus
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.plus
import tuples.vectors.arithmetic.times
import tuples.x
import tuples.y
import uncategorized.fixpoint

typealias XY = Vec2<Int>
typealias Beam = Pair<XY, Day16.BeamDirection>

object Day16 : AdventOfCodeDay<Day16.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        InputData(
            width = lines[0].length,
            height = lines.size,
            mirrors = lines.withGridIndex().flatten()
                .map { (xy, c) ->
                    xy to MirrorDirection.entries.firstOrNull { it.c == c }
                }
                .filterSecondsNotNull()
                .toMap(),
        )

    override fun solvePart1() = getEnergized(0 to 0, RIGHT).size

    override fun solvePart2() =
        input.xRange.product(0).product(DOWN)
            .plus(input.xRange.product(input.yRange.last).product(UP))
            .plus(0.product(input.yRange).product(RIGHT))
            .plus(input.xRange.last.product(input.yRange).product(LEFT))
            .maxOf { (start, direction) -> getEnergized(start, direction).size }

    private fun getEnergized(start: XY, startDirection: BeamDirection): Set<XY> =
        fixpoint(
            setOf(start to startDirection) to setOf(start to startDirection),
        ) { (added, beams) ->
            val (heads, addedBeams) =
                added.fold(
                    setOf<Beam>() to setOf<Beam>(),
                ) { acc, (xy, direction) ->
                    acc + getNextBeam(xy, direction)
                }

            val newBeams =
                addedBeams
                    .filter { (xy, _) -> xy.x in input.xRange && xy.y in input.yRange }
                    .union(beams)

            val newHeads =
                heads
                    .filter { (xy, _) -> xy.x in input.xRange && xy.y in input.yRange }
                    .subtract(beams)

            newHeads to newBeams
        }
            .second
            .map { it.first }
            .toSet()

    private fun getNextBeam(xy: XY, direction: BeamDirection): Pair<Set<Beam>, Set<Beam>> {
        val (headAndMirror: Pair<Beam, MirrorDirection>?, straightBeam: Set<Beam>) =
            infiniteIntSequence()
                .map { xy + it * direction.dXy }
                .takeWhileInclusive { it !in input.mirrors }
                .takeWhile { (x, y) -> x in input.xRange && y in input.yRange }
                .map { it to direction }
                .toList()
                .let { beam ->
                    beam.lastOrNull()?.let { head ->
                        input.mirrors[head.first]?.let { head to it }
                    } to beam.toSet()
                }

        if (headAndMirror == null) return emptySet<Beam>() to straightBeam
        val (head, mirror) = headAndMirror
        return mirror
            .reflect(head.second)
            .map { (head.first + it.dXy) to it }
            .toSet()
            .let { it to straightBeam + it }
    }

    private fun MirrorDirection.reflect(currentDirection: BeamDirection): Set<BeamDirection> =
        when (this) {
            MirrorDirection.V -> when (currentDirection) {
                UP -> setOf(UP)
                RIGHT -> setOf(UP, DOWN)
                DOWN -> setOf(DOWN)
                LEFT -> setOf(UP, DOWN)
            }
            MirrorDirection.H -> when (currentDirection) {
                UP -> setOf(LEFT, RIGHT)
                RIGHT -> setOf(RIGHT)
                DOWN -> setOf(LEFT, RIGHT)
                LEFT -> setOf(LEFT)
            }
            MirrorDirection.TL -> when (currentDirection) {
                UP -> setOf(LEFT)
                RIGHT -> setOf(DOWN)
                DOWN -> setOf(RIGHT)
                LEFT -> setOf(UP)
            }
            MirrorDirection.TR -> when (currentDirection) {
                UP -> setOf(RIGHT)
                RIGHT -> setOf(UP)
                DOWN -> setOf(LEFT)
                LEFT -> setOf(DOWN)
            }
        }

    enum class MirrorDirection(val c: Char) { V('|'), H('-'), TL('\\'), TR('/') }

    enum class BeamDirection(dX: Int, dY: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0),
        ;

        val dXy: XY = dX to dY
    }

    data class InputData(
        val width: Int,
        val height: Int,
        val mirrors: Map<XY, MirrorDirection>,
    ) {
        val xRange = 0 ..< width
        val yRange = 0 ..< height
    }
}
