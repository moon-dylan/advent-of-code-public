package d02

import aoc.AdventOfCodeDay


typealias InputData = List<Day2.CubeGame>

object Day2 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData = lines.map { cubeGameOf(it) }

    private fun cubeGameOf(line: String): CubeGame {
        val (idPart, cubesPart) = line.split(":")
        val id = idPart.drop("Game ".length).toInt()
        val rounds = cubesPart.split(";").map { roundPart ->
            roundPart.split(",")
                .map { it.trim().split(" ") }
                .associate { (count, color) ->
                    when (color) {
                        "red" -> CubeColor.RED
                        "green" -> CubeColor.GREEN
                        "blue" -> CubeColor.BLUE
                        else -> error("Unknown cube color")
                    } to count.toInt()
                }
        }
        return CubeGame(id, rounds)
    }

    override fun solvePart1(): Any {
        val configuration = mapOf(CubeColor.RED to 12, CubeColor.GREEN to 13, CubeColor.BLUE to 14)

        return input.filter { game ->
            game.rounds.all { round ->
                round.all { (color, count) ->
                    count <= configuration[color]!!
                }
            }
        }.sumOf { it.id }
    }

    override fun solvePart2(): Any =
        input.sumOf { game ->
            game.rounds.fold(emptyMap<CubeColor, Int>()) { minimums, round ->
                round.entries.fold(minimums) { acc, (color, amount) ->
                    acc.plus(color to maxOf(amount, acc.getOrDefault(color, Int.MIN_VALUE)))
                }
            }.values.fold(1L) { acc, x -> acc * x }
        }

    enum class CubeColor { RED, GREEN, BLUE }

    data class CubeGame(
        val id: Int,
        val rounds: List<Map<CubeColor, Int>>,
    )
}