package compose.performance.app.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tracing.trace
import coil.compose.AsyncImage
import compose.performance.databinding.AndroidViewImageLayoutBinding
import compose.performance.app.item.ContentItem
import compose.performance.app.item.ImageItem
import compose.performance.app.utils.ANDROID_VIEW_IMAGE
import compose.performance.app.utils.COLUMN_OF_CONTENT
import compose.performance.app.utils.CONTENT_ITEM
import compose.performance.app.utils.CONTENT_ITEMS
import compose.performance.app.utils.FAB
import compose.performance.app.utils.HEAVY_OPERATION_ON_UI
import compose.performance.app.utils.IMAGES
import compose.performance.app.utils.IMAGE_ITEM_TAG
import compose.performance.app.utils.IMAGE_ITEM_TAGS
import compose.performance.app.utils.LAZY_INIT
import compose.performance.app.utils.LOCAL_IMAGE
import compose.performance.app.utils.REMOTE_IMAGE
import compose.performance.app.utils.ROW_OF_IMAGES
import compose.performance.app.utils.a
import compose.performance.app.utils.recomposeHighlighter
import kotlin.random.Random

private val someHeavyItem = a()
private val stableRandom = Random(0)

private fun performSomeHeavyCalculations() { a() }

@Composable
fun AppScreen(viewModel: AppScreenViewModel = viewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val contentItems by viewModel.contentItems.collectAsStateWithLifecycle()

    // 1
    trace(HEAVY_OPERATION_ON_UI) {
        performSomeHeavyCalculations()
    }

    // 3
    trace(LAZY_INIT) {
        if (stableRandom.nextInt() % 2 == 0) {
            someHeavyItem.param1
            someHeavyItem.param2
            someHeavyItem.param3
            someHeavyItem.param4
            someHeavyItem.param5
            someHeavyItem.param6
            someHeavyItem.param7
        } else {
            Unit
        }
    }

    val context = LocalContext.current

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                println("Message received!")
            }
        }

        // 4
        context.registerReceiver(
            receiver,
            IntentFilter(Intent.ACTION_TIMEZONE_CHANGED),
        )

        onDispose {  }
    }

    ScreenContent(
        state = state,
        contentItems = contentItems,
        onAdd = { viewModel.addItem() }, // 5.3
        onRemove = { id -> viewModel.removeItem(id) }, // 5.3
        onCheck = { id, isChecked -> viewModel.checkItem(id, isChecked) }, // 5.3
    )
}

@Composable
private fun ScreenContent(
    state: AppScreenState,
    contentItems: List<ContentItem>, // 5.1
    onAdd: () -> Unit,
    onRemove: (Int) -> Unit,
    onCheck: (Int, Boolean) -> Unit,
) {
    Box {
        Column {
            Images(
                state = state,
            )
            Content(
                contentItems = contentItems,
                onRemove = onRemove,
                onCheck = onCheck,
            )
        }
        FloatingActionButton(
            onClick = onAdd,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .testTag(FAB),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun Images(
    state: AppScreenState,
) = trace(IMAGES) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(ROW_OF_IMAGES)
            .recomposeHighlighter(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = state.imageItems, // 6.1, 6.2, 6.4
        ) { imageItem ->
            when (imageItem) {
                is ImageItem.LocalImageItem -> LocalImage(
                    item = imageItem,
                    state = state,
                )
                is ImageItem.RemoteImageItem -> RemoteImage(
                    item = imageItem,
                    modifier = Modifier,
                    state = state,
                )
                is ImageItem.AndroidViewImage -> AndroidViewImage(
                    item = imageItem,
                    state = state,
                )
            }
        }
    }
}

@Composable
private fun Content(
    contentItems: List<ContentItem>,
    onRemove: (Int) -> Unit,
    onCheck: (Int, Boolean) -> Unit,
) = trace(CONTENT_ITEMS) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp)
            .verticalScroll(state = ScrollState(initial = 0)) // 6.4
            .testTag(COLUMN_OF_CONTENT)
            .recomposeHighlighter(),
    ) {
        contentItems.forEach { item ->
            ContentItem(
                item = item,
                onCheck = onCheck,
                onRemove = onRemove,
            )
        }
    }
}

