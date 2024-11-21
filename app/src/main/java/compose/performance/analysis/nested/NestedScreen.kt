package compose.performance.analysis.nested

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tracing.trace
import compose.performance.R
import compose.performance.analysis.NESTED_ITEM

private const val NESTED_LEVEL = 7
var currentNestedLevel = 0
//var currentPadding = 0

@Composable
fun NestedScreen() = trace(NESTED_ITEM) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
//        SimpleItem(
//            item = NestedItem.Simple(
//                text = "Level: 0",
//            ),
//        )

//        MediumItem(
//            item = NestedItem.Medium(
//                text = "Level: 0",
//                image = R.drawable.placeholder_vector,
//            ),
//        )

        ComplexItem(
            item = NestedItem.Complex(
                text = "Level: 0",
                image = R.drawable.placeholder_vector,
                buttonText = "Click Me!",
            ),
        )
    }
}

@Composable
private fun SimpleItem(
    item: NestedItem.Simple,
) {
    Box {
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(top = currentPadding.pxToDp()),
        )

        if (currentNestedLevel != NESTED_LEVEL) {
//            currentPadding += 32
            currentNestedLevel += 1

            SimpleItem(
                item = NestedItem.Simple(
                    text = "Level: $currentNestedLevel",
                ),
            )
        }
    }
}

@Composable
private fun MediumItem(
    item: NestedItem.Medium,
) {
    Box {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(144.dp),
        )
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Center),
        )

        if (currentNestedLevel != NESTED_LEVEL) {
            currentNestedLevel += 1

            MediumItem(
                item = NestedItem.Medium(
                    text = "Level: $currentNestedLevel",
                    image = R.drawable.placeholder_vector,
                ),
            )
        }
    }
}

@Composable
private fun ComplexItem(
    item: NestedItem.Complex,
) {
    Box {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(144.dp),
        )
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.TopCenter),
        )
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Text(
                text = item.buttonText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        if (currentNestedLevel != NESTED_LEVEL) {
            currentNestedLevel += 1

            ComplexItem(
                item = NestedItem.Complex(
                    text = "Level: $currentNestedLevel",
                    image = R.drawable.placeholder_vector,
                    buttonText = "Click Me!"
                ),
            )
        }
    }
}
