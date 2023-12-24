package d05

import aoc.AdventOfCodeDay
import d05.Day5.GardenerMap
import d05.Day5.GardenerMaps
import d05.Day5.Item.*

object Day5Optimisation : AdventOfCodeDay<InputData>() {
//        override val inputFile = this::class.getInputFile(isActualInput = false)
    override val inputFile = this::class.getInputFile(isActualInput = true)

    @JvmStatic
    fun main(args: Array<String>) {
        runPart1()
        runPart2()
    }

    override fun processInput(text: String, lines: List<String>): InputData {
        val parts = text.trim().split("\n\n+".toRegex())

        val seeds =
            parts[0].drop("seeds:".length).trim().split(" ")
                .map { Seed(it.trim().toLong()) }
                .toSet()

        val seedRanges =
            parts[0].drop("seeds:".length).trim().split(" ")
                .map { it.trim().toLong() }
                .chunked(2)
                .map { (start, length) ->
                    start..<start + length + 1
                }
                .toSet()


        return GardenerMaps(
            seeds = seeds,
            seedRanges = seedRanges,
            seedToSoil = processMapPart(parts[1], ::Seed, ::Soil),
            soilToFertilizer = processMapPart(parts[2], ::Soil, ::Fertilizer),
            fertilizerToWater = processMapPart(parts[3], ::Fertilizer, ::Water),
            waterToLight = processMapPart(parts[4], ::Water, ::Light),
            lightToTemperature = processMapPart(parts[5], ::Light, ::Temperature),
            temperatureToHumidity = processMapPart(parts[6], ::Temperature, ::Humidity),
            humidityToLocation = processMapPart(parts[7], ::Humidity, ::Location),
        )
    }

    private fun <Source : Day5.Item, Target : Day5.Item> processMapPart(
        part: String,
        sourceConstructor: (Long) -> Source,
        targetConstructor: (Long) -> Target,
    ): GardenerMap<Source, Target> {
        return part.trim().split("\n")
            .asSequence()
            .drop(1)
            .map { it.trim().split(" ") }
            .map { list -> list.map { it.trim() } }
            .map { (targetStart, sourceStart, count) ->
                listOf(targetStart.toLong(), sourceStart.toLong(), count.toLong())
            }
            .map { (targetStart, sourceStart, count) ->
                val source = (sourceStart..<sourceStart + count + 1)
                val target = (targetStart..<targetStart + count + 1)
                source to target
            }
            .toSet()
            .let { GardenerMap(it, sourceConstructor, targetConstructor) }
    }

    override fun solvePart1(): Any =
        input.seeds.minOf { input.seedToLocation(it).id }

    override fun solvePart2(): Any =
        input.seedRanges
            .associateWith { it }
            .map(input.seedToSoil.sourceToTarget)
            .map(input.soilToFertilizer.sourceToTarget)
            .map(input.fertilizerToWater.sourceToTarget)
            .map(input.waterToLight.sourceToTarget)
            .map(input.lightToTemperature.sourceToTarget)
            .map(input.temperatureToHumidity.sourceToTarget)
            .map(input.humidityToLocation.sourceToTarget)
            .entries
            .minOf { (_, locationRange) -> locationRange.first }

    private fun Map<LongRange, LongRange>.map(
        mapping: Set<Pair<LongRange, LongRange>>,
    ): Map<LongRange, LongRange> {
        val cutters =
            mapping
                .flatMap { (source, _) ->
                    listOf(source.first, source.last)
                }
                .distinct()
                .sorted()

        return partition(cutters)
            .mapValues { (_, source) ->
                mapping[source.first]..mapping[source.last]
            }
    }

    private fun Map<LongRange, LongRange>.partition(
        sortedCutters: List<Long>,
    ): Map<LongRange, LongRange> =
        flatMap { it.partition(sortedCutters) }.toMap()

    private fun Map.Entry<LongRange, LongRange>.partition(
        sortedCutters: List<Long>,
    ): List<Pair<LongRange, LongRange>> =
        sortedCutters.fold(listOf(this.toPair())) { acc, cutter ->
            val (seeds, source) = acc.last()
            if (cutter !in source || cutter == source.first) {
                acc
            } else {
                val rightSeedsFirst = seeds.first + (cutter - source.first)

                val leftSeeds = seeds.first..<rightSeedsFirst
                val rightSeeds = rightSeedsFirst..<seeds.last

                val leftRange = source.first..<cutter
                val rightRange = cutter..<source.last
                listOf(leftSeeds to leftRange, rightSeeds to rightRange)
            }
        }

    private operator fun Set<Pair<LongRange, LongRange>>.get(id: Long): Long =
        firstOrNull { (source, _) -> id in source }
            ?.let { (source, target) ->
                val offset = id - source.first
                target.first + offset
            }
            ?: id

    private fun GardenerMaps.seedToLocation(seed: Seed): Location =
        seedToSoil[seed]
            .let { soilToFertilizer[it] }
            .let { fertilizerToWater[it] }
            .let { waterToLight[it] }
            .let { lightToTemperature[it] }
            .let { temperatureToHumidity[it] }
            .let { humidityToLocation[it] }
}