package com.example.ui.illustrations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.sin

/**
 * Renders charming, kid-friendly 2D animated illustrations for every alphabet item (A-Z).
 * Supports gentle autonomous motion (floating/blinking) and an interactive spring bounce on tap.
 */
@Composable
fun AlphabetIllustration(
    letter: String,
    modifier: Modifier = Modifier,
    onTap: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val tapScale = remember { Animatable(1f) }
    val tapRotation = remember { Animatable(0f) }

    // Gentle continuous floating animation for a living, friendly feel
    val infiniteTransition = rememberInfiniteTransition(label = "gentleMotion")
    val floatY by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY"
    )
    val gentleWobble by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wobble"
    )

    Box(
        modifier = modifier
            .testTag("illustration_${letter.lowercase()}")
            .graphicsLayer {
                translationY = floatY
                scaleX = tapScale.value
                scaleY = tapScale.value
                rotationZ = gentleWobble + tapRotation.value
            }
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                coroutineScope.launch {
                    tapScale.animateTo(1.15f, tween(120, easing = FastOutSlowInEasing))
                    tapRotation.animateTo(6f, tween(80))
                    tapRotation.animateTo(-6f, tween(80))
                    tapRotation.animateTo(0f, tween(80))
                    tapScale.animateTo(1f, tween(150, easing = FastOutSlowInEasing))
                }
                onTap()
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            when (letter.uppercase()) {
                "A" -> drawApple()
                "B" -> drawBear()
                "C" -> drawCat()
                "D" -> drawDuck()
                "E" -> drawElephant()
                "F" -> drawFish()
                "G" -> drawGiraffe()
                "H" -> drawHouse()
                "I" -> drawIceCream()
                "J" -> drawJuice()
                "K" -> drawKite()
                "L" -> drawLion()
                "M" -> drawMonkey()
                "N" -> drawNest()
                "O" -> drawOrange()
                "P" -> drawPenguin()
                "Q" -> drawQueenCrown()
                "R" -> drawRainbow()
                "S" -> drawSun()
                "T" -> drawTrain()
                "U" -> drawUmbrella()
                "V" -> drawVan()
                "W" -> drawWhale()
                "X" -> drawXylophone()
                "Y" -> drawYoYo()
                "Z" -> drawZebra()
                else -> drawApple()
            }
        }
    }
}

// -------------------------------------------------------------
// INDIVIDUAL VECTOR ART DRAW FUNCTIONS FOR EACH LETTER (A-Z)
// -------------------------------------------------------------

private fun DrawScope.drawApple() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Apple Body (Juicy red double lobe)
    drawCircle(
        color = Color(0xFFE53935),
        radius = r,
        center = Offset(cx - r * 0.28f, cy + r * 0.1f)
    )
    drawCircle(
        color = Color(0xFFD32F2F),
        radius = r,
        center = Offset(cx + r * 0.28f, cy + r * 0.1f)
    )

    // Highlight
    drawCircle(
        color = Color(0xFFFF8A80),
        radius = r * 0.22f,
        center = Offset(cx - r * 0.5f, cy - r * 0.1f)
    )

    // Stem
    val stemPath = Path().apply {
        moveTo(cx, cy - r * 0.7f)
        cubicTo(cx - 5f, cy - r * 1.1f, cx + 15f, cy - r * 1.3f, cx + 8f, cy - r * 1.4f)
    }
    drawPath(stemPath, color = Color(0xFF5D4037), style = Stroke(width = r * 0.12f, cap = StrokeCap.Round))

    // Green Leaf
    val leafPath = Path().apply {
        moveTo(cx + 8f, cy - r * 1.1f)
        cubicTo(cx + r * 0.8f, cy - r * 1.4f, cx + r * 0.7f, cy - r * 0.7f, cx + 8f, cy - r * 0.9f)
    }
    drawPath(leafPath, color = Color(0xFF43A047))

    // Smiling Eyes & Mouth
    drawSmilingFace(cx, cy + r * 0.1f, r * 0.4f)
}

private fun DrawScope.drawBear() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Ears
    drawCircle(Color(0xFF6D4C41), radius = r * 0.38f, center = Offset(cx - r * 0.8f, cy - r * 0.7f))
    drawCircle(Color(0xFFFFCCBC), radius = r * 0.22f, center = Offset(cx - r * 0.8f, cy - r * 0.7f))
    drawCircle(Color(0xFF6D4C41), radius = r * 0.38f, center = Offset(cx + r * 0.8f, cy - r * 0.7f))
    drawCircle(Color(0xFFFFCCBC), radius = r * 0.22f, center = Offset(cx + r * 0.8f, cy - r * 0.7f))

    // Head
    drawCircle(Color(0xFF8D6E63), radius = r, center = Offset(cx, cy))

    // Snout
    drawOval(Color(0xFFD7CCC8), topLeft = Offset(cx - r * 0.45f, cy + r * 0.05f), size = Size(r * 0.9f, r * 0.7f))
    // Nose
    drawOval(Color(0xFF3E2723), topLeft = Offset(cx - r * 0.2f, cy + r * 0.15f), size = Size(r * 0.4f, r * 0.25f))

    // Eyes
    drawCircle(Color(0xFF212121), radius = r * 0.1f, center = Offset(cx - r * 0.4f, cy - r * 0.2f))
    drawCircle(Color.White, radius = r * 0.035f, center = Offset(cx - r * 0.43f, cy - r * 0.23f))
    drawCircle(Color(0xFF212121), radius = r * 0.1f, center = Offset(cx + r * 0.4f, cy - r * 0.2f))
    drawCircle(Color.White, radius = r * 0.035f, center = Offset(cx + r * 0.37f, cy - r * 0.23f))

    // Cheerful smile
    val smilePath = Path().apply {
        moveTo(cx - r * 0.15f, cy + r * 0.5f)
        cubicTo(cx - r * 0.05f, cy + r * 0.65f, cx + r * 0.05f, cy + r * 0.65f, cx + r * 0.15f, cy + r * 0.5f)
    }
    drawPath(smilePath, color = Color(0xFF3E2723), style = Stroke(width = 6f, cap = StrokeCap.Round))
}

