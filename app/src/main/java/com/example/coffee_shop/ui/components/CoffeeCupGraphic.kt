package com.example.coffee_shop.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoffeeCupGraphic(
    modifier: Modifier = Modifier,
    size: Dp = 160.dp,
    showHalo: Boolean = true,
    haloColor: Color = Color.White.copy(alpha = 0.22f)
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer halo ring (as seen in welcome screen)
        if (showHalo) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(haloColor)
            )
        }

        // The Coffee Cup Canvas
        Canvas(
            modifier = Modifier.size(size * 0.74f)
        ) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val outerRadius = this.size.minDimension / 2f

            // 1. Cup Lip / Outer Rim (Pure White Ceramic)
            drawCircle(
                color = Color.White,
                radius = outerRadius,
                center = center
            )

            // 2. Cup Inner Wall (Dark warm brown ceramic)
            val innerWallRadius = outerRadius * 0.88f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF5D3B2A),
                        Color(0xFF6B4431),
                        Color(0xFF4A2D1F)
                    ),
                    center = center,
                    radius = innerWallRadius
                ),
                radius = innerWallRadius,
                center = center
            )

            // 3. Coffee Liquid (Deep Espresso)
            val coffeeRadius = outerRadius * 0.76f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3B2418),
                        Color(0xFF26150C),
                        Color(0xFF190C06)
                    ),
                    center = center,
                    radius = coffeeRadius
                ),
                radius = coffeeRadius,
                center = center
            )

            // 4. Crema / Foam subtle highlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF8B5E40).copy(alpha = 0.35f),
                        Color.Transparent
                    ),
                    center = Offset(center.x - coffeeRadius * 0.2f, center.y - coffeeRadius * 0.2f),
                    radius = coffeeRadius * 0.7f
                ),
                radius = coffeeRadius * 0.65f,
                center = Offset(center.x - coffeeRadius * 0.2f, center.y - coffeeRadius * 0.2f)
            )

            // 5. Cup inner rim reflection stroke
            drawCircle(
                color = Color.White.copy(alpha = 0.4f),
                radius = innerWallRadius,
                center = center,
                style = Stroke(width = outerRadius * 0.025f)
            )
        }
    }
}
