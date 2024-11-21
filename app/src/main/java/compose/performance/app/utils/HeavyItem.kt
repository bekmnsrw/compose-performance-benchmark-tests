package compose.performance.app.utils

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import kotlin.random.Random

data class HeavyItem(
    val param1: Int = 0,
    val param2: String = "",
    val param3: Boolean = false,
    val param4: Char = 'a',
    val param5: Long = 0,
    val param6: List<String> = emptyList(),
    val param7: Map<Int, String> = emptyMap(),
)

fun a(): HeavyItem {
    return HeavyItem(
        param1 = stableRandom.nextInt(),
        param2 = generateRandomString(),
        param3 = stableRandom.nextBoolean(),
        param4 = generateRandomChar(),
        param5 = stableRandom.nextLong(),
        param6 = generateRandomList(),
        param7 = generateRandomMap(),
    )
}

private fun generateRandomList(): List<String> = List(10_000) {
    sampleData.random(stableRandom)
}

private fun generateRandomMap(): Map<Int, String> {
    val randomWords = List(10_000) { sampleData.random(stableRandom) }
    val resultMap: MutableMap<Int, String> = mutableMapOf()
    randomWords.forEachIndexed { index, word ->
        resultMap.put(index, word)
    }
    return resultMap
}

private fun generateRandomString(): String {
    return sampleData.random(stableRandom)
}

private fun generateRandomChar(): Char {
    val randomWord = sampleData.random(stableRandom)
    return randomWord.random(stableRandom)
}

private val stableRandom = Random(0)

private val sampleData = LoremIpsum(500)
    .values.first()
    .split(" ")
    .map { word -> word.trim('.', ',', '\n') }