private fun DrawScope.drawCat() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Ears (Triangles)
    val leftEar = Path().apply {
        moveTo(cx - r * 0.8f, cy - r * 0.2f)
        lineTo(cx - r * 0.85f, cy - r * 1.15f)
        lineTo(cx - r * 0.25f, cy - r * 0.75f)
        close()
    }
    val rightEar = Path().apply {
        moveTo(cx + r * 0.8f, cy - r * 0.2f)
        lineTo(cx + r * 0.85f, cy - r * 1.15f)
        lineTo(cx + r * 0.25f, cy - r * 0.75f)
        close()
    }
    drawPath(leftEar, Color(0xFFFF9800))
    drawPath(rightEar, Color(0xFFFF9800))

    // Inner pink ears
    drawCircle(Color(0xFFFFCDD2), radius = r * 0.2f, center = Offset(cx - r * 0.65f, cy - r * 0.75f))
    drawCircle(Color(0xFFFFCDD2), radius = r * 0.2f, center = Offset(cx + r * 0.65f, cy - r * 0.75f))

    // Kitty Face
    drawCircle(Color(0xFFFFB74D), radius = r, center = Offset(cx, cy))

    // Whiskers
    drawLine(Color(0xFF5D4037), Offset(cx - r * 0.9f, cy + r * 0.1f), Offset(cx - r * 0.4f, cy + r * 0.15f), strokeWidth = 5f)
    drawLine(Color(0xFF5D4037), Offset(cx - r * 0.9f, cy + r * 0.3f), Offset(cx - r * 0.4f, cy + r * 0.25f), strokeWidth = 5f)
    drawLine(Color(0xFF5D4037), Offset(cx + r * 0.9f, cy + r * 0.1f), Offset(cx + r * 0.4f, cy + r * 0.15f), strokeWidth = 5f)
    drawLine(Color(0xFF5D4037), Offset(cx + r * 0.9f, cy + r * 0.3f), Offset(cx + r * 0.4f, cy + r * 0.25f), strokeWidth = 5f)

    // Nose
    drawCircle(Color(0xFFE91E63), radius = r * 0.08f, center = Offset(cx, cy + r * 0.15f))
    drawSmilingFace(cx, cy - r * 0.1f, r * 0.45f)
}