@Composable
private fun LocalImage(
    item: ImageItem.LocalImageItem,
    state: AppScreenState,
) = trace(LOCAL_IMAGE) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        modifier = Modifier
            .size(
                height = 128.dp,
                width = 176.dp,
            )
            .recomposeHighlighter(),
    ) {
        Box {
            Image(
                painter = painterResource(item.drawableId), // 2.1
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(), // 2.2
            )
            ItemTags(
                tags = item.tags,
                modifier = Modifier.align(Alignment.BottomCenter),
                state = state,
            )
        }
    }
}

@Composable
private fun AndroidViewImage(
    item: ImageItem.AndroidViewImage,
    state: AppScreenState,
) = trace(ANDROID_VIEW_IMAGE) {
    AndroidViewBinding(AndroidViewImageLayoutBinding::inflate)
}

@Composable
private fun RemoteImage(
    item: ImageItem.RemoteImageItem,
    modifier: Modifier,
    state: AppScreenState,
) = trace(REMOTE_IMAGE) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        modifier = Modifier
            .size(
                height = 128.dp,
                width = 176.dp,
            )
            .recomposeHighlighter(),
    ) {
        Box {
            AsyncImage(
                model = item.url, // 2.1
                contentDescription = null,
                contentScale = ContentScale.Crop,
                // 2.2, 2.3, 6.5
                modifier = if (state.isLoading) Modifier.size(0.dp) else Modifier.fillMaxSize(),
            )
            ItemTags(
                tags = item.tags,
                modifier = modifier.align(Alignment.BottomCenter),
                state = state,
            )
        }
    }
}

@Composable
private fun ItemTags(
    tags: List<String>,
    modifier: Modifier,
    state: AppScreenState,
) = trace(IMAGE_ITEM_TAGS) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .recomposeHighlighter(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        tags.forEach { tag ->
            ItemTag(
                tag = tag,
                modifier = modifier,
                state = state,
            )
        }
    }
}

@Composable
private fun ItemTag(
    tag: String,
    modifier: Modifier,
    state: AppScreenState, // 5.2
) = trace(IMAGE_ITEM_TAG) {
    Text(
        text = tag,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 10.sp,
        maxLines = 1,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(2.dp),
    )
}

@Composable
private fun ContentItem(
    item: ContentItem,
    modifier: Modifier = Modifier,
    onCheck: (Int, Boolean) -> Unit,
    onRemove: (Int) -> Unit,
) = trace(CONTENT_ITEM) {
    // 6.6
    val someItem = a()

    Box(modifier = modifier.recomposeHighlighter()) {
        val (rowTonalElevation, iconBg) = when (item.type) {
            ContentItem.ContentType.REFERENCE -> 4.dp to MaterialTheme.colorScheme.primary
            ContentItem.ContentType.EQUALITY -> 0.dp to MaterialTheme.colorScheme.tertiary
        }

        ListItem(
            tonalElevation = rowTonalElevation,
            headlineContent = { Text(item.name) },
            leadingContent = {
                Text(
                    text = item.type.name.take(3),
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconBg, CircleShape)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            overlineContent = {
                Text("instance ${System.identityHashCode(item)}")
            },
            trailingContent = {
                Row {
                    Checkbox(checked = item.isChecked, onCheckedChange = { onCheck(item.id, it) })
                    IconButton(
                        onClick = {
                            onRemove(item.id)
                            someItem.run { param1 }
                        }
                    ) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Delete),
                            contentDescription = null,
                        )
                    }
                }
            },
        )

        if (item.isChecked) {
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }
    }
}
