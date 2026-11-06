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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.model.CartItem
import com.example.coffee_shop.model.CoffeeOrder
import com.example.coffee_shop.ui.theme.CardBorder
import com.example.coffee_shop.ui.theme.OrangeLight
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.SuccessGreen
import com.example.coffee_shop.ui.theme.TextDark
import com.example.coffee_shop.ui.theme.TextMuted
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
    cartItems: List<CartItem>,
    onDismiss: () -> Unit,
    onQuantityChange: (CartItem, Int) -> Unit,
    onRemoveItem: (CartItem) -> Unit,
    onCheckout: (CoffeeOrder) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var promoCodeInput by remember { mutableStateOf("NGOPI20") }
    var appliedPromo by remember { mutableStateOf<String?>("NGOPI20") }
    var promoMessage by remember { mutableStateOf("Diskon 20% aktif!") }

    val subtotal = cartItems.sumOf { it.totalPrice }
    val discount = if (appliedPromo.equals("NGOPI20", ignoreCase = true)) {
        (subtotal * 0.2).toInt()
    } else {
        0
    }
    val serviceFee = if (cartItems.isNotEmpty()) 2000 else 0
    val totalPayment = (subtotal - discount + serviceFee).coerceAtLeast(0)

    val formattedSubtotal = "Rp " + String.format(Locale.GERMANY, "%,d", subtotal)
    val formattedDiscount = "-Rp " + String.format(Locale.GERMANY, "%,d", discount)
    val formattedServiceFee = "Rp " + String.format(Locale.GERMANY, "%,d", serviceFee)
    val formattedTotal = "Rp " + String.format(Locale.GERMANY, "%,d", totalPayment)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Keranjang Belanja (${cartItems.size})",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(OrangeLight)
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center
                ) {
                    CloseIcon(tint = OrangePrimary, size = 16.dp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CoffeeCupGraphic(size = 90.dp, showHalo = false)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Keranjang Anda Masih Kosong",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Yuk pilih kopi favoritmu hari ini!",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                ) {
                    items(cartItems, key = { it.id }) { item ->
                        CartItemRow(
                            item = item,
                            onQuantityChange = { newQty -> onQuantityChange(item, newQty) },
                            onRemove = { onRemoveItem(item) }
                        )
                        HorizontalDivider(
                            color = CardBorder.copy(alpha = 0.6f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Promo code input row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFBF8F4))
                        .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = promoCodeInput,
                        onValueChange = { promoCodeInput = it.uppercase() },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        ),
                        cursorBrush = SolidColor(OrangePrimary),
                        modifier = Modifier.weight(1f),
                        decorationBox = { innerTextField ->
                            if (promoCodeInput.isEmpty()) {
                                Text(
                                    text = "Masukkan kode promo...",
                                    fontSize = 13.5.sp,
                                    color = TextMuted
                                )
                            }
                            innerTextField()
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(OrangePrimary)
                            .clickable {
                                if (promoCodeInput.equals("NGOPI20", ignoreCase = true)) {
                                    appliedPromo = "NGOPI20"
                                    promoMessage = "Diskon 20% aktif!"
                                } else {
                                    appliedPromo = null
                                    promoMessage = "Kode promo tidak valid"
                                }
                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Terapkan",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                if (appliedPromo != null) {
                    Text(
                        text = "✓ $promoMessage",
                        fontSize = 12.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp, start = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Price Summary
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF9F5))
                        .padding(14.dp)
                ) {
                    SummaryRow(label = "Subtotal", value = formattedSubtotal)
                    if (discount > 0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        SummaryRow(
                            label = "Diskon Promo ($appliedPromo)",
                            value = formattedDiscount,
                            valueColor = OrangePrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    SummaryRow(label = "Biaya Layanan", value = formattedServiceFee)
                    HorizontalDivider(
                        color = CardBorder,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    SummaryRow(
                        label = "Total Pembayaran",
                        value = formattedTotal,
                        isBold = true,
                        valueColor = OrangePrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Checkout Button
                Button(
                    onClick = {
                        val orderId = "SKM-" + (1000..9999).random()
                        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
                        val order = CoffeeOrder(
                            id = orderId,
                            items = cartItems.toList(),
                            totalAmount = totalPayment,
                            status = "Sedang Dibuat ☕",
                            orderDate = dateFormat.format(Date()),
                            promoCode = appliedPromo,
                            discountAmount = discount
                        )
                        onCheckout(order)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Pesan Sekarang · $formattedTotal",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(OrangeLight),
            contentAlignment = Alignment.Center
        ) {
            CoffeeCupGraphic(size = 40.dp, showHalo = false)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.coffee.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${item.temperature} · ${item.size} · ${item.sugarLevel}",
                fontSize = 12.sp,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.formattedTotalPrice,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = OrangePrimary
            )
        }

        // Stepper
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(OrangeLight)
                .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        if (item.quantity > 1) {
                            onQuantityChange(item.quantity - 1)
                        } else {
                            onRemove()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (item.quantity > 1) "−" else "✕",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary
                )
            }

            Text(
                text = "${item.quantity}",
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(OrangePrimary)
                    .clickable { onQuantityChange(item.quantity + 1) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    valueColor: Color = TextDark
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isBold) 14.5.sp else 13.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) TextDark else TextMuted
        )
        Text(
            text = value,
            fontSize = if (isBold) 16.sp else 13.5.sp,
            fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.SemiBold,
            color = valueColor
        )
    }
}
