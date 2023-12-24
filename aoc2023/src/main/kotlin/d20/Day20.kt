package d20

import aoc.AdventOfCodeDay
import collections.filterFirstNotNull
import sequences.infiniteBigIntegerSequence
import java.math.BigInteger

object Day20 : AdventOfCodeDay<Day20.InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val moduleToOutputs = kotlin.run {
            val moduleToOutputNames =
                lines.map { it.trim() }.filter { it.isNotBlank() }.associate { line ->
                    val (modulePart, outputsPart) = line.split(" +-> +".toRegex()).map { it.trim() }

                    val module =
                        if (modulePart == "broadcaster") {
                            Module.Other("broadcaster")
                        } else if (modulePart.startsWith("%")) {
                            Module.FlipFlop(modulePart.drop(1))
                        } else if (modulePart.startsWith("&")) {
                            Module.Conjunction(modulePart.drop(1))
                        } else error("unknown module type!")

                    module to outputsPart.split(", *".toRegex()).map { it.trim() }
                }

            val nameToModule =
                moduleToOutputNames.keys.associateBy { it.name }

            moduleToOutputNames.mapValues { (_, values) ->
                values.map { nameToModule[it] ?: Module.Other(it) }
            }
        }

        val moduleToParent =
            moduleToOutputs
                .flatMap { (module, outputs) -> outputs.map { module to it } }
                .groupBy { (_, conj) -> conj }
                .mapValues { (_, inputs) -> inputs.map { it.first }.toSet() }

        val conjToInputs =
            moduleToParent
                .entries
                .map { (source, target) -> (source as? Module.Conjunction) to target }
                .filterFirstNotNull()
                .toMap()

        val rxParentParents =
            moduleToParent[Module.Other("rx")]!!.flatMap { moduleToParent[it]!! }.toSet()

        return InputData(
            broadcasterOutputs = moduleToOutputs[Module.Other("broadcaster")].orEmpty(),
            moduleToOutputs = moduleToOutputs,
            conjToInputs = conjToInputs,
            rxInverterInputs = rxParentParents,
        )
    }

    override fun solvePart1(): Any =
        (1 .. 1000)
            .fold(
                Configuration(turnedOnFlipFlops = emptySet(), conjToHighInputs = emptyMap()),
            ) { conf, _ ->
                input.pressButton(BigInteger.ZERO, conf)
            }
            .let { (it.lowPulseCount * it.highPulseCount) }

    // rx <- &kc <- {&ph, &vn, &kt, &hn }
    override fun solvePart2(): Any {
        var configuration = Configuration(turnedOnFlipFlops = emptySet(), conjToHighInputs = emptyMap())
        infiniteBigIntegerSequence()
            .takeWhile { rxInverterInputToLowsGapSize.keys != input.rxInverterInputs }
            .forEach { index ->
                if (index % 1_000_000.toBigInteger() == BigInteger.ZERO) println("Currently at press %,d".format(index))
                configuration = input.pressButton(index, configuration)
            }

        return rxInverterInputToLowsGapSize.values.fold(BigInteger.ONE) { acc, cycle -> acc * cycle }
    }

    private var rxInverterInputToLowsGapSize = mutableMapOf<Module, BigInteger>()
    private var rxInverterInputToFirstLowIndex = mutableMapOf<Module, BigInteger>()

    private fun InputData.pressButton(index: BigInteger, initialConfiguration: Configuration): Configuration {
        var configuration = initialConfiguration

        configuration = configuration.copy(lowPulseCount = configuration.lowPulseCount.inc()) // button -> broadcaster
        val working = ArrayDeque<Triple<Module, Pulse, Module>>(
            Module.Other("broadcaster").let { broadcaster ->
                moduleToOutputs[broadcaster].orEmpty().map { Triple(broadcaster, Pulse.LOW, it) }
            },
        )

        while (working.isNotEmpty()) {
            val (sender, inputPulse, currentModule) = working.removeFirst()

            configuration = configuration.let {
                when (inputPulse) {
                    Pulse.LOW -> it.copy(lowPulseCount = it.lowPulseCount.inc())
                    Pulse.HIGH -> it.copy(highPulseCount = it.highPulseCount.inc())
                }
            }

            if (currentModule in input.rxInverterInputs && inputPulse == Pulse.LOW) {
                val previousLowIndex = rxInverterInputToFirstLowIndex[currentModule]
                if (previousLowIndex == null) {
                    rxInverterInputToFirstLowIndex[currentModule] = index
                } else {
                    rxInverterInputToLowsGapSize[currentModule] = index - previousLowIndex
                }
            }

            when (currentModule) {
                is Module.Other -> continue
                is Module.Conjunction -> {
                    configuration = configuration.rememberPulse(currentModule, sender, inputPulse)
                    val highInputs = configuration.conjToHighInputs[currentModule].orEmpty()
                    val inputs = conjToInputs[currentModule].orEmpty()
                    val outPulse = if (inputs.minus(highInputs).isEmpty()) Pulse.LOW else Pulse.HIGH
                    val outputs = moduleToOutputs[currentModule].orEmpty()
                    working.addAll(outputs.map { Triple(currentModule, outPulse, it) })
                }

                is Module.FlipFlop -> {
                    if (inputPulse == Pulse.HIGH) continue
                    configuration = configuration.flipFlopSwitched(currentModule)
                    val outPulse = if (currentModule in configuration.turnedOnFlipFlops) Pulse.HIGH else Pulse.LOW
                    val outputs = moduleToOutputs[currentModule].orEmpty()
                    working.addAll(outputs.map { Triple(currentModule, outPulse, it) })
                }
            }
        }

        return configuration
    }

    private fun Configuration.flipFlopSwitched(flipFlop: Module.FlipFlop): Configuration =
        copy(
            turnedOnFlipFlops = if (flipFlop in turnedOnFlipFlops) {
                turnedOnFlipFlops - flipFlop
            } else {
                turnedOnFlipFlops + flipFlop
            },
        )

    private fun Configuration.rememberPulse(
        conjunction: Module.Conjunction,
        sender: Module,
        inputPulse: Pulse,
    ): Configuration {
        val newHighInputs =
            conjToHighInputs[conjunction].orEmpty().let { currentHighs ->
                when (inputPulse) {
                    Pulse.LOW -> currentHighs - setOfNotNull(sender)
                    Pulse.HIGH -> currentHighs + setOfNotNull(sender)
                }
            }

        return copy(conjToHighInputs = conjToHighInputs + (conjunction to newHighInputs))
    }

    data class InputData(
        val broadcasterOutputs: List<Module>,
        val moduleToOutputs: Map<Module, List<Module>>,
        val conjToInputs: Map<Module.Conjunction, Set<Module>>,
        val rxInverterInputs: Set<Module>,
    )

    data class Configuration(
        val turnedOnFlipFlops: Set<Module.FlipFlop>,
        val conjToHighInputs: Map<Module.Conjunction, Set<Module>>,
        val lowPulseCount: BigInteger = BigInteger.ZERO,
        val highPulseCount: BigInteger = BigInteger.ZERO,
    )

    sealed interface Module {
        val name: String

        data class FlipFlop(override val name: String) : Module
        data class Conjunction(override val name: String) : Module
        data class Other(override val name: String) : Module
    }

    enum class Pulse { LOW, HIGH }
}
