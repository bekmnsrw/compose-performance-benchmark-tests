package compose.performance.analysis.interoperability

import androidx.annotation.DrawableRes
import compose.performance.R

sealed interface ScreenItem {

    data class Simple(
        val text: String = "",
    ) : ScreenItem

    data class Medium(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
    ) : ScreenItem

    data class Complex(
        val text: String = "",
        @DrawableRes val image: Int = R.drawable.placeholder_vector,
        val buttonText: String = "",
    ) : ScreenItem
}