private fun DrawScope.drawDuck() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.32f

    // Body
    drawOval(Color(0xFFFFD54F), topLeft = Offset(cx - r * 0.9f, cy - r * 0.1f), size = Size(r * 1.8f, r * 1.3f))
    // Wing
    drawOval(Color(0xFFFFC107), topLeft = Offset(cx - r * 0.4f, cy + r * 0.1f), size = Size(r * 0.9f, r * 0.6f))
    // Head
    drawCircle(Color(0xFFFFEE58), radius = r * 0.65f, center = Offset(cx + r * 0.4f, cy - r * 0.5f))
    // Beak
    val beak = Path().apply {
        moveTo(cx + r * 0.85f, cy - r * 0.65f)
        lineTo(cx + r * 1.45f, cy - r * 0.45f)
        lineTo(cx + r * 0.85f, cy - r * 0.25f)
        close()
    }
    drawPath(beak, Color(0xFFFF6D00))
    // Eye
    drawCircle(Color(0xFF212121), radius = r * 0.09f, center = Offset(cx + r * 0.5f, cy - r * 0.65f))
    drawCircle(Color.White, radius = r * 0.03f, center = Offset(cx + r * 0.48f, cy - r * 0.68f))

    // Water ripple
    drawArc(
        Color(0xFF81D4FA),
        0f, 180f, false,
        topLeft = Offset(cx - r * 1.2f, cy + r * 0.9f),
        size = Size(r * 2.4f, r * 0.4f),
        style = Stroke(8f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawElephant() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Large friendly ears
    drawCircle(Color(0xFF9FA8DA), radius = r * 0.6f, center = Offset(cx - r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFFC5CAE9), radius = r * 0.4f, center = Offset(cx - r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFF9FA8DA), radius = r * 0.6f, center = Offset(cx + r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFFC5CAE9), radius = r * 0.4f, center = Offset(cx + r * 0.8f, cy - r * 0.1f))

    // Head
    drawCircle(Color(0xFF7986CB), radius = r, center = Offset(cx, cy))

    // Trunk curving upward cheerfully
    val trunk = Path().apply {
        moveTo(cx - r * 0.2f, cy + r * 0.3f)
        cubicTo(cx - r * 0.2f, cy + r * 1.1f, cx + r * 0.6f, cy + r * 1.2f, cx + r * 0.6f, cy + r * 0.8f)
        cubicTo(cx + r * 0.4f, cy + r * 0.8f, cx + r * 0.1f, cy + r * 0.9f, cx + r * 0.05f, cy + r * 0.3f)
        close()
    }
    drawPath(trunk, Color(0xFF5C6BC0))

    // Eyes
    drawCircle(Color(0xFF212121), radius = r * 0.1f, center = Offset(cx - r * 0.35f, cy - r * 0.15f))
    drawCircle(Color.White, radius = r * 0.035f, center = Offset(cx - r * 0.38f, cy - r * 0.18f))
    drawCircle(Color(0xFF212121), radius = r * 0.1f, center = Offset(cx + r * 0.35f, cy - r * 0.15f))
    drawCircle(Color.White, radius = r * 0.035f, center = Offset(cx + r * 0.32f, cy - r * 0.18f))
}

private fun DrawScope.drawFish() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Tail fin
    val tail = Path().apply {
        moveTo(cx - r * 0.6f, cy)
        lineTo(cx - r * 1.4f, cy - r * 0.7f)
        lineTo(cx - r * 1.1f, cy)
        lineTo(cx - r * 1.4f, cy + r * 0.7f)
        close()
    }
    drawPath(tail, Color(0xFFFF7043))

    // Body
    drawOval(Color(0xFF26C6DA), topLeft = Offset(cx - r * 0.8f, cy - r * 0.55f), size = Size(r * 1.8f, r * 1.1f))

    // Stripes
    drawArc(Color(0xFF00ACC1), -60f, 120f, false, topLeft = Offset(cx - r * 0.4f, cy - r * 0.5f), size = Size(r * 0.6f, r * 1f), style = Stroke(8f))
    drawArc(Color(0xFF00ACC1), -60f, 120f, false, topLeft = Offset(cx - r * 0.1f, cy - r * 0.5f), size = Size(r * 0.6f, r * 1f), style = Stroke(8f))

    // Eye
    drawCircle(Color.White, radius = r * 0.16f, center = Offset(cx + r * 0.5f, cy - r * 0.1f))
    drawCircle(Color(0xFF212121), radius = r * 0.09f, center = Offset(cx + r * 0.55f, cy - r * 0.1f))

    // Bubbles
    drawCircle(Color(0xFF80DEEA), radius = r * 0.12f, center = Offset(cx + r * 1.1f, cy - r * 0.5f))
    drawCircle(Color(0xFFB2EBF2), radius = r * 0.08f, center = Offset(cx + r * 1.25f, cy - r * 0.8f))
}

