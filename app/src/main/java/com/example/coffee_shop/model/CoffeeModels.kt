package com.example.coffee_shop.model

import java.util.UUID

data class CoffeeItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val category: String,
    val isPopular: Boolean = true
) {
    val formattedPrice: String
        get() = "Rp " + String.format("%,d", price).replace(',', '.')
}

data class CartItem(
    val id: String = UUID.randomUUID().toString(),
    val coffee: CoffeeItem,
    val quantity: Int = 1,
    val size: String = "Reguler",
    val temperature: String = "Panas",
    val sugarLevel: String = "Normal"
) {
    val singlePrice: Int
        get() = coffee.price + if (size.contains("Large", ignoreCase = true)) 5000 else 0

    val totalPrice: Int
        get() = singlePrice * quantity

    val formattedTotalPrice: String
        get() = "Rp " + String.format("%,d", totalPrice).replace(',', '.')
}

data class CoffeeOrder(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Int,
    val status: String,
    val orderDate: String,
    val promoCode: String? = null,
    val discountAmount: Int = 0
) {
    val formattedTotal: String
        get() = "Rp " + String.format("%,d", totalAmount).replace(',', '.')
}

object SampleData {
    val menuItems = listOf(
        CoffeeItem(
            id = "c1",
            name = "Caffe Latte",
            description = "Espresso + susu steamed lembut",
            price = 32000,
            rating = 4.8,
            category = "Latte"
        ),
        CoffeeItem(
            id = "c2",
            name = "Cappuccino",
            description = "Espresso, susu, dan foam tebal",
            price = 30000,
            rating = 4.7,
            category = "Espresso"
        ),
        CoffeeItem(
            id = "c3",
            name = "Americano",
            description = "Espresso dengan air panas",
            price = 25000,
            rating = 4.6,
            category = "Espresso"
        ),
        CoffeeItem(
            id = "c4",
            name = "Iced Latte",
            description = "Espresso dingin dengan susu segar lembut",
            price = 33000,
            rating = 4.8,
            category = "Dingin"
        ),
        CoffeeItem(
            id = "c5",
            name = "Caramel Macchiato",
            description = "Espresso dengan sirup vanila dan karamel lezat",
            price = 35000,
            rating = 4.9,
            category = "Latte"
        ),
        CoffeeItem(
            id = "c6",
            name = "Cold Brew",
            description = "Seduhan kopi dingin segar khas Sukma 12 jam",
            price = 28000,
            rating = 4.7,
            category = "Dingin"
        ),
        CoffeeItem(
            id = "c7",
            name = "Espresso Solo",
            description = "Ekstraksi kopi murni konsentrasi tinggi",
            price = 20000,
            rating = 4.5,
            category = "Espresso"
        ),
        CoffeeItem(
            id = "c8",
            name = "Kopi Gula Aren",
            description = "Kopi susu espresso dengan gula aren nusantara",
            price = 27000,
            rating = 4.9,
            category = "Dingin"
        )
    )

    fun initialCart(): List<CartItem> = listOf(
        CartItem(
            coffee = menuItems[0], // Caffe Latte
            quantity = 1,
            size = "Reguler",
            temperature = "Panas",
            sugarLevel = "Normal"
        ),
        CartItem(
            coffee = menuItems[1], // Cappuccino
            quantity = 1,
            size = "Reguler",
            temperature = "Panas",
            sugarLevel = "Normal"
        )
    )
}
