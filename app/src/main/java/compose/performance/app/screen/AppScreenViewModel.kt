package compose.performance.app.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.performance.R
import compose.performance.app.item.ContentItem
import compose.performance.app.item.ImageItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

class AppScreenViewModel : ViewModel() {

    private var incrementingKey = 0

    var latestDateChange by mutableStateOf<LocalDate>(LocalDate.now(), neverEqualPolicy())
        private set

    private val _state = MutableStateFlow(AppScreenState())
    val state = _state.asStateFlow()

    private val _contentItems = MutableStateFlow(emptyList<ContentItem>())
    val contentItems = _contentItems
        .map { simulateNewInstances(it) }
        .onEach { latestDateChange = LocalDate.now() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    isLoading = true,
                )
            )

            delay(3_000)

            _state.emit(
                _state.value.copy(
                    imageItems = generateImageItems(),
                    isLoading = false,
                )
            )
        }

        repeat(30) {
            addItem()
        }
    }

    private fun generateImageItems(): List<ImageItem> {
        return List(1_000) { index ->
            when (index % 3) {
                0 -> ImageItem.LocalImageItem(
                    id = index,
                    tags = sampleData.shuffled(stableRandom).take(4),
                    drawableId = poolOfLocalImageDrawables[stableRandom.nextInt(
                        poolOfLocalImageDrawables.size)],
                )
                1 -> ImageItem.RemoteImageItem(
                    id = index,
                    tags = sampleData.shuffled(stableRandom).take(4),
                    url = poolOfRemoteImageUrls[stableRandom.nextInt(poolOfRemoteImageUrls.size)],
                )
                else -> ImageItem.AndroidViewImage(
                    id = index,
                    drawableId = poolOfLocalImageDrawables[stableRandom.nextInt(
                        poolOfLocalImageDrawables.size)],
                )
            }
        }
    }

    fun addItem() {
        _contentItems.value += ContentItem(
            id = incrementingKey++,
            type = ContentItem.ContentType.fromId(incrementingKey),
            name = sampleData.random(stableRandom),
            isChecked = false,
            createdAt = LocalDateTime.now(),
        )
    }

    fun removeItem(id: Int) {
        _contentItems.value = _contentItems.value.filterNot { it.id == id }
    }

    fun checkItem(id: Int, isChecked: Boolean) {
        val contentItems = _contentItems.value.toMutableList()
        val index = contentItems.indexOfFirst { it.id == id }
        if (index < 0) return
        contentItems[index] = contentItems[index].copy(isChecked = isChecked)
        _contentItems.value = contentItems
    }
}

private fun simulateNewInstances(items: List<ContentItem>): List<ContentItem> {
    return items.map { item ->
        if (item.type == ContentItem.ContentType.REFERENCE) item.copy() else item
    }
}

private val poolOfRemoteImageUrls = listOf(
    "https://images.unsplash.com/photo-1568650108567-f040f546ce15?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1680807869086-e08309acfb24?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1534644107580-3a4dbd494a95?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8dGVzdHxlbnwwfHwwfHx8MA%3D%3D",
    "https://images.unsplash.com/photo-1606326608606-aa0b62935f2b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1460518451285-97b6aa326961?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
)

private val poolOfLocalImageDrawables = listOf(
    R.drawable.placeholder,
    R.drawable.placeholder_small,
    R.drawable.placeholder_vector,
    R.drawable.ic_launcher_background,
)

private val sampleData = LoremIpsum(500)
    .values.first()
    .split(" ")
    .map { word -> word.trim('.', ',', '\n') }

private val stableRandom = Random(0)
