package compose.performance.app.item

import androidx.annotation.DrawableRes

sealed class ImageItem {

    abstract val id: Int
    abstract val tags: List<String>

    data class LocalImageItem(
        override val id: Int,
        override val tags: List<String>,
        @DrawableRes val drawableId: Int,
    ): ImageItem()

    data class RemoteImageItem(
        override val id: Int,
        override val tags: List<String>,
        val url: String,
    ): ImageItem()

    data class AndroidViewImage(
        override val id: Int = 0,
        override val tags: List<String> = emptyList(),
        @DrawableRes val drawableId: Int,
    ) : ImageItem()
}
