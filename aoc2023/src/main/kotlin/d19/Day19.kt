package d19

import aoc.AdventOfCodeDay
import collections.arithmetic.multiplication
import collections.toNonEmpty
import tuples.vectors.map
import java.math.BigInteger

typealias Partition = Map<Day19.Category, IntRange>

object Day19 : AdventOfCodeDay<Day19.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val (workflowsPart, partsPart) = text.trim().split("\n\n+".toRegex()).map { it.trim() }

        val workflows =
            workflowsPart.split("\n+".toRegex())
                .map { line ->
                    val (namePart, restPart) = line.trim().split("{").map { it.trim() }
                    namePart to restPart.dropLast(1).split(",").map { it.trim() }
                }
                .map { (name, transitionParts) ->
                    val transitions = transitionParts.dropLast(1).map { part ->
                        val (conditionPart, destination) = part.split(":").map { it.trim() }
                        val (categoryPart, conditionValuePart) = conditionPart.split("[<>]".toRegex())
                        Condition(
                            category = when (categoryPart) {
                                "x" -> Category.X
                                "m" -> Category.M
                                "a" -> Category.A
                                "s" -> Category.S
                                else -> error("invalid category name")
                            },
                            op = when (conditionPart.drop(categoryPart.length).take(1)) {
                                ">" -> Operation.GT
                                "<" -> Operation.LT
                                else -> error("invalid operation")
                            },
                            rating = conditionValuePart.toInt(),
                        ) to destination
                    }

                    Workflow(
                        name = name,
                        conditionToWorkflowName = transitions,
                        endResult = transitionParts.last(),
                    )
                }

        val categories =
            partsPart.split("\n+".toRegex()).map { line ->
                line.drop(1).dropLast(1).split(",").associate { part ->
                    val (categoryPart, ratingPart) = part.split("=").map { it.trim() }
                    val category = when (categoryPart) {
                        "x" -> Category.X
                        "m" -> Category.M
                        "a" -> Category.A
                        "s" -> Category.S
                        else -> error("invalid category name")
                    }
                    category to ratingPart.toInt()
                }
            }

        return InputData(
            workflows = workflows,
            nameToWorkflow = workflows.associateBy { it.name },
            parts = categories,
        )
    }

    override fun solvePart1(): Any =
        input.parts
            .filter { it.isAcceptedPart() }
            .sumOf { part -> part.values.sumOf { it.toBigInteger() } }

    private fun Map<Category, Int>.isAcceptedPart(): Boolean {
        var currentWorkflow = input.nameToWorkflow["in"]!!

        while (true) {
            val workflow = currentWorkflow

            val nextName =
                workflow.conditionToWorkflowName.firstOrNull { (condition, _) ->
                    val rating = get(condition.category)!!
                    when (condition.op) {
                        Operation.GT -> rating > condition.rating
                        Operation.LT -> rating < condition.rating
                    }
                }?.let { (_, name) -> name } ?: workflow.endResult

            when (nextName) {
                "A" -> return true
                "R" -> return false
                else -> currentWorkflow = input.nameToWorkflow[nextName]!!
            }
        }
    }

    override fun solvePart2(): Any =
        getAcceptedPartitions().sumOf { partition ->
            partition.values
                .map { it.size.toBigInteger() }
                .toNonEmpty()
                ?.multiplication()
                ?: BigInteger.ZERO
        }

    private fun getAcceptedPartitions(): List<Partition> {
        val initialState =
            Category.entries.associateWith { (1 .. 4000) } to input.nameToWorkflow["in"]!!

        val working = ArrayDeque(listOf(initialState))
        val acceptedStates = mutableListOf<Partition>()

        while (working.isNotEmpty()) {
            val (currentPartition, currentWorkflow) = working.removeFirst()

            if (currentPartition.isEmpty()) continue

            currentWorkflow.partitionToDestinations(currentPartition)
                .mapNotNull { (partition, name) ->
                    when (name) {
                        "R" -> null
                        "A" -> {
                            acceptedStates.add(partition)
                            null
                        }
                        else -> partition to input.nameToWorkflow[name]!!
                    }
                }
                .let { working.addAll(it) }
        }

        return acceptedStates
    }

    private fun Workflow.partitionToDestinations(initialPartition: Partition): List<Pair<Partition, String>> =
        conditionToWorkflowName
            .fold(
                initialPartition to emptyList<Pair<Partition, String>>(),
            ) { (remainingPartition, separated), (condition, destination) ->
                if (remainingPartition.isEmpty()) return separated

                val (truePartition, falsePartition) = condition.partitionToTrueAndFalse(remainingPartition)

                falsePartition.orEmpty() to (truePartition?.let { separated + (it to destination) } ?: separated)
            }
            .let { (remaining, separated) -> separated + (remaining to endResult) }
            .filter { (partition) -> !partition.isEmpty }

    private fun Condition.partitionToTrueAndFalse(partition: Partition): Pair<Partition?, Partition?> {
        val axis = partition[category]!!

        return when (op) {
            Operation.GT -> {
                if (rating < axis.first) {
                    axis to null
                } else if (rating < axis.last) {
                    val falseAxis = axis.first .. rating
                    val trueAxis = rating + 1 .. axis.last
                    trueAxis to falseAxis
                } else {
                    null to axis
                }
            }
            Operation.LT -> {
                if (axis.last < rating) {
                    axis to null
                } else if (axis.first < rating) {
                    val falseAxis = rating .. axis.last
                    val trueAxis = axis.first ..< rating
                    trueAxis to falseAxis
                } else {
                    null to axis
                }
            }
        }.map { axisPartition ->
            axisPartition?.let { partition.plus(category to it) }
        }
    }

    private operator fun IntRange.minus(other: IntRange): Pair<IntRange?, IntRange?> =
        (first .. (minOf(other.first - 1, last)) to first .. (minOf(other.last + 1, first)))
            .map { range -> range.takeUnless { it.isEmpty() } }

    private val Partition.isEmpty get() = values.any { it.isEmpty() }

    private val IntRange.size get() = (last - first + 1).coerceAtLeast(0)

    data class InputData(
        val workflows: List<Workflow>,
        val nameToWorkflow: Map<String, Workflow>,
        val parts: List<Map<Category, Int>>,
    )

    enum class Category { X, M, A, S }

    data class Workflow(
        val name: String,
        val conditionToWorkflowName: List<Pair<Condition, String>>,
        val endResult: String,
    )

    data class Condition(
        val category: Category,
        val op: Operation,
        val rating: Int,
    )

    enum class Operation { GT, LT }
}
