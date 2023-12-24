package aoc

import java.io.File
import kotlin.reflect.KClass
import kotlin.system.measureNanoTime

abstract class AdventOfCodeDay<InputData> {
    abstract val inputFile: File
    protected val input: InputData by lazy {
        processInput(inputFile.readText(), inputFile.readLines())
    }

    abstract fun processInput(text: String, lines: List<String>): InputData
    abstract fun solvePart1(): Any?
    abstract fun solvePart2(): Any?

    protected fun runPart1() {
        printResultAndTime(1, ::solvePart1)
    }

    protected fun runPart2() {
        printResultAndTime(2, ::solvePart2)
    }

    private inline fun printResultAndTime(nr: Int, action: () -> Any?) {
        val (nanos, result) = getTimedResult(action)
        println(">> Part $nr result: \"${result}\" <<")
        println("  >> Elapsed: %,d ns (approx %,.3f ms) <<".format(nanos, nanos / 1_000_000.0))
    }

    private inline fun getTimedResult(action: () -> Any?) : Pair<Long, Any?> {
        var result: Any?
        return measureNanoTime { result = action() } to result
    }

    companion object {
        @JvmStatic
        protected fun <InputData, Day : AdventOfCodeDay<InputData>> KClass<Day>.getInputFile(
            isActualInput: Boolean = false,
        ) =
            getInputFile(if (isActualInput) "actualInput.txt" else "testInput.txt")

        @JvmStatic
        protected fun <InputData, Day : AdventOfCodeDay<InputData>> KClass<Day>.getInputFile(filename: String) =
            File(getKotlinClassDeclarationLocation(this), filename)
    }
}
