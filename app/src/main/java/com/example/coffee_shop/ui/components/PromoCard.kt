package com.example.coffee_shop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.ui.theme.OrangeGradientEnd
import com.example.coffee_shop.ui.theme.OrangeGradientStart

@Composable
fun PromoCard(
    onPromoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        OrangeGradientStart,
                        OrangeGradientEnd,
                        Color(0xFFFFAD60)
                    )
                )
            )
            .clickable(onClick = onPromoClick)
            .padding(vertical = 18.dp, horizontal = 20.dp)
    ) {
        // Decorative background circles on the right side
        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 40.dp, y = (-10).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
        )

        Box(
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 10.dp, y = 25.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f))
        )

        // Text Content
        Column(
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Text(
                text = "Promo Hari Ini",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Diskon 20% Caffe Latte",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Gunakan kode: NGOPI20",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.88f)
            )
        }
    }
}
