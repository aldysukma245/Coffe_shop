package com.example.coffee_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.ui.components.CoffeeCupGraphic
import com.example.coffee_shop.ui.theme.OrangePrimary

@Composable
fun WelcomeScreen(
    onStartOrder: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OrangePrimary)
    ) {
        // Decorative background translucent circles (matching screenshot)
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (-80).dp, y = (-50).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
        )

        Box(
            modifier = Modifier
                .size(340.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 100.dp, y = 100.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Center Coffee Cup Graphic
            CoffeeCupGraphic(
                size = 210.dp,
                haloColor = Color.White.copy(alpha = 0.22f)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Title
            Text(
                text = "Sukma Coffee",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            Text(
                text = "Kopi enak, hari jadi semangat",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.92f)
            )

            Spacer(modifier = Modifier.weight(1.2f))

            // "Mulai Pesan" Button (White pill with orange text)
            Button(
                onClick = onStartOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = OrangePrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Mulai Pesan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    com.example.coffee_shop.ui.theme.Coffee_shopTheme {
        WelcomeScreen(onStartOrder = {})
    }
}

