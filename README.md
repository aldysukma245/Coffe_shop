# ☕ Sukma Coffee - Android App

Aplikasi pemesanan kopi modern dan interaktif yang dibangun menggunakan **Jetpack Compose** dan **Material 3**. Aplikasi ini mengusung desain *clean, warm, & vibrant coffee aesthetic* dengan alur pemesanan mulai dari halaman sambutan (*onboarding*), pencarian menu, kustomisasi racikan kopi, keranjang belanja dengan kupon promo, hingga pelacakan status pesanan.

---

## 📸 Tampilan Aplikasi

| Halaman Sambutan (*Welcome Screen*) | Halaman Beranda (*Home Screen*) |
| :---: | :---: |
| <img src="screenshots/welcome_screen.png" width="280" alt="Welcome Screen"/> | <img src="screenshots/home_screen.png" width="280" alt="Home Screen"/> |

---

## ✨ Fitur Utama

### 1. 🌟 Halaman Sambutan (*Welcome Screen*)
* **Branding Minimalis & Elegan**: Latar belakang oranye hangat (*vibrant warm orange*) dengan aksen lingkaran transparan.
* **Ilustrasi Cangkir Kopi Khusus**: Menggunakan Jetpack Compose `Canvas` untuk merender tampilan atas (*top-down view*) cangkir kopi secara presisi (keramik putih, bibir cangkir espresso, dan lapisan crema).
* **Navigasi Cepat**: Tombol *pill* putih **"Mulai Pesan"** yang bertransisi mulus ke halaman utama beranda.

### 2. 🏠 Halaman Beranda (*Home Screen*)
* **Header Oranye Melengkung**:
  * Sapaan ramah: *"Sukma Coffee · Selamat sore"* & *"Mau ngopi apa hari ini?"*.
  * **Badge Keranjang Dinamis**: Menampilkan jumlah item keranjang secara *real-time* (`Keranjang · X`) dan langsung membuka lembar belanja saat ditekan.
* **Pencarian Menu Real-time**: Input pencarian interaktif *"Cari kopi favoritmu..."* yang langsung memfilter daftar menu saat diketik, dilengkapi tombol *clear*.
* **Kartu Promo Interaktif**: Banner gradien promo *"Diskon 20% Caffe Latte"* dengan kode `NGOPI20`. Mengetuk kartu otomatis menyalin kode promo dengan notifikasi Snackbar.
* **Kategori Filter (*Chips*)**: Tombol filter kategori (**Semua**, **Espresso**, **Latte**, **Dingin**) dengan animasi pergantian status aktif/non-aktif.
* **Menu Populer**: Daftar kartu kopi terfavorit lengkap dengan foto racikan, deskripsi bahan, harga tebal oranye, dan rating bintang emas:
  * **Caffe Latte** (Rp 32.000, ★ 4.8)
  * **Cappuccino** (Rp 30.000, ★ 4.7)
  * **Americano** (Rp 25.000, ★ 4.6)
  * Menu lainnya: *Iced Latte*, *Caramel Macchiato*, *Cold Brew*, dan *Kopi Gula Aren*.

### 3. 🛠️ Detail & Kustomisasi Minuman (*Bottom Sheet*)
Mengetuk menu kopi mana pun akan membuka lembar kustomisasi:
* **Pilihan Suhu**: Panas (*Hot*) atau Dingin (*Iced*).
* **Ukuran Gelas**: Reguler atau Large (+Rp 5.000).
* **Tingkat Kemanisan**: Normal (100%), Less Sugar (50%), atau Tanpa Gula (0%).
* **Pengatur Jumlah (*Stepper*)**: Tombol `−` dan `+` dengan penghitungan total harga instan.
* **Tambah ke Keranjang**: Otomatis memperbarui item keranjang dan menampilkan umpan balik Snackbar.

### 4. 🛒 Keranjang Belanja & Checkout (*Cart Sheet*)
* Daftar rincian kopi yang telah dipesan beserta spesifikasi kustomisasinya.
* Ubah kuantitas atau hapus item langsung dari keranjang.
* **Fitur Kupon Diskon**: Masukkan kode promo `NGOPI20` untuk mendapatkan potongan diskon 20%.
* **Rincian Pembayaran Transparan**: Subtotal, Potongan Promo, Biaya Layanan, dan Total Bayar.
* **Checkout Instan**: Menghasilkan ID pesanan unik (`#SKM-XXXX`) dan otomatis memindahkan pesanan ke tab pelacakan.

