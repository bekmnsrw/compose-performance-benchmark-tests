package compose.performance.analysis.nested

import androidx.annotation.DrawableRes
import compose.performance.R

sealed interface NestedItem {

    data class Simple(
        val text: String = "",
    ) : NestedItem

    data class Medium(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
    ) : NestedItem

    data class Complex(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
        val buttonText: String = "",
    ) : NestedItem
}
