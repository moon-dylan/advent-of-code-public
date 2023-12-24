package d17

import aoc.AdventOfCodeDay
import collections.withGridIndex
import tuples.vectors.Vec2
import tuples.vectors.arithmetic.manhattanDistanceTo
import tuples.vectors.arithmetic.plus
import tuples.vectors.arithmetic.times
import tuples.x
import tuples.y

typealias XY = Vec2<Int>

object Day17 : AdventOfCodeDay<Day17.InputData>() {
    //        override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val heatMap =
            lines
                .filter { it.isNotBlank() }
                .withGridIndex()
                .flatten()
                .associate { (xy, c) -> xy to c.digitToInt() }

        val xRange = heatMap.map { (xy, _) -> xy.x }.let { xs -> xs.min() .. xs.max() }
        val yRange = heatMap.map { (xy, _) -> xy.y }.let { ys -> ys.min() .. ys.max() }

        return InputData(
            heatLossMap = heatMap,
            xRange = xRange,
            yRange = yRange,
            startXy = xRange.first to yRange.first,
            targetXy = xRange.last to yRange.last
        )
    }

    override fun solvePart1(): Any = getLowestHeatLoss(1 .. 3)
    override fun solvePart2(): Any = getLowestHeatLoss(4 .. 10)

    private fun CrucibleState.minBoundLossTo(target: XY): Int = xy.manhattanDistanceTo(target)

    private fun getLowestHeatLoss(turningStepRange: IntRange): Int {
        val stateToLoss = mutableMapOf<CrucibleState, Int>()
        val working = mutableSetOf<CrucibleState>()

        // assumption: this state has finished moving straight
        // assumption: produces states that have finished moving straight
        fun XY.moveInDirection(startLoss: Int, direction: Direction): Map<XY, Int> {
            val forcedLastLoss =
                (1 ..< turningStepRange.first)
                    .map { this@moveInDirection + direction.dXy * it }
                    .fold(startLoss) { lastLoss, newXy ->
                        lastLoss + (input.heatLossMap[newXy] ?: return emptyMap())
                    }

            return buildMap {
                turningStepRange
                    .map { this@moveInDirection + direction.dXy * it }
                    .fold(forcedLastLoss) { lastLoss, newXy ->
                        (lastLoss + (input.heatLossMap[newXy] ?: return@buildMap))
                            .also { set(newXy, it) }
                    }
            }
        }

        fun XY.iterateStates(startLoss: Int, axis: Axis) {
            val nextStepAxis = when (axis) {
                Axis.VERTICAL -> Axis.HORIZONTAL
                Axis.HORIZONTAL -> Axis.VERTICAL
            }

            val newStates =
                axis.turnDirections
                    .flatMap { direction ->
                        moveInDirection(startLoss, direction).entries
                            .map { (xy, loss) -> CrucibleState(xy, nextStepAxis) to loss }
                            .filter { (state, loss) ->
                                loss < (stateToLoss[state] ?: Int.MAX_VALUE)
                            }
                    }

            newStates.forEach { (state, newLoss) -> stateToLoss[state] = newLoss }
            working.addAll(newStates.map { it.first })
        }

        Axis.entries.forEach { input.startXy.iterateStates(0, it) }
        var minFoundLoss = Int.MAX_VALUE

        while (working.isNotEmpty()) {
            val current = working.minByOrNull { stateToLoss[it]!! } ?: break
            working.remove(current)
            val currentLoss = stateToLoss[current] ?: error("loss was not saved!")

            if (minFoundLoss <= currentLoss + current.minBoundLossTo(input.targetXy)) continue

            if (current.xy == input.targetXy) {
                minFoundLoss = currentLoss
                continue
            }

            current.xy.iterateStates(currentLoss, current.nextStepAxis)
        }

        return minFoundLoss
    }

    data class CrucibleState(val xy: XY, val nextStepAxis: Axis)

    enum class Axis { VERTICAL, HORIZONTAL }

    enum class Direction(val dXy: XY) {
        UP(0 to -1),
        RIGHT(1 to 0),
        DOWN(0 to 1),
        LEFT(-1 to 0),
    }

    private val Axis.turnDirections: Set<Direction>
        get() = when (this) {
            Axis.HORIZONTAL -> setOf(Direction.RIGHT, Direction.LEFT)
            Axis.VERTICAL -> setOf(Direction.UP, Direction.DOWN)
        }

    data class InputData(
        val heatLossMap: Map<XY, Int>,
        val xRange: IntRange,
        val yRange: IntRange,
        val startXy: XY,
        val targetXy: XY,
    )
}
