package d24

import aoc.AdventOfCodeDay
import aoc.trimLinesAndFilterNotBlank
import collections.filterFirstNotNull
import collections.toTriple
import com.microsoft.z3.Context
import com.microsoft.z3.Status
/*import com.microsoft.z3.Context
import com.microsoft.z3.Status*/
import tuples.vectors.Vec2
import tuples.vectors.Vec3
import tuples.vectors.arithmetic.plus
import tuples.vectors.arithmetic.times
import tuples.vectors.arithmetic.unaryMinus
import tuples.vectors.map
import tuples.x
import tuples.xy
import tuples.y
import tuples.z
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

typealias InputData = List<Day24.Hailstone>
typealias XYZ = Vec3<BigInteger>
typealias XY = Vec2<BigDecimal>

object Day24 : AdventOfCodeDay<InputData>() {
//    override val inputFile = this::class.getInputFile(isActualInput = false)
//    private val testArea = 7.toBigDecimal() .. 27.toBigDecimal()

    override val inputFile = this::class.getInputFile(isActualInput = true)
    private val testArea =
        "200000000000000".toBigDecimal() .. "400000000000000".toBigDecimal()

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData =
        lines.trimLinesAndFilterNotBlank()
            .map { line ->
                line.trim().split(" @ ")
                    .map { it.trim() }
                    .map { part ->
                        part.split(", ")
                            .map { it.trim().toBigInteger() }
                            .toTriple()
                    }
            }
            .map { (pos, velocity) ->
                Hailstone(pos, velocity)
            }

    override fun solvePart1(): Any {
        val pairsToCheck =
            input.flatMapIndexed { i, a ->
                input.filterIndexed { j, _ -> i < j }.map { a to it }
            }

        return pairsToCheck
            .mapNotNull { (a, b) -> a.getCollisionInXyTo(b) }
            .filter { (x, y) -> x in testArea && y in testArea }
            .size
    }

    private fun Hailstone.getCollisionInXyTo(other: Hailstone): XY? {
        val thisPos = pos.xy.map { it.toBigDecimal() }
        val thisVelocity = velocity.xy.map { it.toBigDecimal() }

        val otherPos = other.pos.xy.map { it.toBigDecimal() }
        val otherVelocity = other.velocity.xy.map { it.toBigDecimal() }

        if (thisPos == otherPos) return thisPos

        val thisLine = toLineVectorXy()
        val otherLine = other.toLineVectorXy()

        if (thisLine == null) {
            // this is vertical line
            if (otherLine == null) {
                // both lines are vertical
                if (thisPos.x != otherPos.x) return null
                if (thisVelocity.y == otherVelocity.y) return null

                val collisionTime =
                    (otherPos.y - thisPos.y).divide(thisVelocity.y - otherVelocity.y, MathContext.DECIMAL128)
                return (thisPos + thisVelocity * collisionTime).takeIf { isInDirection(it) && other.isInDirection(it) }
            }

            val collisionY = otherLine.slope * thisPos.x + otherLine.offset
            return (thisPos.x to collisionY).takeIf { isInDirection(it) && other.isInDirection(it) }
        }

        if (otherLine == null) {
            // other is vertical line
            val collisionY = thisLine.slope * otherPos.x + thisLine.offset
            return (otherPos.x to collisionY).takeIf { isInDirection(it) && other.isInDirection(it) }
        }

        val intersection = thisLine.intersect(otherLine)
        if (intersection != null) return intersection.takeIf { isInDirection(it) && other.isInDirection(it) }

        // parallel lines
        if (thisVelocity.x == otherVelocity.x) return null
        if (thisLine.offset != otherLine.offset) return null

        val collisionTime =
            (otherPos.x - thisPos.x).divide(thisVelocity.x - otherVelocity.x, MathContext.DECIMAL128)
        return (thisPos + thisVelocity * collisionTime).takeIf { isInDirection(it) && other.isInDirection(it) }
    }

    private fun NonVerticalLine.intersect(other: NonVerticalLine): XY? {
        if (slope == other.slope) return null
        val collisionX = (other.offset - offset).divide(slope - other.slope, MathContext.DECIMAL128)
        val collisionY = slope * collisionX + offset
        return collisionX to collisionY
    }

    private fun Hailstone.isInDirection(point: XY): Boolean {
        val x = pos.x.toBigDecimal()
        val isOnX = if (velocity.x <= BigInteger.ZERO) point.x <= x else x < point.x

        val y = pos.y.toBigDecimal()
        val isOnY = if (velocity.y <= BigInteger.ZERO) point.y <= y else y < point.y

        return isOnX && isOnY
    }

    private fun Hailstone.toLineVectorXy(): NonVerticalLine? {
        if (velocity.x == BigDecimal.ZERO) return null  // vertical line
        val slope = velocity.y.toBigDecimal().divide(velocity.x.toBigDecimal(), MathContext.DECIMAL128)
        return NonVerticalLine(
            slope = slope,
            offset = pos.y.toBigDecimal() - slope * pos.x.toBigDecimal(),
        )
    }

    // TODO: extract types with utilities:
    //  - 2D line by points
    //  - 2D ray with origin
    data class NonVerticalLine(val slope: BigDecimal, val offset: BigDecimal)

    // TODO: extract Z3 API
    override fun solvePart2(): Any /*= Context().run*/ {
        /*val throwPos = "xyz".map { mkIntConst("pos_$it") }.toTriple()
        val throwVelocity = "xyz".map { mkIntConst("vel_$it") }.toTriple()

        val solver = mkSolver()

        input.take(3).forEachIndexed { i, hail ->
            val hailPos = hail.pos.map { mkInt(it.toLong()) }
            val hailVelocity = hail.velocity.map { mkInt(it.toLong()) }
            val time = mkIntConst("t_$i")

            solver.add(
                mkEq(
                    mkSub(throwPos.x, hailPos.x),
                    mkMul(time, mkSub(throwVelocity.x, hailVelocity.x)),
                ),
                mkEq(
                    mkSub(throwPos.y, hailPos.y),
                    mkMul(time, mkSub(throwVelocity.y, hailVelocity.y)),
                ),
                mkEq(
                    mkSub(throwPos.z, hailPos.z),
                    mkMul(time, mkSub(throwVelocity.z, hailVelocity.z)),
                ),
            )
        }

        solver.check().let { if (it != Status.SATISFIABLE) error("Solver status: $it") }
        throwPos.toList().sumOf { solver.model.eval(it, false).toString().toBigInteger() }*/
        return -1
    }

    data class Hailstone(val pos: XYZ, val velocity: XYZ)
}
