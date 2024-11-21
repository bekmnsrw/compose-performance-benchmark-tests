package compose.performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import compose.performance.analysis.interoperability.InteropScreen
import compose.performance.analysis.lists.ListScreen
import compose.performance.analysis.nested.NestedScreen
import compose.performance.app.screen.AppScreen
import compose.performance.ui.theme.ComposePerformanceTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposePerformanceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { testTagsAsResourceId = true },
                    color = MaterialTheme.colorScheme.background,
                ) {
//                    AppScreen()
//                    NestedScreen()
//                    ListScreen()
                    InteropScreen()
                }
            }
        }
    }
}
