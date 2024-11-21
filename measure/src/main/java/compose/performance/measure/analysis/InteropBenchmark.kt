package compose.performance.measure.analysis

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingGfxInfoMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import compose.performance.measure.AbstractBenchmark
import compose.performance.measure.startActivity
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class InteropBenchmark : AbstractBenchmark(StartupMode.COLD){

    @Test
    fun measureInteropScreenComposable() = benchmark(CompilationMode.Partial())

    override val metrics: List<Metric> = listOf(
        FrameTimingGfxInfoMetric(),
        TraceSectionMetric(COMPLEX_SCREEN_ITEM, Mode.Sum),
    )

    override fun MacrobenchmarkScope.measureBlock() {
        pressHome()
        startActivity()
        Thread.sleep(SLEEP_TIMEOUT)
    }

    private companion object {

        const val SLEEP_TIMEOUT = 1_000L
    }
}