private fun DrawScope.drawGiraffe() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Neck & Head
    drawRoundRect(Color(0xFFFFB74D), topLeft = Offset(cx - r * 0.25f, cy - r * 0.3f), size = Size(r * 0.5f, r * 1.3f), cornerRadius = CornerRadius(16f, 16f))
    drawCircle(Color(0xFFFFB74D), radius = r * 0.55f, center = Offset(cx, cy - r * 0.4f))

    // Horns (Ossicones)
    drawLine(Color(0xFF795548), Offset(cx - r * 0.2f, cy - r * 0.8f), Offset(cx - r * 0.25f, cy - r * 1.15f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawCircle(Color(0xFF5D4037), radius = r * 0.12f, center = Offset(cx - r * 0.25f, cy - r * 1.18f))
    drawLine(Color(0xFF795548), Offset(cx + r * 0.2f, cy - r * 0.8f), Offset(cx + r * 0.25f, cy - r * 1.15f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawCircle(Color(0xFF5D4037), radius = r * 0.12f, center = Offset(cx + r * 0.25f, cy - r * 1.18f))

    // Spots
    drawCircle(Color(0xFFE65100), radius = r * 0.15f, center = Offset(cx - r * 0.05f, cy + r * 0.1f))
    drawCircle(Color(0xFFE65100), radius = r * 0.18f, center = Offset(cx + r * 0.05f, cy + r * 0.6f))

    // Muzzle & Smile
    drawOval(Color(0xFFFFE0B2), topLeft = Offset(cx - r * 0.35f, cy - r * 0.3f), size = Size(r * 0.7f, r * 0.45f))
    drawSmilingFace(cx, cy - r * 0.55f, r * 0.35f)
}

private fun DrawScope.drawHouse() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.38f

    // Wall (Square)
    drawRoundRect(Color(0xFFFFF9C4), topLeft = Offset(cx - r * 0.8f, cy - r * 0.2f), size = Size(r * 1.6f, r * 1.2f), cornerRadius = CornerRadius(16f, 16f))

    // Roof (Triangle)
    val roof = Path().apply {
        moveTo(cx - r * 1.05f, cy - r * 0.15f)
        lineTo(cx, cy - r * 1.1f)
        lineTo(cx + r * 1.05f, cy - r * 0.15f)
        close()
    }
    drawPath(roof, Color(0xFFE53935))

    // Door
    drawRoundRect(Color(0xFF795548), topLeft = Offset(cx - r * 0.25f, cy + r * 0.3f), size = Size(r * 0.5f, r * 0.7f), cornerRadius = CornerRadius(12f, 12f))
    drawCircle(Color(0xFFFFD54F), radius = r * 0.05f, center = Offset(cx + r * 0.12f, cy + r * 0.65f))

    // Window
    drawRoundRect(Color(0xFF81D4FA), topLeft = Offset(cx + r * 0.35f, cy), size = Size(r * 0.35f, r * 0.35f), cornerRadius = CornerRadius(6f, 6f))
}

private fun DrawScope.drawIceCream() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Waffle Cone (Triangle)
    val cone = Path().apply {
        moveTo(cx - r * 0.55f, cy + r * 0.05f)
        lineTo(cx + r * 0.55f, cy + r * 0.05f)
        lineTo(cx, cy + r * 1.25f)
        close()
    }
    drawPath(cone, Color(0xFFFFB74D))

    // Scoops of ice cream
    drawCircle(Color(0xFF81C784), radius = r * 0.45f, center = Offset(cx - r * 0.25f, cy - r * 0.1f)) // Mint
    drawCircle(Color(0xFFF06292), radius = r * 0.5f, center = Offset(cx, cy - r * 0.4f)) // Strawberry

    // Cherry on top!
    drawCircle(Color(0xFFC2185B), radius = r * 0.16f, center = Offset(cx, cy - r * 0.95f))
    val stem = Path().apply {
        moveTo(cx, cy - r * 1.05f)
        cubicTo(cx + 10f, cy - r * 1.25f, cx + 25f, cy - r * 1.3f, cx + 30f, cy - r * 1.35f)
    }
    drawPath(stem, Color(0xFF5D4037), style = Stroke(6f, cap = StrokeCap.Round))

    // Smiling face on strawberry scoop
    drawSmilingFace(cx, cy - r * 0.4f, r * 0.25f)
}

private fun DrawScope.drawJuice() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Glass (Trapezoid)
    val glass = Path().apply {
        moveTo(cx - r * 0.65f, cy - r * 0.8f)
        lineTo(cx + r * 0.65f, cy - r * 0.8f)
        lineTo(cx + r * 0.45f, cy + r * 0.9f)
        lineTo(cx - r * 0.45f, cy + r * 0.9f)
        close()
    }
    drawPath(glass, Color(0xFFFFAB91))

    // Orange Juice Liquid
    val juice = Path().apply {
        moveTo(cx - r * 0.6f, cy - r * 0.5f)
        lineTo(cx + r * 0.6f, cy - r * 0.5f)
        lineTo(cx + r * 0.42f, cy + r * 0.85f)
        lineTo(cx - r * 0.42f, cy + r * 0.85f)
        close()
    }
    drawPath(juice, Color(0xFFFF6D00))

    // Straw (Red & white diagonal)
    drawLine(Color(0xFFE53935), Offset(cx + r * 0.1f, cy + r * 0.5f), Offset(cx + r * 0.6f, cy - r * 1.3f), strokeWidth = 14f, cap = StrokeCap.Round)

    // Smiling face on glass
    drawSmilingFace(cx, cy + r * 0.15f, r * 0.3f)
}

private fun DrawScope.drawKite() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.38f

    // Diamond Body
    val kite = Path().apply {
        moveTo(cx, cy - r * 1.05f)
        lineTo(cx + r * 0.75f, cy - r * 0.1f)
        lineTo(cx, cy + r * 0.85f)
        lineTo(cx - r * 0.75f, cy - r * 0.1f)
        close()
    }
    drawPath(kite, Color(0xFFBA68C8))

    // Cross sticks
    drawLine(Color(0xFF4A148C), Offset(cx, cy - r * 1.05f), Offset(cx, cy + r * 0.85f), strokeWidth = 5f)
    drawLine(Color(0xFF4A148C), Offset(cx - r * 0.75f, cy - r * 0.1f), Offset(cx + r * 0.75f, cy - r * 0.1f), strokeWidth = 5f)

    // Tail ribbon
    val tail = Path().apply {
        moveTo(cx, cy + r * 0.85f)
        cubicTo(cx - 30f, cy + r * 1.15f, cx + 40f, cy + r * 1.35f, cx - 10f, cy + r * 1.55f)
    }
    drawPath(tail, Color(0xFFFF5722), style = Stroke(6f, cap = StrokeCap.Round))

    // Cheerful kite face
    drawSmilingFace(cx, cy - r * 0.1f, r * 0.3f)
}

private fun DrawScope.drawLion() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.34f

    // Mane (Puffy sun-like petals)
    for (i in 0 until 12) {
        val angle = Math.toRadians((i * 30).toDouble())
        val mx = cx + (r * 0.85f * kotlin.math.cos(angle)).toFloat()
        val my = cy + (r * 0.85f * sin(angle)).toFloat()
        drawCircle(Color(0xFFE65100), radius = r * 0.36f, center = Offset(mx, my))
    }

    // Head
    drawCircle(Color(0xFFFFB300), radius = r * 0.75f, center = Offset(cx, cy))

    // Ears
    drawCircle(Color(0xFFFFB300), radius = r * 0.22f, center = Offset(cx - r * 0.55f, cy - r * 0.6f))
    drawCircle(Color(0xFFFFB300), radius = r * 0.22f, center = Offset(cx + r * 0.55f, cy - r * 0.6f))

    // Snout
    drawOval(Color(0xFFFFF8E1), topLeft = Offset(cx - r * 0.3f, cy + r * 0.05f), size = Size(r * 0.6f, r * 0.45f))
    drawOval(Color(0xFF3E2723), topLeft = Offset(cx - r * 0.15f, cy + r * 0.1f), size = Size(r * 0.3f, r * 0.2f))

    drawSmilingFace(cx, cy - r * 0.1f, r * 0.35f)
}

