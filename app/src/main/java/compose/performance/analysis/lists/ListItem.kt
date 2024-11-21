package compose.performance.analysis.lists

import androidx.annotation.DrawableRes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import compose.performance.R
import kotlin.random.Random

sealed interface ListItem {

    data class Simple(
        val text: String = "",
    ) : ListItem

    data class Medium(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
    ) : ListItem

    data class Complex(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
        val buttonText: String = "",
    ) : ListItem
}

fun generateSimpleItems(size: Int): List<ListItem.Simple> {
    return List(size) { index ->
        ListItem.Simple(
            text = sampleData.shuffled(stableRandom).first(),
        )
    }
}

fun generateMediumItems(size: Int): List<ListItem.Medium> {
    return List(size) { index ->
        ListItem.Medium(
            text = sampleData.shuffled(stableRandom).first(),
            image = R.drawable.placeholder_vector,
        )
    }
}

fun generateComplexItems(size: Int): List<ListItem.Complex> {
    return List(size) { index ->
        ListItem.Complex(
            text = sampleData.shuffled(stableRandom).first(),
            image = R.drawable.placeholder_vector,
            buttonText = "Click Me!",
        )
    }
}

private val sampleData = LoremIpsum(500)
    .values.first()
    .split(" ")
    .map { word -> word.trim('.', ',', '\n') }

private val stableRandom = Random(0)
