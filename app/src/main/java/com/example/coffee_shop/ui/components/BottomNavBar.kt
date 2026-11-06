package com.example.coffee_shop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.ui.theme.CardBorder
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.TextMuted

enum class MainTab {
    BERANDA,
    PESANAN,
    PROFIL
}

@Composable
fun BottomNavBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp)
            .background(Color.White)
            .border(width = 1.dp, color = CardBorder.copy(alpha = 0.8f))
            .navigationBarsPadding()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 1: Beranda
            BottomNavItem(
                title = "Beranda",
                isSelected = selectedTab == MainTab.BERANDA,
                icon = { tint -> HomeIcon(tint = tint, size = 22.dp) },
                onClick = { onTabSelected(MainTab.BERANDA) }
            )

            // Tab 2: Pesanan
            BottomNavItem(
                title = "Pesanan",
                isSelected = selectedTab == MainTab.PESANAN,
                icon = { tint -> OrdersIcon(tint = tint, size = 22.dp) },
                onClick = { onTabSelected(MainTab.PESANAN) }
            )

            // Tab 3: Profil
            BottomNavItem(
                title = "Profil",
                isSelected = selectedTab == MainTab.PROFIL,
                icon = { tint -> ProfileIcon(tint = tint, size = 22.dp) },
                onClick = { onTabSelected(MainTab.PROFIL) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    title: String,
    isSelected: Boolean,
    icon: @Composable (Color) -> Unit,
    onClick: () -> Unit
) {
    val activeColor = OrangePrimary
    val inactiveColor = TextMuted

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon(if (isSelected) activeColor else inactiveColor)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontSize = 11.5.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}
