# Final Project ASD

Repository ini merupakan hasil final project kami kelompok 1 ASD Kelas D
Anggota Kelompok:
- Muhammad Akbar Ilman Setijadi
- Nashiwa Insan Muflih
- Gelar Ridho Ramadhan
- Fahyi Nashaqi

# Dependency

Untuk menjalankan program ini, Anda perlu mengunduh
- `gradle`: build system java
- `sqlite`: database 

kemudian untuk dataset kamus bahasa inggris dan file database bisa diunduh [disini](https://drive.google.com/drive/folders/1nVx7JThv_91ZFFK8wzJT901f9aMV7Kin?usp=sharing)


Setelah file diunduh, masukkan file `.csv` kamus kedalam folder *app/src/main/resources*

Setelah itu, masukkan file `.db` di struktur terluar repository ini
```
final-project/
├── latest_dictionary.db          # SQLite database untuk definisi kata
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
app\build\install\app\bin\app.bat
```

**macOS / Linux:**
```bash
gradle installDist && ./app/build/install/app/bin/app
```
