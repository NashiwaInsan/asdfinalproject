package org.fpasd.tree;

import java.util.ArrayList;

// kelas `Trie` merupakan struktur data yang menyimpan `Node` paling awal (root node)
// dari kelas ini, kita bisa membuat fungsi-fungsi bantuan privat (private helper functions) untuk memudahkan kita
// dalam mengembangkan fungsi-fungsi operasi `Trie` seperti `insertWord()` dan `searchPartial()`
public class Trie {

	// field yang menunjukkan `Node` paling awal
	Node root;

	// konstruktor untuk pengguna
	public Trie() {
		this.root = new Node();
	}

	// fungsi untuk memasukkan `word` kedalam `Trie`
	public void insertWord(String word) {
		// untuk mengimplementasikan fungsi `insertword()` bisa dilakukan secara
		// iteratif atau rekursif.
		// pada kasus ini, kita menggunakan pendekatan iteratif.
		Node current = root;
		// iterasi setiap karakter pada `word`
		for (int i = 0; i < word.length(); i++) {
			// karakter ke-i pada `word`
			char cw = word.charAt(i);
			// jika `cw` tidak ada dalam `children` pada `Node` saat ini `current` artinya
			// karakter belum dimasukkan
			if (!current.hasCharInChildren(cw)) {
				Node newNode = new Node(cw);
				current.addChild(newNode);
			}
			// pada baris ini, artinya ada `cw` didalam `children` sehingga `current` (pada
			// awalnya dari `root`) menjadi
			// `root.children.get(newNode)`
			current = current.getChild(cw);
		}
		// ketika suatu kata berhasil dimasukkan kedalam `Trie`, `Node.isEndOfWord` akan
		// berubah menjadi `true`
		current.isEndOfWord = true;
	}

	// fungsi untuk mencari kata yang berawalan `query` atau `query` utuh yang ada
	// pada `Trie`
	public ArrayList<String> searchPartial(String query) {
		// `ArrayList` yang berisi `String` hasil pencarian
		ArrayList<String> searchResults = new ArrayList<>();
		Node current = root;

		// jika `current` dalam `Node` tidak memiliki `children`, hasil pencarian kosong
		if (current.children.size() == 0) {
			return searchResults;
		}

		// angka yang menentukan seberapa banyak karakter yang cocok secara berurutan
		// dalam `query`
		int matchedCharsIndex = 0;
		for (int i = 0; i < query.length(); i++) {
			char cq = query.charAt(i);

			// jika `Node` memiliki anakan dengan karakter `cq` maka `Trie` akan ditelusuri
			// lebih dalam
			if (current.hasCharInChildren(cq)) {
				matchedCharsIndex++;
				current = current.getChild(cq);
			} else {
				// jika kondisi sebelumnya tidak terpenuhi, fungsi akan mengembalikan sebuah
				// `ArrayList` kosong
				return searchResults;
			}
		}

		// `current` bukan merupakan kata utuh, tetapi hanya awalan kata-kata lain
		String matchedUpTo = query.substring(0, matchedCharsIndex);

		// kumpulkan kata-kata lain yang berawalan dengan `matchedUpTo` dan kembalikan
		// `searchResults`
		collectWords(current, matchedUpTo, searchResults);
		return searchResults;
	}

	// fungsi bantuan untuk mencari semua karakter anakan `node` sampai leaf.
	private void collectWords(
			Node node,
			String prefix,
			ArrayList<String> results) {
		if (node.isEndOfWord)
			results.add(prefix);

		for (Node child : node.children) {
			collectWords(child, prefix + child.ch, results);
		}
	}

	public ArrayList<String> printAllWords() {
		ArrayList<String> results = new ArrayList<>();
		collectWords(root, "", results);
		return results;
	}
}