private fun DrawScope.drawMonkey() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Ears
    drawCircle(Color(0xFF5D4037), radius = r * 0.35f, center = Offset(cx - r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFFFFCCBC), radius = r * 0.2f, center = Offset(cx - r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFF5D4037), radius = r * 0.35f, center = Offset(cx + r * 0.8f, cy - r * 0.1f))
    drawCircle(Color(0xFFFFCCBC), radius = r * 0.2f, center = Offset(cx + r * 0.8f, cy - r * 0.1f))

    // Head
    drawCircle(Color(0xFF6D4C41), radius = r * 0.75f, center = Offset(cx, cy))

    // Heart shaped muzzle
    drawOval(Color(0xFFFFCCBC), topLeft = Offset(cx - r * 0.45f, cy - r * 0.05f), size = Size(r * 0.9f, r * 0.65f))

    drawSmilingFace(cx, cy - r * 0.15f, r * 0.35f)
}

private fun DrawScope.drawNest() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Blue Eggs
    drawOval(Color(0xFF81D4FA), topLeft = Offset(cx - r * 0.5f, cy - r * 0.5f), size = Size(r * 0.45f, r * 0.65f))
    drawOval(Color(0xFF4FC3F7), topLeft = Offset(cx + r * 0.05f, cy - r * 0.5f), size = Size(r * 0.45f, r * 0.65f))

    // Twig Nest Bowl
    drawArc(Color(0xFF795548), 0f, 180f, true, topLeft = Offset(cx - r * 0.95f, cy - r * 0.25f), size = Size(r * 1.9f, r * 1.3f))
    // Twigs texture
    drawLine(Color(0xFF4E342E), Offset(cx - r * 0.8f, cy + r * 0.1f), Offset(cx + r * 0.8f, cy + r * 0.3f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawLine(Color(0xFF3E2723), Offset(cx - r * 0.7f, cy + r * 0.4f), Offset(cx + r * 0.6f, cy + r * 0.2f), strokeWidth = 8f, cap = StrokeCap.Round)
}

private fun DrawScope.drawOrange() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Orange Circle
    drawCircle(Color(0xFFFF6D00), radius = r, center = Offset(cx, cy))
    drawCircle(Color(0xFFFFAB40), radius = r * 0.22f, center = Offset(cx - r * 0.4f, cy - r * 0.3f))

    // Stem & Leaf
    drawCircle(Color(0xFF2E7D32), radius = r * 0.15f, center = Offset(cx, cy - r * 0.95f))
    val leaf = Path().apply {
        moveTo(cx, cy - r * 0.95f)
        cubicTo(cx + r * 0.6f, cy - r * 1.3f, cx + r * 0.5f, cy - r * 0.7f, cx, cy - r * 0.95f)
    }
    drawPath(leaf, Color(0xFF43A047))

    drawSmilingFace(cx, cy, r * 0.4f)
}

private fun DrawScope.drawPenguin() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Body (Black)
    drawOval(Color(0xFF263238), topLeft = Offset(cx - r * 0.7f, cy - r * 0.9f), size = Size(r * 1.4f, r * 1.8f))

    // Belly (White)
    drawOval(Color.White, topLeft = Offset(cx - r * 0.45f, cy - r * 0.5f), size = Size(r * 0.9f, r * 1.3f))

    // Flippers
    drawOval(Color(0xFF263238), topLeft = Offset(cx - r * 1.05f, cy - r * 0.2f), size = Size(r * 0.45f, r * 0.9f))
    drawOval(Color(0xFF263238), topLeft = Offset(cx + r * 0.6f, cy - r * 0.2f), size = Size(r * 0.45f, r * 0.9f))

    // Beak
    val beak = Path().apply {
        moveTo(cx - r * 0.2f, cy - r * 0.45f)
        lineTo(cx + r * 0.2f, cy - r * 0.45f)
        lineTo(cx, cy - r * 0.25f)
        close()
    }
    drawPath(beak, Color(0xFFFF9800))

    // Feet
    drawOval(Color(0xFFFF9800), topLeft = Offset(cx - r * 0.55f, cy + r * 0.8f), size = Size(r * 0.45f, r * 0.25f))
    drawOval(Color(0xFFFF9800), topLeft = Offset(cx + r * 0.1f, cy + r * 0.8f), size = Size(r * 0.45f, r * 0.25f))

    // Eyes
    drawCircle(Color(0xFF212121), radius = r * 0.08f, center = Offset(cx - r * 0.25f, cy - r * 0.6f))
    drawCircle(Color(0xFF212121), radius = r * 0.08f, center = Offset(cx + r * 0.25f, cy - r * 0.6f))
}

