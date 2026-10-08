package com.ht9945.designkit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.theme.DK
import com.ht9945.designkit.theme.DKShapes
import com.ht9945.designkit.theme.DKType

enum class DKTone { Accent, Success, Warning, Danger, Neutral }

@Composable
fun DKBadge(text: String, modifier: Modifier = Modifier, tone: DKTone = DKTone.Accent) {
    val c = DK.colors
    val color = when (tone) {
        DKTone.Accent -> c.accentEnd
        DKTone.Success -> c.success
        DKTone.Warning -> c.warning
        DKTone.Danger -> c.danger
        DKTone.Neutral -> c.textSecondary
    }
    Text(
        text,
        style = DKType.caption,
        color = color,
        modifier = modifier
            .background(color.copy(alpha = 0.14f), DKShapes.chip)
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

/** Dairesel ilerleme göstergesi; ortasına içerik koyulabilir. */
@Composable
fun DKProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 112.dp,
    strokeWidth: Dp = 12.dp,
    content: @Composable () -> Unit = {},
) {
    val c = DK.colors
    val animated by animateFloatAsState(progress.coerceIn(0f, 1f), label = "ring")
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(size)) {
            val stroke = strokeWidth.toPx()
            val arcSize = Size(this.size.width - stroke, this.size.height - stroke)
            val topLeft = Offset(stroke / 2, stroke / 2)
            drawArc(
                color = c.track,
                startAngle = 0f, sweepAngle = 360f, useCenter = false,
                topLeft = topLeft, size = arcSize,
                style = Stroke(stroke),
            )
            if (animated > 0f) {
                drawArc(
                    brush = c.accentBrush,
                    startAngle = -90f, sweepAngle = 360f * animated, useCenter = false,
                    topLeft = topLeft, size = arcSize,
                    style = Stroke(stroke, cap = StrokeCap.Round),
                )
            }
        }
        content()
    }
}

/** Basit çubuk grafik. values: 0..1 arası oran. */
@Composable
fun DKBarChart(
    values: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 160.dp,
    labelEvery: Int = 1,
) {
    val c = DK.colors
    Column(modifier) {
        Canvas(Modifier.fillMaxWidth().height(chartHeight)) {
            val n = values.size
            if (n == 0) return@Canvas
            val cell = size.width / n
            val barW = cell * 0.62f
            val radius = CornerRadius(minOf(barW / 2f, 8.dp.toPx()))
            values.forEachIndexed { i, v ->
                val x = i * cell + (cell - barW) / 2f
                drawRoundRect(
                    color = c.track,
                    topLeft = Offset(x, 0f),
                    size = Size(barW, size.height),
                    cornerRadius = radius,
                )
                val h = (size.height * v.coerceIn(0f, 1f)).coerceAtLeast(6.dp.toPx())
                drawRoundRect(
                    brush = c.accentBrush,
                    topLeft = Offset(x, size.height - h),
                    size = Size(barW, h),
                    cornerRadius = radius,
                )
            }
        }
        Row(Modifier.fillMaxWidth().padding(top = 8.dp)) {
            labels.forEachIndexed { i, label ->
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    if (i % labelEvery == 0) {
                        Text(
                            label,
                            style = DKType.caption,
                            color = c.textSecondary,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.wrapContentWidth(unbounded = true),
                        )
                    }
                }
            }
        }
    }
}
