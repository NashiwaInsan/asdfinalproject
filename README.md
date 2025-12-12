# Final Project ASD | Aplikasi Kamus Interaktif & Game Frekuensi Kata

Repository ini merupakan implementasi Proyek Akhir mata kuliah Algoritma dan Struktur Data (ASD) yang dikembangkan sebagai aplikasi berbasis *Command Line Interface* (CLI) interaktif. Aplikasi ini menyediakan fitur pencarian kata kamus yang efisien menggunakan struktur data canggih dan dilengkapi dengan mini-game edukatif.

## Anggota Kelompok

* Muhammad Akbar Ilman Setijadi
* Nashiwa Insan Muflih
* Gelar Ridho Ramadhan
* Fahyi Nashaqi

---

## 🚀 Fitur Utama

Aplikasi ini memiliki antarmuka interaktif REPL (Read-Eval-Print Loop) dan dua modul utama:

### 1. Dictionary Search (Pencarian Kamus)
* **Pencarian Berbasis Prefiks Cepat:** Menggunakan implementasi **Radix Tree** untuk pencarian kata yang sangat efisien berdasarkan awalan kata (*prefix*).
* **Autocompletion Interaktif:** Mengintegrasikan **JLine** untuk menyediakan saran kata secara *real-time* saat pengguna mengetik, didukung oleh Radix Tree.
* **Detail Kata Komprehensif:** Menampilkan Rank (menggunakan utilitas konversi ke bentuk ordinal), Frekuensi, dan Definisi kata yang diambil dari database **SQLite**.

### 2. Frequency Guessing Game (Permainan Tebak Frekuensi)
* **Mini-Game Kompetitif:** Sebuah permainan dua pemain bergantian menebak frekuensi kemunculan sebuah kata dalam korpus data. Penebak dengan selisih terdekat dengan frekuensi aktual memenangkan ronde.
* **Sistem Skor:** Permainan dimainkan dalam 3 putaran (*Best of 3*).

### Struktur Data & Algoritma (ASD)
* **Radix Tree:** Struktur data utama yang digunakan untuk optimasi pencarian awalan kata di modul kamus.
* **Trie:** Implementasi struktur data Trie juga tersedia dalam kode sebagai perbandingan atau pengembangan.
* **Merge Sort:** Implementasi algoritma *Merge Sort* (berdasarkan panjang kata) juga tersedia dalam proyek.

## 🛠️ Teknologi & Dependensi

Proyek ini dibangun menggunakan:

* **Bahasa Pemrograman:** Java (Versi 25, sesuai konfigurasi *toolchain* Gradle).
* **Sistem Build:** Gradle 9.2.1.
* **Struktur Data Kunci:** Radix Tree.

### Pustaka Pihak Ketiga (Dependencies)

| Pustaka | Deskripsi | Sumber |
| :--- | :--- | :--- |
| `org.jline:jline` | Menyediakan kapabilitas terminal interaktif dan REPL. |
| `org.jline:jline-terminal-ffm` | Implementasi terminal menggunakan Foreign Function Memory (FFM). |
| `com.github.lalyos:jfiglet` | Digunakan untuk membuat banner ASCII Art yang menarik (mis. "welcome", "Dictionary Search", "Frequency Game"). |
| `org.xerial:sqlite-jdbc` | Driver JDBC untuk koneksi dan interaksi dengan database SQLite. |

## ⚙️ Prasyarat Instalasi

Untuk menjalankan aplikasi ini, Anda memerlukan:

1.  **Java Development Kit (JDK):** Direkomendasikan JDK 25 atau versi yang kompatibel (sesuai konfigurasi *toolchain* proyek).
2.  **Gradle:** Build system untuk mengompilasi dan menjalankan aplikasi.
3.  **SQLite:** Meskipun driver JDBC sudah disertakan, Anda mungkin perlu tools SQLite untuk menyiapkan/mengakses database secara eksternal.

### Setup Data

Aplikasi ini bergantung pada dua file data eksternal:

1.  **`word_frequency_with_definitions.csv`**: File dataset yang berisi daftar kata, rank, dan frekuensi. Ini digunakan untuk membangun `RadixTree` dan *map* frekuensi/rank.
2.  **`dictionary.db`**: Database SQLite yang menyimpan definisi kata. Aplikasi akan mencari definisi berdasarkan `word_id` (yang sama dengan `rank` di file CSV).

**Langkah-langkah Setup:**

1.  Unduh file dataset `.csv` dan `.db` dari sumber yang telah disediakan oleh pengembang [disini](https://drive.google.com/drive/folders/1nVx7JThv_91ZFFK8wzJT901f9aMV7Kin?usp=sharing)
2.  Tempatkan file **`word_frequency_with_definitions.csv`** ke dalam direktori sumber daya Java:
    ```
    final-project/app/src/main/resources/
    ```
3.  Tempatkan file **`dictionary.db`** di direktori root proyek:
    ```
    final-project/dictionary.db
    ```
 ## 🌳 Susunan Proyek
Struktur direktori utama proyek adalah sebagai berikut:
```
final-project/
├── dictionary.db          # SQLite database untuk definisi kata
│
└── app/
     └── src/
          └── main/
              ├── java/
              │   └── com/
              │       └── <nama>/
              │           ├── entry/
              │           │   └── Main.class
              │           └── tree/
              │               ├── Node.class
              │               ├── RadixNode.class
              │               ├── RadixTree$Pair.class
              │               ├── RadixTree.class
              │               └── Trie.class
              │
              └── resources/
                  └── word_frequency_dataset.csv    # Dataset kata dengan rank dan frekuensi
```
## Installation

### Clone Repository
```bash
git clone https://github.com/NashiwaInsan/asdfinalproject.git final-project
cd final-project
```

### Install Gradle
Pastikan Gradle sudah terinstall. Cek dengan:
```bash
gradle --version
```

Jika belum ada, install dari [gradle.org/install](https://gradle.org/install/) atau:
- **Windows**: `choco install gradle` atau `scoop install gradle`
- **macOS**: `brew install gradle`  
- **Linux**: `sudo apt install gradle`

### Run Application

**Windows:**
```bash
gradle installDist
./app/build/install/app/bin/app
```

**macOS / Linux:**
```bash
gradle installDist && ./app/build/install/app/bin/app
```
