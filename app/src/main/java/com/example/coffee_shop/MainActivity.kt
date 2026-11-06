package com.example.coffee_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coffee_shop.model.CartItem
import com.example.coffee_shop.model.CoffeeOrder
import com.example.coffee_shop.model.SampleData
import com.example.coffee_shop.ui.components.BottomNavBar
import com.example.coffee_shop.ui.components.MainTab
import com.example.coffee_shop.ui.screens.HomeScreen
import com.example.coffee_shop.ui.screens.OrdersScreen
import com.example.coffee_shop.ui.screens.ProfileScreen
import com.example.coffee_shop.ui.screens.WelcomeScreen
import com.example.coffee_shop.ui.theme.Coffee_shopTheme
import com.example.coffee_shop.ui.theme.OrangePrimary
import com.example.coffee_shop.ui.theme.TextDark
import com.example.coffee_shop.ui.theme.TextMuted

enum class AppScreen {
    WELCOME,
    MAIN
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Coffee_shopTheme {
                CoffeeApp()
            }
        }
    }
}

@Composable
fun CoffeeApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.WELCOME) }
    var selectedTab by remember { mutableStateOf(MainTab.BERANDA) }

    // Cart state preloaded with 2 items to match the "Keranjang · 2" in screenshot
    val cartItems = remember { mutableStateListOf<CartItem>().apply { addAll(SampleData.initialCart()) } }

    // Orders state
    val orders = remember { mutableStateListOf<CoffeeOrder>() }

    // Success Order Dialog state
    var completedOrderDialog by remember { mutableStateOf<CoffeeOrder?>(null) }

    // Handle back press
    BackHandler(enabled = currentScreen == AppScreen.MAIN) {
        if (selectedTab != MainTab.BERANDA) {
            selectedTab = MainTab.BERANDA
        } else {
            currentScreen = AppScreen.WELCOME
        }
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            if (targetState == AppScreen.MAIN) {
                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> -width } + fadeOut()
                )
            } else {
                (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> width } + fadeOut()
                )
            }
        },
        label = "ScreenTransition"
    ) { screen ->
        when (screen) {
            AppScreen.WELCOME -> {
                WelcomeScreen(
                    onStartOrder = {
                        currentScreen = AppScreen.MAIN
                        selectedTab = MainTab.BERANDA
                    }
                )
            }

            AppScreen.MAIN -> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFFFFDFB),
                    bottomBar = {
                        BottomNavBar(
                            selectedTab = selectedTab,
                            onTabSelected = { tab -> selectedTab = tab }
                        )
                    }
                ) { innerPadding ->
                    when (selectedTab) {
                        MainTab.BERANDA -> {
                            HomeScreen(
                                cartItems = cartItems,
                                onAddToCart = { newItem ->
                                    // If same item with same specs exists, increase quantity
                                    val existingIndex = cartItems.indexOfFirst {
                                        it.coffee.id == newItem.coffee.id &&
                                                it.size == newItem.size &&
                                                it.temperature == newItem.temperature &&
                                                it.sugarLevel == newItem.sugarLevel
                                    }
                                    if (existingIndex >= 0) {
                                        val existing = cartItems[existingIndex]
                                        cartItems[existingIndex] = existing.copy(quantity = existing.quantity + newItem.quantity)
                                    } else {
                                        cartItems.add(newItem)
                                    }
                                },
                                onQuantityChange = { item, newQty ->
                                    val index = cartItems.indexOfFirst { it.id == item.id }
                                    if (index >= 0) {
                                        cartItems[index] = cartItems[index].copy(quantity = newQty)
                                    }
                                },
                                onRemoveCartItem = { item ->
                                    cartItems.removeAll { it.id == item.id }
                                },
                                onCheckout = { newOrder ->
                                    orders.add(0, newOrder)
                                    cartItems.clear()
                                    completedOrderDialog = newOrder
                                    selectedTab = MainTab.PESANAN
                                },
                                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                            )
                        }

                        MainTab.PESANAN -> {
                            OrdersScreen(
                                orders = orders,
                                onCompleteOrder = { orderToComplete ->
                                    val index = orders.indexOfFirst { it.id == orderToComplete.id }
                                    if (index >= 0) {
                                        orders[index] = orders[index].copy(status = "Selesai ✓")
                                    }
                                },
                                onReorder = { reorderOrder ->
                                    cartItems.addAll(reorderOrder.items.map { it.copy() })
                                    selectedTab = MainTab.BERANDA
                                },
                                onExploreMenu = {
                                    selectedTab = MainTab.BERANDA
                                },
                                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                            )
                        }

                        MainTab.PROFIL -> {
                            ProfileScreen(
                                onBackToWelcome = {
                                    currentScreen = AppScreen.WELCOME
                                },
                                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                            )
                        }
                    }
                }
            }
        }
    }

    // Success dialog after checkout
    completedOrderDialog?.let { order ->
        AlertDialog(
            onDismissRequest = { completedOrderDialog = null },
            confirmButton = {
                Button(
                    onClick = { completedOrderDialog = null },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Lihat Pesanan", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(
                    text = "Pesanan Berhasil Dibuat! ☕",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark
                )
            },
            text = {
                Text(
                    text = "Pesanan #${order.id} sebesar ${order.formattedTotal} telah diterima. Barista Sukma Coffee sedang menyiapkan pesanan Anda.",
                    fontSize = 14.sp,
                    color = TextMuted,
                    lineHeight = 20.sp
                )
            },
            containerColor = Color.White
        )
    }
}