private fun DrawScope.drawQueenCrown() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.38f

    // Crown Body
    val crown = Path().apply {
        moveTo(cx - r * 0.9f, cy + r * 0.5f)
        lineTo(cx - r * 1.0f, cy - r * 0.3f)
        lineTo(cx - r * 0.4f, cy + r * 0.05f)
        lineTo(cx, cy - r * 0.7f)
        lineTo(cx + r * 0.4f, cy + r * 0.05f)
        lineTo(cx + r * 1.0f, cy - r * 0.3f)
        lineTo(cx + r * 0.9f, cy + r * 0.5f)
        close()
    }
    drawPath(crown, Color(0xFFFFD54F))

    // Jewels
    drawCircle(Color(0xFFE91E63), radius = r * 0.12f, center = Offset(cx - r * 1.0f, cy - r * 0.3f))
    drawCircle(Color(0xFF00E676), radius = r * 0.15f, center = Offset(cx, cy - r * 0.7f))
    drawCircle(Color(0xFF00B0FF), radius = r * 0.12f, center = Offset(cx + r * 1.0f, cy - r * 0.3f))

    // Smiling face on crown band
    drawSmilingFace(cx, cy + r * 0.25f, r * 0.28f)
}

private fun DrawScope.drawRainbow() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    val colors = listOf(
        Color(0xFFE53935), Color(0xFFFF9800), Color(0xFFFFEB3B),
        Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFF9C27B0)
    )

    for (i in colors.indices) {
        val radiusOffset = r * (1.1f - i * 0.12f)
        drawArc(
            color = colors[i],
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(cx - radiusOffset, cy - radiusOffset * 0.7f),
            size = Size(radiusOffset * 2f, radiusOffset * 1.4f),
            style = Stroke(width = r * 0.1f, cap = StrokeCap.Round)
        )
    }

    // Smiling Cloud on left
    drawCircle(Color(0xFFE1F5FE), radius = r * 0.32f, center = Offset(cx - r * 0.8f, cy + r * 0.2f))
    drawCircle(Color(0xFFB3E5FC), radius = r * 0.24f, center = Offset(cx - r * 1.05f, cy + r * 0.25f))
    drawSmilingFace(cx - r * 0.8f, cy + r * 0.2f, r * 0.18f)

    // Smiling Cloud on right
    drawCircle(Color(0xFFE1F5FE), radius = r * 0.32f, center = Offset(cx + r * 0.8f, cy + r * 0.2f))
    drawCircle(Color(0xFFB3E5FC), radius = r * 0.24f, center = Offset(cx + r * 1.05f, cy + r * 0.25f))
}

private fun DrawScope.drawSun() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.32f

    // Warm radiating rays
    for (i in 0 until 12) {
        val angle = Math.toRadians((i * 30).toDouble())
        val startX = cx + (r * 1.05f * kotlin.math.cos(angle)).toFloat()
        val startY = cy + (r * 1.05f * sin(angle)).toFloat()
        val endX = cx + (r * 1.42f * kotlin.math.cos(angle)).toFloat()
        val endY = cy + (r * 1.42f * sin(angle)).toFloat()
        drawLine(Color(0xFFFFB300), Offset(startX, startY), Offset(endX, endY), strokeWidth = 10f, cap = StrokeCap.Round)
    }

    // Core sun
    drawCircle(Color(0xFFFFEB3B), radius = r, center = Offset(cx, cy))
    drawSmilingFace(cx, cy, r * 0.45f)
}

private fun DrawScope.drawTrain() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Engine Body
    drawRoundRect(Color(0xFFE91E63), topLeft = Offset(cx - r * 0.9f, cy - r * 0.2f), size = Size(r * 1.2f, r * 0.8f), cornerRadius = CornerRadius(12f, 12f))
    // Cabin
    drawRoundRect(Color(0xFF00ACC1), topLeft = Offset(cx + r * 0.1f, cy - r * 0.65f), size = Size(r * 0.8f, r * 1.25f), cornerRadius = CornerRadius(12f, 12f))
    // Chimney
    drawRoundRect(Color(0xFFFFB300), topLeft = Offset(cx - r * 0.7f, cy - r * 0.65f), size = Size(r * 0.35f, r * 0.5f), cornerRadius = CornerRadius(6f, 6f))

    // Steam puffs
    drawCircle(Color(0xFFE0E0E0), radius = r * 0.16f, center = Offset(cx - r * 0.7f, cy - r * 0.9f))
    drawCircle(Color(0xFFEEEEEE), radius = r * 0.22f, center = Offset(cx - r * 0.9f, cy - r * 1.15f))

    // Wheels
    drawCircle(Color(0xFF212121), radius = r * 0.25f, center = Offset(cx - r * 0.5f, cy + r * 0.7f))
    drawCircle(Color(0xFFFFD54F), radius = r * 0.1f, center = Offset(cx - r * 0.5f, cy + r * 0.7f))
    drawCircle(Color(0xFF212121), radius = r * 0.3f, center = Offset(cx + r * 0.5f, cy + r * 0.65f))
    drawCircle(Color(0xFFFFD54F), radius = r * 0.12f, center = Offset(cx + r * 0.5f, cy + r * 0.65f))

    // Cabin window
    drawRoundRect(Color.White, topLeft = Offset(cx + r * 0.25f, cy - r * 0.5f), size = Size(r * 0.45f, r * 0.45f), cornerRadius = CornerRadius(8f, 8f))
}

