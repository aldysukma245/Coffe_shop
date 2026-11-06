package com.example.coffee_shop.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    size: Dp = 20.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.width * 0.11f
        val radius = this.size.width * 0.32f
        val center = Offset(this.size.width * 0.42f, this.size.height * 0.42f)

        drawCircle(
            color = tint,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        val startHandle = Offset(center.x + radius * 0.7f, center.y + radius * 0.7f)
        val endHandle = Offset(this.size.width * 0.9f, this.size.height * 0.9f)
        drawLine(
            color = tint,
            start = startHandle,
            end = endHandle,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    size: Dp = 18.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = this.size.width * 0.13f
        val p = this.size.width * 0.22f
        drawLine(
            color = tint,
            start = Offset(p, p),
            end = Offset(this.size.width - p, this.size.height - p),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(this.size.width - p, p),
            end = Offset(p, this.size.height - p),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun HomeIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    size: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val roofPath = Path().apply {
            moveTo(w * 0.15f, h * 0.48f)
            lineTo(w * 0.5f, h * 0.18f)
            lineTo(w * 0.85f, h * 0.48f)
        }
        drawPath(
            path = roofPath,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        val wallsPath = Path().apply {
            moveTo(w * 0.24f, h * 0.46f)
            lineTo(w * 0.24f, h * 0.84f)
            lineTo(w * 0.76f, h * 0.84f)
            lineTo(w * 0.76f, h * 0.46f)
        }
        drawPath(
            path = wallsPath,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun OrdersIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    size: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // 3 horizontal order/menu lines
        drawLine(
            color = tint,
            start = Offset(w * 0.2f, h * 0.32f),
            end = Offset(w * 0.8f, h * 0.32f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.2f, h * 0.5f),
            end = Offset(w * 0.8f, h * 0.5f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.2f, h * 0.68f),
            end = Offset(w * 0.6f, h * 0.68f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    size: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Head
        drawCircle(
            color = tint,
            radius = w * 0.22f,
            center = Offset(w * 0.5f, h * 0.32f),
            style = Stroke(width = strokeWidth)
        )

        // Shoulders arc
        val shoulderPath = Path().apply {
            moveTo(w * 0.18f, h * 0.84f)
            cubicTo(
                w * 0.22f, h * 0.62f,
                w * 0.78f, h * 0.62f,
                w * 0.82f, h * 0.84f
            )
        }
        drawPath(
            path = shoulderPath,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun StarIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFFFFA000),
    size: Dp = 16.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.05f)
            lineTo(w * 0.64f, h * 0.34f)
            lineTo(w * 0.95f, h * 0.38f)
            lineTo(w * 0.72f, h * 0.60f)
            lineTo(w * 0.78f, h * 0.92f)
            lineTo(w * 0.5f, h * 0.76f)
            lineTo(w * 0.22f, h * 0.92f)
            lineTo(w * 0.28f, h * 0.60f)
            lineTo(w * 0.05f, h * 0.38f)
            lineTo(w * 0.36f, h * 0.34f)
            close()
        }
        drawPath(path = path, color = tint, style = Fill)
    }
}
