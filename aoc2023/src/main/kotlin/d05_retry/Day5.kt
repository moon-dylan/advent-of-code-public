package d05_retry

import aoc.AdventOfCodeDay
import d05_retry.Day5.Item.*

typealias InputData = Day5.GardenerMaps

object Day5 : AdventOfCodeDay<InputData>() {
    //    override val inputFile = this::class.getInputFile(isActualInput = false)
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
                .map { Item.Seed(it.trim().toLong()) }
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

    private fun <Source : Item, Target : Item> processMapPart(
        part: String,
        sourceConstructor: (Long) -> Source,
        targetConstructor: (Long) -> Target,
    ): GardenerMap<Source, Target> {
        return part.trim().split("\n").asSequence().drop(1)
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

    override fun solvePart2(): Any? {
        val max = input.seedRanges.maxOfOrNull { it.last } ?: return null
        return (0..max).firstOrNull { location ->
            val seed = input.locationToSeed(Location(location)).id
            input.seedRanges.any { seed in it }
        }
    }

    private fun GardenerMaps.seedToLocation(seed: Seed): Location =
        seedToSoil[seed]
            .let { soilToFertilizer[it] }
            .let { fertilizerToWater[it] }
            .let { waterToLight[it] }
            .let { lightToTemperature[it] }
            .let { temperatureToHumidity[it] }
            .let { humidityToLocation[it] }

    private fun GardenerMaps.locationToSeed(location: Location): Seed =
        humidityToLocation[location]
            .let { temperatureToHumidity[it] }
            .let { lightToTemperature[it] }
            .let { waterToLight[it] }
            .let { fertilizerToWater[it] }
            .let { soilToFertilizer[it] }
            .let { seedToSoil[it] }

    data class GardenerMaps(
        val seeds: Set<Seed>,
        val seedRanges: Set<LongRange>,
        val seedToSoil: GardenerMap<Seed, Soil>,
        val soilToFertilizer: GardenerMap<Soil, Fertilizer>,
        val fertilizerToWater: GardenerMap<Fertilizer, Water>,
        val waterToLight: GardenerMap<Water, Light>,
        val lightToTemperature: GardenerMap<Light, Temperature>,
        val temperatureToHumidity: GardenerMap<Temperature, Humidity>,
        val humidityToLocation: GardenerMap<Humidity, Location>,
    )

    data class GardenerMap<Source : Item, Target : Item>(
        val sourceToTarget: Set<Pair<LongRange, LongRange>>,
        val sourceConstructor: (Long) -> Source,
        val targetConstructor: (Long) -> Target,
    ) {
        @JvmName("getTargetFromSource")
        operator fun get(item: Source): Target = targetConstructor(sourceToTarget[item.id])
        @JvmName("getSourceFromTarget")
        operator fun get(item: Target): Source = sourceConstructor(sourceToTarget.getReverse(item.id))

        private operator fun Set<Pair<LongRange, LongRange>>.get(id: Long): Long =
            firstOrNull { (source, target) -> id in source }
                ?.let { (source, target) ->
                    val offset = id - source.first
                    target.first + offset
                }
                ?: id

        private fun Set<Pair<LongRange, LongRange>>.getReverse(id: Long): Long =
            firstOrNull { (target, source) -> id in source }
                ?.let { (target, source) ->
                    val offset = id - source.first
                    target.first + offset
                }
                ?: id
    }

    sealed interface Item {
        val id: Long

        data class Location(override val id: Long) : Item
        data class Humidity(override val id: Long) : Item
        data class Temperature(override val id: Long) : Item
        data class Light(override val id: Long) : Item
        data class Water(override val id: Long) : Item
        data class Fertilizer(override val id: Long) : Item
        data class Soil(override val id: Long) : Item
        data class Seed(override val id: Long) : Item
    }
}
