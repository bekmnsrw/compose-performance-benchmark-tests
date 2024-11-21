package compose.performance.measure.analysis

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
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
class ListBenchmark : AbstractBenchmark(StartupMode.COLD){

    @Test
    fun measureListScreenComposable() = benchmark(CompilationMode.Partial())

    override val metrics: List<Metric> = listOf(
//        TraceSectionMetric(SCROLLABLE_COLUMN, Mode.Sum),
//        TraceSectionMetric(LAZY_COLUMN, Mode.Sum),

//        TraceSectionMetric(SCROLLABLE_ROW, Mode.Sum),
        TraceSectionMetric(LAZY_ROW, Mode.Sum),
    )

    override fun MacrobenchmarkScope.measureBlock() {
        pressHome()
        startActivity()
//        measureScrollableColumn()
//        measureLazyColumn()

//        measureScrollableRow()
        measureLazyRow()
    }

    private fun MacrobenchmarkScope.measureScrollableColumn() {
        device.wait(Until.hasObject(By.res(SCROLLABLE_COLUMN_TAG)), WAIT_TIMEOUT)
        val scrollableColumn = device.findObject(By.res(SCROLLABLE_COLUMN_TAG))

        repeat(times = 3) {
            scrollableColumn.scroll(Direction.DOWN, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }
    }

    private fun MacrobenchmarkScope.measureLazyColumn() {
        device.wait(Until.hasObject(By.res(LAZY_COLUMN_TAG)), WAIT_TIMEOUT)
        val scrollableColumn = device.findObject(By.res(LAZY_COLUMN_TAG))

        repeat(times = 3) {
            scrollableColumn.scroll(Direction.DOWN, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }
    }

    private fun MacrobenchmarkScope.measureScrollableRow() {
        device.wait(Until.hasObject(By.res(SCROLLABLE_ROW_TAG)), WAIT_TIMEOUT)
        val scrollableColumn = device.findObject(By.res(SCROLLABLE_ROW_TAG))

        repeat(times = 3) {
            scrollableColumn.scroll(Direction.RIGHT, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }
    }

    private fun MacrobenchmarkScope.measureLazyRow() {
        device.wait(Until.hasObject(By.res(LAZY_ROW_TAG)), WAIT_TIMEOUT)
        val scrollableColumn = device.findObject(By.res(LAZY_ROW_TAG))

        repeat(times = 3) {
            scrollableColumn.scroll(Direction.RIGHT, SCROLL_PERCENT)
            Thread.sleep(SLEEP_TIMEOUT)
        }
    }

    private companion object {

        const val SCROLL_PERCENT = 1f
        const val SLEEP_TIMEOUT = 500L
        const val WAIT_TIMEOUT = 5_000L
    }
}
