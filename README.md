# Final Project ASD

Repository ini merupakan hasil final project kami kelompok 1 ASD Kelas D

# Dependency

Untuk menjalankan program ini, Anda perlu mengunduh
- `gradle`: build system java
- `sqlite`: database 

kemudian untuk dataset kamus bahasa inggris dan file database bisa diunduh [disini](https://drive.google.com/drive/folders/1nVx7JThv_91ZFFK8wzJT901f9aMV7Kin?usp=sharing)


Setelah file diunduh, masukkan file `.csv` kamus kedalam folder app/src/main/resources
```
└── src
     └── main
         ├── java
         │   └── com
         │       └── <nama>
         │           ├── entry
         │           │   └── Main.class
         │           └── tree
         │               ├── Node.class
         │               ├── RadixNode.class
         │               ├── RadixTree$Pair.class
         │               ├── RadixTree.class
         │               └── Trie.class
         └── resources
             ├── word_frequency_dataset.csv
             └── words_alpha.txt
```

Setelah itu, masukkan file `.db` di struktur terluar repository ini

```
$ git clone https://github.com/NashiwaInsan/asdfinalproject.git final-project
# masukkan langsung kedalam folder final project
$ cd final-project
```

Setelah itu, jalankan menggunakan `gradle` pada _project root_
```
$ gradle installDist && ./app/build/install/app/bin/app
```
