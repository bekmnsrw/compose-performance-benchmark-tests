package compose.performance.app.item

import java.time.LocalDateTime

data class ContentItem(
    val id: Int,
    val type: ContentType,
    val name: String,
    val isChecked: Boolean,
    val createdAt: LocalDateTime,
    val unstableProperty: UnstableClass = UnstableClass(),
) {

    enum class ContentType {
        REFERENCE,
        EQUALITY;

        companion object {
            fun fromId(id: Int): ContentType {
                return if (id % 2 == 0) REFERENCE else EQUALITY
            }
        }
    }
}

data class UnstableClass(
    var varProperty: Int = 0,
    val unstableLambda: (UnstableParam) -> Unit = {},
    val unstableList: List<String> = emptyList(),
) {

    class UnstableParam(var varProperty: Int = 0)
}
