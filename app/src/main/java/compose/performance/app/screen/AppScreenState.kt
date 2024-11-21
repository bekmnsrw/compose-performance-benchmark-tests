package compose.performance.app.screen

import compose.performance.app.item.ImageItem

data class AppScreenState(
    val imageItems: List<ImageItem> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    var unstableProperty: String = "",
)
