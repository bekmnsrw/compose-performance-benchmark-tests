package compose.performance.measure.app

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import compose.performance.measure.AbstractBenchmark
import compose.performance.measure.startActivity
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class AppBenchmark : AbstractBenchmark(StartupMode.COLD, 1) {

    @Test
    fun measureAppPerformance() = benchmark(CompilationMode.Partial())

    override val metrics: List<Metric> = listOf(
        StartupTimingMetric(),
        FrameTimingMetric(),
        TraceSectionMetric(ANDROID_VIEW_IMAGE, Mode.Sum),
        TraceSectionMetric(CONTENT_ITEM, Mode.Sum),
        TraceSectionMetric(CONTENT_ITEMS, Mode.Sum),
        TraceSectionMetric(HEAVY_OPERATION_ON_UI, Mode.Sum),
        TraceSectionMetric(IMAGES, Mode.Sum),
        TraceSectionMetric(IMAGE_ITEM_TAG, Mode.Sum),
        TraceSectionMetric(IMAGE_ITEM_TAGS, Mode.Sum),
        TraceSectionMetric(LAZY_INIT, Mode.Sum),
        TraceSectionMetric(LOCAL_IMAGE, Mode.Sum),
        TraceSectionMetric(REMOTE_IMAGE, Mode.Sum),
    )

    override fun MacrobenchmarkScope.measureBlock() {
        pressHome()
        startActivity()

        /**
         * Click on FAB
         */
        device.wait(Until.hasObject(By.res(FAB)), WAIT_TIMEOUT)
        val fab = device.findObject(By.res(FAB))

        repeat(times = 10) {
            fab.click()
            Thread.sleep(SLEEP_TIMEOUT)
        }

        /**
         * Scroll row of images
         */
        device.wait(Until.hasObject(By.res(ROW_OF_IMAGES)), WAIT_TIMEOUT)
        val imagesFeed = device.findObject(By.res(ROW_OF_IMAGES))

        repeat(times = 5) {
            imagesFeed.scroll(Direction.RIGHT, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }

        /**
         * Scroll column of content
         */
        device.wait(Until.hasObject(By.res(COLUMN_OF_CONTENT)), WAIT_TIMEOUT)
        val contentFeed = device.findObject(By.res(COLUMN_OF_CONTENT))

        repeat(times = 3) {
            contentFeed.scroll(Direction.DOWN, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }
    }

    private companion object {

        const val SCROLL_PERCENT = 1f
        const val SLEEP_TIMEOUT = 500L
        const val WAIT_TIMEOUT = 5_000L
    }
}
