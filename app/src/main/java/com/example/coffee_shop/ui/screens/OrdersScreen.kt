package com.example.coffee_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.model.CoffeeOrder
import com.example.coffee_shop.ui.components.CoffeeCupGraphic
import com.example.coffee_shop.ui.theme.CardBorder
import com.example.coffee_shop.ui.theme.OrangeLight
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.TextDark
import com.example.coffee_shop.ui.theme.TextMuted

@Composable
fun OrdersScreen(
    orders: List<CoffeeOrder>,
    onCompleteOrder: (CoffeeOrder) -> Unit,
    onReorder: (CoffeeOrder) -> Unit,
    onExploreMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFBF8F5))
            .statusBarsPadding()
    ) {
        // Top Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Daftar Pesanan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CoffeeCupGraphic(size = 110.dp, showHalo = false)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Belum Ada Pesanan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pesanan kopi yang Anda beli akan muncul di sini",
                        fontSize = 13.5.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Button(
                        onClick = onExploreMenu,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OrangePrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(text = "Pesan Sekarang", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderCard(
                        order = order,
                        onComplete = { onCompleteOrder(order) },
                        onReorder = { onReorder(order) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: CoffeeOrder,
    onComplete: () -> Unit,
    onReorder: () -> Unit
) {
    val isActive = order.status.contains("Dibuat")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.5.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row: Order ID & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pesanan #${order.id}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = order.orderDate,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isActive) OrangeLight else Color(0xFFE8F5E9))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = order.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) OrangePrimary else Color(0xFF2E7D32)
                    )
                }
            }

            HorizontalDivider(
                color = CardBorder.copy(alpha = 0.7f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Items List
            order.items.forEach { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(OrangeLight),
                        contentAlignment = Alignment.Center
                    ) {
                        CoffeeCupGraphic(size = 26.dp, showHalo = false)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${cartItem.quantity}x ${cartItem.coffee.name}",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
                        Text(
                            text = "${cartItem.temperature} · ${cartItem.size}",
                            fontSize = 11.5.sp,
                            color = TextMuted
                        )
                    }

                    Text(
                        text = cartItem.formattedTotalPrice,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary
                    )
                }
            }

            HorizontalDivider(
                color = CardBorder.copy(alpha = 0.7f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Bottom Summary & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Pembayaran",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                    Text(
                        text = order.formattedTotal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OrangePrimary
                    )
                }

                if (isActive) {
                    Button(
                        onClick = onComplete,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OrangePrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Pesanan Diterima ✓",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(OrangeLight)
                            .clickable(onClick = onReorder)
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Pesan Lagi",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary
                        )
                    }
                }
            }
        }
    }
}
