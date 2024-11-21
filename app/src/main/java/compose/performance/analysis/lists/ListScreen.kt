package compose.performance.analysis.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import compose.performance.analysis.LAZY_COLUMN
import compose.performance.analysis.LAZY_COLUMN_TAG
import compose.performance.analysis.LAZY_ROW
import compose.performance.analysis.LAZY_ROW_TAG
import compose.performance.analysis.SCROLLABLE_COLUMN
import compose.performance.analysis.SCROLLABLE_COLUMN_TAG
import compose.performance.analysis.SCROLLABLE_ROW
import compose.performance.analysis.SCROLLABLE_ROW_TAG

@Composable
fun ListScreen() {
    val simpleItems = generateSimpleItems(6)
    val mediumItems = generateMediumItems(3)
    val complexItems = generateComplexItems(1)

//    ScrollableColumnList(complexItems)
//    LazyColumnList(complexItems)

//    ScrollableRowList(complexItems)
    LazyRowList(complexItems)
}

@Composable
private fun ScrollableColumnList(
    items: List<ListItem>,
) = trace(SCROLLABLE_COLUMN) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = ScrollState(initial = 0))
            .testTag(SCROLLABLE_COLUMN_TAG),
    ) {
        items.forEach { item ->
            ListItem(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ScrollableRowList(
    items: List<ListItem>,
) = trace(SCROLLABLE_ROW) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(state = ScrollState(initial = 0))
            .testTag(SCROLLABLE_ROW_TAG),
    ) {
        items.forEach { item ->
            ListItem(item)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun LazyColumnList(
    items: List<ListItem>,
) = trace(LAZY_COLUMN) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LAZY_COLUMN_TAG),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items) { item ->
            ListItem(item)
        }
    }
}

@Composable
private fun LazyRowList(
    items: List<ListItem>,
) = trace(LAZY_ROW) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LAZY_ROW_TAG),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items) { item ->
            ListItem(item)
        }
    }
}

@Composable
private fun ListItem(
    item: ListItem,
) {
    when (item) {
        is ListItem.Complex -> ComplexListItem(item)
        is ListItem.Medium -> MediumListItem(item)
        is ListItem.Simple -> SimpleListItem(item)
    }
}

@Composable
private fun SimpleListItem(
    item: ListItem.Simple,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        )
    }
}

@Composable
private fun MediumListItem(
    item: ListItem.Medium,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp),
        )
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
                .align(Alignment.CenterStart),
        )
    }
}

@Composable
private fun ComplexListItem(
    item: ListItem.Complex,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp),
        )
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
                .align(Alignment.CenterStart),
        )
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Text(
                text = item.buttonText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