private fun DrawScope.drawUmbrella() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Canopy (Semi circle)
    drawArc(Color(0xFF00897B), 180f, 180f, true, topLeft = Offset(cx - r * 1.1f, cy - r * 0.8f), size = Size(r * 2.2f, r * 1.6f))
    // Alternating bright slice
    drawArc(Color(0xFF4DB6AC), 210f, 60f, true, topLeft = Offset(cx - r * 1.1f, cy - r * 0.8f), size = Size(r * 2.2f, r * 1.6f))

    // Handle (Curving rod)
    drawLine(Color(0xFF5D4037), Offset(cx, cy), Offset(cx, cy + r * 0.9f), strokeWidth = 10f, cap = StrokeCap.Round)
    drawArc(
        Color(0xFF5D4037),
        0f, 180f, false,
        topLeft = Offset(cx - r * 0.25f, cy + r * 0.75f),
        size = Size(r * 0.35f, r * 0.35f),
        style = Stroke(10f, cap = StrokeCap.Round)
    )

    // Raindrops
    drawCircle(Color(0xFF81D4FA), radius = 6f, center = Offset(cx - r * 0.7f, cy + r * 0.4f))
    drawCircle(Color(0xFF81D4FA), radius = 7f, center = Offset(cx + r * 0.8f, cy + r * 0.5f))
}

private fun DrawScope.drawVan() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Body
    drawRoundRect(Color(0xFF3949AB), topLeft = Offset(cx - r * 1.0f, cy - r * 0.4f), size = Size(r * 2.0f, r * 0.95f), cornerRadius = CornerRadius(16f, 16f))
    // Front hood
    drawRoundRect(Color(0xFF5C6BC0), topLeft = Offset(cx + r * 0.4f, cy - r * 0.2f), size = Size(r * 0.6f, r * 0.75f), cornerRadius = CornerRadius(12f, 12f))

    // Windows
    drawRoundRect(Color(0xFFE8EAF6), topLeft = Offset(cx - r * 0.85f, cy - r * 0.3f), size = Size(r * 0.6f, r * 0.35f), cornerRadius = CornerRadius(6f, 6f))
    drawRoundRect(Color(0xFFE8EAF6), topLeft = Offset(cx - r * 0.1f, cy - r * 0.3f), size = Size(r * 0.5f, r * 0.35f), cornerRadius = CornerRadius(6f, 6f))

    // Wheels
    drawCircle(Color(0xFF212121), radius = r * 0.24f, center = Offset(cx - r * 0.55f, cy + r * 0.55f))
    drawCircle(Color(0xFFB0BEC5), radius = r * 0.1f, center = Offset(cx - r * 0.55f, cy + r * 0.55f))
    drawCircle(Color(0xFF212121), radius = r * 0.24f, center = Offset(cx + r * 0.55f, cy + r * 0.55f))
    drawCircle(Color(0xFFB0BEC5), radius = r * 0.1f, center = Offset(cx + r * 0.55f, cy + r * 0.55f))
}

private fun DrawScope.drawWhale() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Whale Body
    drawOval(Color(0xFF0288D1), topLeft = Offset(cx - r * 1.0f, cy - r * 0.5f), size = Size(r * 1.9f, r * 1.1f))

    // Belly
    drawArc(Color(0xFFE1F5FE), 0f, 180f, true, topLeft = Offset(cx - r * 0.75f, cy), size = Size(r * 1.4f, r * 0.6f))

    // Tail
    val tail = Path().apply {
        moveTo(cx - r * 0.9f, cy)
        lineTo(cx - r * 1.4f, cy - r * 0.45f)
        lineTo(cx - r * 1.2f, cy)
        lineTo(cx - r * 1.4f, cy + r * 0.45f)
        close()
    }
    drawPath(tail, Color(0xFF0288D1))

    // Water Spout
    val spout = Path().apply {
        moveTo(cx, cy - r * 0.5f)
        cubicTo(cx - 20f, cy - r * 1.1f, cx - 40f, cy - r * 1.2f, cx - 30f, cy - r * 1.3f)
        moveTo(cx, cy - r * 0.5f)
        cubicTo(cx + 20f, cy - r * 1.1f, cx + 40f, cy - r * 1.2f, cx + 30f, cy - r * 1.3f)
    }
    drawPath(spout, Color(0xFF81D4FA), style = Stroke(6f, cap = StrokeCap.Round))

    // Smiling eye & mouth
    drawCircle(Color(0xFF212121), radius = r * 0.08f, center = Offset(cx + r * 0.45f, cy - r * 0.15f))
    drawSmilingFace(cx + r * 0.3f, cy, r * 0.25f)
}