### 5. 📋 Riwayat & Pelacakan Pesanan (*Orders Screen*)
* Menampilkan pesanan aktif dengan status **"Sedang Dibuat ☕"**.
* Konfirmasi pesanan dengan tombol **"Pesanan Diterima ✓"** yang mengubah status menjadi **"Selesai ✓"**.
* Tombol **"Pesan Lagi"** untuk memasukkan kembali pesanan favorit ke keranjang belanja.

### 6. 👤 Profil Pengguna (*Profile Screen*)
* Kartu keanggotaan eksklusif **Member Gold ☕** (*Sukma Pratama*) dengan saldo **1.250 Poin Kopi**.
* Ringkasan cepat statistik Pesanan, Voucher, dan Saldo SukmaPay.
* Tombol navigasi untuk kembali ke Halaman Awal (*Welcome Screen*).

---

## 🛠️ Teknologi & Arsitektur (*Tech Stack*)

* **Bahasa Pemrograman**: [Kotlin](https://kotlinlang.org/) `2.2.10`
* **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
* **Compose BOM**: `2026.02.01`
* **Arsitektur**: Clean Architecture / UI State-Driven Component
* **Grafis & Ikon**: Pure Compose Canvas & Vector Drawing (ringan, tajam di semua resolusi layar, tanpa aset gambar berat)
* **Android Gradle Plugin (AGP)**: `9.5.1`
* **Spesifikasi Android**:
  * `minSdk`: `24` (Android 7.0 Nougat+)
  * `targetSdk` / `compileSdk`: `37` (Android 15+)
  * `Java Version`: `11`

---

## 📂 Struktur Direktori Proyek

```text
app/src/main/java/com/example/coffee_shop/
├── MainActivity.kt                      # Navigasi utama, Scaffold, dan State Holder
├── model/
│   └── CoffeeModels.kt                  # Data class CoffeeItem, CartItem, CoffeeOrder, & SampleData
├── ui/
│   ├── components/
│   │   ├── AppIcons.kt                  # Ikon custom Canvas (Search, Close, Home, Orders, Profile, Star)
│   │   ├── BottomNavBar.kt              # Bottom Navigation Bar (Beranda, Pesanan, Profil)
│   │   ├── CartBottomSheet.kt          # Lembar keranjang belanja & kupon promo NGOPI20
│   │   ├── CategoryChips.kt             # Chip filter kategori kopi
│   │   ├── CoffeeCupGraphic.kt          # Ilustrasi top-down cup kopi berbasis Canvas
│   │   ├── CoffeeDetailBottomSheet.kt   # Kustomisasi suhu, ukuran, gula, & kuantitas
│   │   ├── CoffeeItemCard.kt            # Kartu item menu kopi
│   │   ├── PromoCard.kt                 # Banner kartu promo diskon
│   │   └── TopHeader.kt                 # Header atas bergelombang & kolom pencarian
│   ├── screens/
│   │   ├── HomeScreen.kt                # Halaman utama katalog menu & interaksi
│   │   ├── OrdersScreen.kt              # Halaman daftar dan pelacakan pesanan
│   │   ├── ProfileScreen.kt             # Halaman profil member & poin
│   │   └── WelcomeScreen.kt             # Halaman pembuka / splash screen onboarding
│   └── theme/
│       ├── Color.kt                     # Palet warna kopi & oranye (OrangePrimary, CoffeeDark, dll.)
│       ├── Theme.kt                     # Konfigurasi MaterialTheme Coffee
│       └── Type.kt                      # Konfigurasi tipografi
```

---

## 🚀 Cara Menjalankan Proyek (*Getting Started*)

### Prasyarat
1. **Android Studio** versi Ladybug / Meerkat atau yang lebih baru.
2. **JDK 11** atau lebih tinggi.
3. Perangkat Android fisik atau Emulator dengan Android OS 7.0 (API 24) ke atas.

### Langkah Instalasi
1. *Clone* repositori ini:
   ```bash
   git clone https://github.com/username/coffee_shop.git
   ```
2. Buka proyek di **Android Studio**:
   * Pilih menu **File ➔ Open...** lalu arahkan ke direktori proyek ini.
3. Tunggu proses **Gradle Sync** selesai.
4. Sambungkan perangkat Android Anda melalui kabel USB (pastikan *USB Debugging* aktif).
5. Klik tombol **Run 'app' (▶)** di Android Studio.

---

## 📄 Lisensi
Proyek ini dibuat untuk keperluan portofolio dan demonstrasi aplikasi Android modern dengan Jetpack Compose.
