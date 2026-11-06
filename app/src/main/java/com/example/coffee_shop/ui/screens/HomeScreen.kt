package com.example.coffee_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.model.CartItem
import com.example.coffee_shop.model.CoffeeItem
import com.example.coffee_shop.model.CoffeeOrder
import com.example.coffee_shop.model.SampleData
import com.example.coffee_shop.ui.components.CartBottomSheet
import com.example.coffee_shop.ui.components.CategoryChips
import com.example.coffee_shop.ui.components.CoffeeCupGraphic
import com.example.coffee_shop.ui.components.CoffeeDetailBottomSheet
import com.example.coffee_shop.ui.components.CoffeeItemCard
import com.example.coffee_shop.ui.components.PromoCard
import com.example.coffee_shop.ui.components.TopHeader
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.TextDark
import com.example.coffee_shop.ui.theme.TextMuted
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    cartItems: List<CartItem>,
    onAddToCart: (CartItem) -> Unit,
    onQuantityChange: (CartItem, Int) -> Unit,
    onRemoveCartItem: (CartItem) -> Unit,
    onCheckout: (CoffeeOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }

    val categories = remember { listOf("Semua", "Espresso", "Latte", "Dingin") }

    // Bottom Sheet states
    var selectedCoffeeForDetail by remember { mutableStateOf<CoffeeItem?>(null) }
    var showCartSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Filter items based on Category & Search Query
    val filteredMenuItems = remember(searchQuery, selectedCategory) {
        SampleData.menuItems.filter { item ->
            val matchesCategory = if (selectedCategory == "Semua") {
                true
            } else {
                item.category.equals(selectedCategory, ignoreCase = true)
            }

            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                item.name.contains(searchQuery, ignoreCase = true) ||
                        item.description.contains(searchQuery, ignoreCase = true)
            }

            matchesCategory && matchesSearch
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFFFFFDFB))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Header (Orange with greeting, cart badge, and search bar)
            TopHeader(
                cartCount = cartItems.sumOf { it.quantity },
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onCartClick = { showCartSheet = true }
            )

            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // 1. Promo Card
                item {
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        PromoCard(
                            onPromoClick = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Kode promo NGOPI20 disalin!")
                                }
                            }
                        )
                    }
                }

                // 2. Category Chips Row
                item {
                    Spacer(modifier = Modifier.height(18.dp))
                    CategoryChips(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }

                // 3. Section Title "Menu Populer"
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Menu Populer",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Text(
                            text = "Lihat semua",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OrangePrimary,
                            modifier = Modifier.clickable {
                                selectedCategory = "Semua"
                                searchQuery = ""
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 4. Coffee Items List
                if (filteredMenuItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp, horizontal = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CoffeeCupGraphic(size = 80.dp, showHalo = false)
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Menu tidak ditemukan",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Coba cari dengan kata kunci lain",
                                    fontSize = 13.sp,
                                    color = TextMuted
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        selectedCategory = "Semua"
                                        searchQuery = ""
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = OrangePrimary,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(text = "Reset Filter", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    items(filteredMenuItems, key = { it.id }) { coffee ->
                        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                            CoffeeItemCard(
                                coffee = coffee,
                                onClick = { selectedCoffeeForDetail = coffee }
                            )
                        }
                    }
                }
            }
        }

        // Snackbar Host for feedback notifications
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }

    // Coffee Detail / Customization Bottom Sheet
    selectedCoffeeForDetail?.let { coffee ->
        CoffeeDetailBottomSheet(
            coffee = coffee,
            onDismiss = { selectedCoffeeForDetail = null },
            onAddToCart = { cartItem ->
                onAddToCart(cartItem)
                selectedCoffeeForDetail = null
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("${cartItem.coffee.name} ditambahkan ke keranjang!")
                }
            }
        )
    }

    // Cart Bottom Sheet
    if (showCartSheet) {
        CartBottomSheet(
            cartItems = cartItems,
            onDismiss = { showCartSheet = false },
            onQuantityChange = onQuantityChange,
            onRemoveItem = onRemoveCartItem,
            onCheckout = { order ->
                showCartSheet = false
                onCheckout(order)
            }
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    com.example.coffee_shop.ui.theme.Coffee_shopTheme {
        HomeScreen(
            cartItems = SampleData.initialCart(),
            onAddToCart = {},
            onQuantityChange = { _, _ -> },
            onRemoveCartItem = {},
            onCheckout = {}
        )
    }
}

