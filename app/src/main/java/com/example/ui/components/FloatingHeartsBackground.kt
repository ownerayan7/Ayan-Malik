package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.random.Random

private data class AmbientParticle(
    val initialXRatio: Float,
    val speed: Float,
    val size: Float,
    val alpha: Float,
    val type: Int, // 0 = star/sparkle, 1 = sakura flower petal, 2 = glowing bokeh
    val color: Color
)

@Composable
fun FloatingHeartsBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val progress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_progress"
    )

    val particles = remember {
        val random = Random(108)
        val colors = listOf(
            Color(0xFFFFD54F), // Gentle warm gold/star
            Color(0xFFFFB74D), // Soft amber
            Color(0xFFFFCCBC), // Peach petal
            Color(0xFFE1BEE7), // Soft lavender
            Color(0xFFFFF9C4), // Warm light yellow
            Color(0xFFFFCDD2)  // Soft petal
        )
        List(24) {
            AmbientParticle(
                initialXRatio = random.nextFloat(),
                speed = 0.45f + random.nextFloat() * 0.8f,
                size = 12f + random.nextFloat() * 16f,
                alpha = 0.22f + random.nextFloat() * 0.45f,
                type = random.nextInt(3),
                color = colors[random.nextInt(colors.size)]
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFF9F5), // Soft warm cream peach
                        Color(0xFFFAF0F8), // Gentle pastel
                        Color(0xFFF3EBF9)  // Soft soothing lavender
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            particles.forEach { p ->
                val currentY = (canvasH + p.size) - ((progress.value * p.speed * canvasH * 1.5f + (p.initialXRatio * canvasH)) % (canvasH + p.size * 2))
                val waveOffset = kotlin.math.sin((currentY / 55f).toDouble()).toFloat() * 14f
                val currentX = (p.initialXRatio * canvasW + waveOffset).coerceIn(0f, canvasW)

                when (p.type) {
                    0 -> drawFourPointStar(
                        centerX = currentX,
                        centerY = currentY,
                        radius = p.size * 0.8f,
                        color = p.color.copy(alpha = p.alpha)
                    )
                    1 -> drawPetal(
                        centerX = currentX,
                        centerY = currentY,
                        size = p.size,
                        color = p.color.copy(alpha = p.alpha * 0.8f)
                    )
                    else -> drawCircle(
                        color = p.color.copy(alpha = p.alpha * 0.6f),
                        radius = p.size * 0.6f,
                        center = Offset(currentX, currentY)
                    )
                }
            }
        }

        content()
    }
}

private fun DrawScope.drawFourPointStar(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color
) {
    val innerRadius = radius * 0.28f
    val path = Path().apply {
        moveTo(centerX, centerY - radius)
        cubicTo(centerX, centerY - innerRadius, centerX + innerRadius, centerY, centerX + radius, centerY)
        cubicTo(centerX + innerRadius, centerY, centerX, centerY + innerRadius, centerX, centerY + radius)
        cubicTo(centerX, centerY + innerRadius, centerX - innerRadius, centerY, centerX - radius, centerY)
        cubicTo(centerX - innerRadius, centerY, centerX, centerY - innerRadius, centerX, centerY - radius)
        close()
    }
    drawPath(path = path, color = color, style = Fill)
}

private fun DrawScope.drawPetal(
    centerX: Float,
    centerY: Float,
    size: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(centerX, centerY - size)
        cubicTo(
            centerX + size * 0.8f, centerY - size * 0.4f,
            centerX + size * 0.8f, centerY + size * 0.4f,
            centerX, centerY + size
        )
        cubicTo(
            centerX - size * 0.8f, centerY + size * 0.4f,
            centerX - size * 0.8f, centerY - size * 0.4f,
            centerX, centerY - size
        )
        close()
    }
    drawPath(path = path, color = color, style = Fill)
}