private fun DrawScope.drawXylophone() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    val colors = listOf(
        Color(0xFFE53935), Color(0xFFFF9800), Color(0xFFFFEB3B),
        Color(0xFF4CAF50), Color(0xFF00ACC1), Color(0xFF7E57C2)
    )

    // Base bars
    for (i in colors.indices) {
        val barHeight = r * (1.5f - i * 0.16f)
        val barX = cx - r * 0.9f + i * (r * 0.32f)
        val barY = cy - barHeight / 2f
        drawRoundRect(
            color = colors[i],
            topLeft = Offset(barX, barY),
            size = Size(r * 0.25f, barHeight),
            cornerRadius = CornerRadius(8f, 8f)
        )
    }

    // Mallet
    drawLine(Color(0xFF795548), Offset(cx - r * 0.6f, cy - r * 0.8f), Offset(cx + r * 0.8f, cy - r * 0.1f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawCircle(Color(0xFFE91E63), radius = r * 0.12f, center = Offset(cx - r * 0.6f, cy - r * 0.8f))
}

private fun DrawScope.drawYoYo() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.36f

    // Yellow String
    val stringPath = Path().apply {
        moveTo(cx, cy - r * 1.4f)
        cubicTo(cx - 15f, cy - r * 0.8f, cx + 15f, cy - r * 0.4f, cx, cy)
    }
    drawPath(stringPath, Color(0xFFFFD54F), style = Stroke(6f, cap = StrokeCap.Round))

    // YoYo disks
    drawCircle(Color(0xFFD84315), radius = r * 0.9f, center = Offset(cx, cy))
    drawCircle(Color(0xFFFF7043), radius = r * 0.7f, center = Offset(cx, cy))
    drawCircle(Color(0xFFFFCCBC), radius = r * 0.25f, center = Offset(cx, cy))

    drawSmilingFace(cx, cy - r * 0.1f, r * 0.35f)
}

private fun DrawScope.drawZebra() {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.35f

    // Ears
    drawCircle(Color(0xFFEEEEEE), radius = r * 0.3f, center = Offset(cx - r * 0.7f, cy - r * 0.7f))
    drawCircle(Color(0xFF212121), radius = r * 0.18f, center = Offset(cx - r * 0.7f, cy - r * 0.7f))
    drawCircle(Color(0xFFEEEEEE), radius = r * 0.3f, center = Offset(cx + r * 0.7f, cy - r * 0.7f))
    drawCircle(Color(0xFF212121), radius = r * 0.18f, center = Offset(cx + r * 0.7f, cy - r * 0.7f))

    // Head (White)
    drawCircle(Color(0xFFFAFAFA), radius = r, center = Offset(cx, cy))

    // Mane
    drawArc(Color(0xFF212121), 220f, 100f, false, topLeft = Offset(cx - r * 0.6f, cy - r * 1.1f), size = Size(r * 1.2f, r * 0.6f), style = Stroke(14f, cap = StrokeCap.Round))

    // Black Stripes
    drawLine(Color(0xFF212121), Offset(cx - r * 0.9f, cy - r * 0.1f), Offset(cx - r * 0.45f, cy - r * 0.05f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawLine(Color(0xFF212121), Offset(cx - r * 0.9f, cy + r * 0.2f), Offset(cx - r * 0.45f, cy + r * 0.25f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawLine(Color(0xFF212121), Offset(cx + r * 0.9f, cy - r * 0.1f), Offset(cx + r * 0.45f, cy - r * 0.05f), strokeWidth = 8f, cap = StrokeCap.Round)
    drawLine(Color(0xFF212121), Offset(cx + r * 0.9f, cy + r * 0.2f), Offset(cx + r * 0.45f, cy + r * 0.25f), strokeWidth = 8f, cap = StrokeCap.Round)

    // Snout
    drawOval(Color(0xFF424242), topLeft = Offset(cx - r * 0.35f, cy + r * 0.35f), size = Size(r * 0.7f, r * 0.5f))

    drawSmilingFace(cx, cy - r * 0.15f, r * 0.4f)
}

/**
 * Universal smiling eyes & mouth helper for friendly cartoon characters.
 */
private fun DrawScope.drawSmilingFace(cx: Float, cy: Float, span: Float) {
    // Eyes
    drawCircle(Color(0xFF212121), radius = span * 0.22f, center = Offset(cx - span * 0.65f, cy))
    drawCircle(Color.White, radius = span * 0.08f, center = Offset(cx - span * 0.72f, cy - span * 0.08f))

    drawCircle(Color(0xFF212121), radius = span * 0.22f, center = Offset(cx + span * 0.65f, cy))
    drawCircle(Color.White, radius = span * 0.08f, center = Offset(cx + span * 0.58f, cy - span * 0.08f))

    // Cheerful rosy cheeks
    drawCircle(Color(0xFFFF8A80).copy(alpha = 0.6f), radius = span * 0.2f, center = Offset(cx - span * 0.85f, cy + span * 0.35f))
    drawCircle(Color(0xFFFF8A80).copy(alpha = 0.6f), radius = span * 0.2f, center = Offset(cx + span * 0.85f, cy + span * 0.35f))

    // Smile arc
    val smilePath = Path().apply {
        moveTo(cx - span * 0.35f, cy + span * 0.35f)
        cubicTo(cx - span * 0.1f, cy + span * 0.65f, cx + span * 0.1f, cy + span * 0.65f, cx + span * 0.35f, cy + span * 0.35f)
    }
    drawPath(smilePath, color = Color(0xFF212121), style = Stroke(width = 6f, cap = StrokeCap.Round))
}
