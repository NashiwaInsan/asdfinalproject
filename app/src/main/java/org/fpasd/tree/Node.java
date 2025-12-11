package org.fpasd.tree;

import java.util.ArrayList;
import java.util.List;

// internal data inside of a `Trie`
public class Node {

	// field yang menandakan bahwa suatu `Node` merupakan akhir dari suatu kata
	boolean isEndOfWord;
	// field untuk menyimpan huruf dalam `Node`
	char ch;
	// field yang menunjukkan anak-anak `Node`. tipe data field ini bisa beragam
	// seperti `Node[]` dan `Map<Character, Node>`.
	ArrayList<Node> children;

	// konstruktor bawaan jika pengguna tidak memasukkan sebuah karakter
	public Node() {
		this.isEndOfWord = false;
		this.children = new ArrayList<>();
	}

	// konstruktor jika pnegguna memasukkan sebuah karakter
	public Node(char ch) {
		this.ch = ch;
		this.isEndOfWord = false;
		this.children = new ArrayList<>();
	}

	// fungsi pembantu untuk debugging
	@Override
	public String toString() {
		return "<(" + ch + ")>";
	}

	// fungsi untuk menentukan apakah ada `c` ada didalam `Node` pada `children`
	public boolean hasCharInChildren(char c) {
		int length = children.size();
		boolean result = false;

		// jika panjang dari `ArrayList<Node>` adalah 0, artinya tidak ada anakan yang
		// dicari
		if (length == 0) {
			return result;
		}

		// jika ada `children` memiliki `Node`, `result` akan diubah menjadi `true` jika
		// `node.ch == c`
		for (Node node : children) {
			if (node.ch == c) {
				result = true;
			}
		}

		return result;
	}

	// fungsi untuk ngeprint seluruh input yang pernah dimasukkan kedalam `Node` dan
	// anak-anaknya
	public void printTree(String accumulator) {
		// jika `Node` merupakan kata untuk dan tidak ada anakan lain, maka berhenti
		if (isEndOfWord && children.size() == 0) {
			System.out.println("EOW with no other children: " + accumulator);
			return;
		}

		// jika `Node` merupakan kata utuh, maka print
		if (isEndOfWord) {
			System.out.println("EOW: " + accumulator);
		}

		for (Node node : children) {
			node.printTree(accumulator + node.ch);
		}
	}

	// fungsi pembantu untuk memasukkan `Node` kedalam `children`
	public void addChild(Node child) {
		this.children.add(child);
	}

	// fungsi pembantu untuk mendapatkan `Node` didalam `children`
	public Node getChild(char c) {
		for (Node node : children) {
			if (node.ch == c) {
				return node;
			}
		}
		return null;
	}

	public static void mergeSort(List<String> arr) {
		if (arr.size() <= 1)
			return;
		mergeSortHelper(arr, 0, arr.size() - 1);
	}

	public static void mergeSortHelper(List<String> arr, int left, int right) {
		if (left >= right)
			return;
		int mid = (left + right) / 2;

		mergeSortHelper(arr, left, mid);
		mergeSortHelper(arr, mid + 1, right);

		merge(arr, left, mid, right);
	}

	private static void merge(List<String> arr, int left, int mid, int right) {
		List<String> temp = new ArrayList<>();

		int i = left;
		int j = mid + 1;

		while (i <= mid && j <= right) {
			String itemI = arr.get(i);
			String itemJ = arr.get(j);
			int d1 = itemI.length();
			int d2 = itemJ.length();

			if (d1 <= d2) {
				temp.add(itemI);
				i++;
			} else {
				temp.add(itemJ);
				j++;
			}
		}

		while (i <= mid) {
			temp.add(arr.get(i++));
		}
		while (j <= right) {
			temp.add(arr.get(j++));
		}

		// copy back
		for (int k = 0; k < temp.size(); k++) {
			arr.set(left + k, temp.get(k));
		}
	}
}
