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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.model.CartItem
import com.example.coffee_shop.model.CoffeeItem
import com.example.coffee_shop.ui.theme.CardBorder
import com.example.coffee_shop.ui.theme.OrangeLight
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.StarGold
import com.example.coffee_shop.ui.theme.TextDark
import com.example.coffee_shop.ui.theme.TextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeDetailBottomSheet(
    coffee: CoffeeItem,
    onDismiss: () -> Unit,
    onAddToCart: (CartItem) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedTemp by remember { mutableStateOf(if (coffee.category == "Dingin") "Dingin" else "Panas") }
    var selectedSize by remember { mutableStateOf("Reguler") }
    var selectedSugar by remember { mutableStateOf("Normal") }
    var quantity by remember { mutableIntStateOf(1) }

    val sizeExtra = if (selectedSize.contains("Large", ignoreCase = true)) 5000 else 0
    val singlePrice = coffee.price + sizeExtra
    val totalPrice = singlePrice * quantity
    val formattedTotal = "Rp " + String.format("%,d", totalPrice).replace(',', '.')

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
                .verticalScroll(rememberScrollState())
        ) {
            // Header Image & Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(OrangeLight),
                    contentAlignment = Alignment.Center
                ) {
                    CoffeeCupGraphic(size = 68.dp, showHalo = false)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coffee.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "★", color = StarGold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${coffee.rating} · Rekomendasi Barista",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = OrangePrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = coffee.formattedPrice,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OrangePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = coffee.description,
                fontSize = 13.5.sp,
                color = TextMuted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Temperature Option
            Text(
                text = "Pilihan Suhu",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Panas", "Dingin").forEach { temp ->
                    OptionPill(
                        text = temp,
                        isSelected = selectedTemp == temp,
                        onClick = { selectedTemp = temp },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Size Option
            Text(
                text = "Ukuran Gelas",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Reguler", "Large (+5rb)").forEach { size ->
                    OptionPill(
                        text = size,
                        isSelected = selectedSize == size,
                        onClick = { selectedSize = size },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sugar Level Option
            Text(
                text = "Tingkat Kemanisan",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Normal", "Less Sugar", "Tanpa Gula").forEach { sugar ->
                    OptionPill(
                        text = sugar,
                        isSelected = selectedSugar == sugar,
                        onClick = { selectedSugar = sugar },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quantity & Add to Cart Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Stepper
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(OrangeLight)
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { if (quantity > 1) quantity-- },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "−", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OrangePrimary)
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(OrangePrimary)
                            .clickable { quantity++ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        val cartItem = CartItem(
                            coffee = coffee,
                            quantity = quantity,
                            size = selectedSize,
                            temperature = selectedTemp,
                            sugarLevel = selectedSugar
                        )
                        onAddToCart(cartItem)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Tambah · $formattedTotal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun OptionPill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) OrangePrimary else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) OrangePrimary else CardBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else TextDark
        )
    }
}
