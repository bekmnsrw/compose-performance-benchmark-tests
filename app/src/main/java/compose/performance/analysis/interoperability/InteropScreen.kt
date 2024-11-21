package compose.performance.analysis.interoperability

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.tracing.trace
import compose.performance.R
import compose.performance.analysis.COMPLEX_ANDROID_VIEW_SCREEN_ITEM
import compose.performance.analysis.COMPLEX_SCREEN_ITEM
import compose.performance.analysis.MEDIUM_ANDROID_VIEW_SCREEN_ITEM
import compose.performance.analysis.MEDIUM_SCREEN_ITEM
import compose.performance.analysis.SIMPLE_ANDROID_VIEW_SCREEN_ITEM
import compose.performance.analysis.SIMPLE_SCREEN_ITEM
import compose.performance.databinding.ComplexViewBinding
import compose.performance.databinding.MediumViewBinding
import compose.performance.databinding.SimpleViewBinding

@Composable
fun InteropScreen() {
    /**
     * Simple
     */
//    SimpleScreenItem(
//        item = ScreenItem.Simple(
//            text = "SimpleText",
//        ),
//    )

//    SimpleAndroidViewScreenItem()

    /**
     * Medium
     */
//    MediumScreenItem(
//        item = ScreenItem.Medium(
//            text = "MediumText",
//            image = R.drawable.placeholder_vector,
//        ),
//    )
//
//    MediumAndroidViewScreenItem()

    /**
     * Complex
     */
    ComplexScreenItem(
        item = ScreenItem.Complex(
            text = "ComplexText",
            image = R.drawable.placeholder_vector,
            buttonText = "Click Me!",
        ),
    )
//
//    ComplexAndroidViewScreenItem()
}

@Composable
private fun SimpleScreenItem(
    item: ScreenItem.Simple,
) = trace(SIMPLE_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box {
            Text(
                text = item.text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun MediumScreenItem(
    item: ScreenItem.Medium,
) = trace(MEDIUM_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
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
        }
    }
}

@Composable
private fun ComplexScreenItem(
    item: ScreenItem.Complex,
) = trace(COMPLEX_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
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
        }
    }
}

@Composable
private fun SimpleAndroidViewScreenItem() = trace(SIMPLE_ANDROID_VIEW_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidViewBinding(SimpleViewBinding::inflate)
    }
}

@Composable
private fun MediumAndroidViewScreenItem() = trace(MEDIUM_ANDROID_VIEW_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidViewBinding(MediumViewBinding::inflate)
    }
}

@Composable
private fun ComplexAndroidViewScreenItem() = trace(COMPLEX_ANDROID_VIEW_SCREEN_ITEM) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidViewBinding(ComplexViewBinding::inflate)
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    ComplexScreenItem(
        item = ScreenItem.Complex(
            text = "ComplexText",
            buttonText = "Click Me!",
        ),
    )
}
