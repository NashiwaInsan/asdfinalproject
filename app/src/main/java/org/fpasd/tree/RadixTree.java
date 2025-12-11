package org.fpasd.tree;

import java.util.ArrayList;
import java.util.HashMap;

public class RadixTree {

	public static class Pair {

		public String word;
		public int rank;

		Pair(String word, int rank) {
			this.word = word;
			this.rank = rank;
		}
	}

	RadixNode root;

	public void insertWord(String word, int rank) {
		RadixNode current = root;
		int currentCharIndex = 0;
		int length = word.length();
		while (currentCharIndex < length) {
			char c = word.charAt(currentCharIndex);

			if (!current.children.containsKey(c)) {
				RadixNode newNode = new RadixNode(
						word.substring(currentCharIndex));
				newNode.isEndOfWord = true;
				newNode.rank = rank;
				current.children.put(c, newNode);
				break;
			}

			current = current.children.get(c);

			int prefixLength = commonPrefixLength(
					current.substr,
					word.substring(currentCharIndex));

			currentCharIndex += prefixLength;

			if (prefixLength < current.substr.length()) {
				RadixNode newNode = new RadixNode(
						current.substr.substring(prefixLength));
				newNode.isEndOfWord = current.isEndOfWord;
				newNode.rank = current.rank;
				newNode.children = current.children;
				current.substr = current.substr.substring(0, prefixLength);
				HashMap<Character, RadixNode> tmp = new HashMap<>();
				tmp.put(newNode.substr.charAt(0), newNode);
				current.children = tmp;
				current.isEndOfWord = (prefixLength == length);
				current.rank = (prefixLength == length) ? rank : -1;
			}
		}
	}

	public ArrayList<String> search(String word) {
		ArrayList<String> results = new ArrayList<>();

		RadixNode current = root;
		int i = 0;
		while (i < word.length()) {
			char c = word.charAt(i);

			if (!current.children.containsKey(c)) {
				return results;
			}

			current = current.children.get(c);

			int prefixLength = commonPrefixLength(
					word.substring(i),
					current.substr);

			i += prefixLength;
		}

		if (current.isEndOfWord) {
			results.add(word);
			return results;
		}

		current.getChildrenSubstring(word, current.substr, results);

		return results;
	}

	public ArrayList<Pair> searchWithRank(String word) {
		ArrayList<Pair> results = new ArrayList<>();

		RadixNode current = root;
		int i = 0;
		while (i < word.length()) {
			char c = word.charAt(i);

			if (!current.children.containsKey(c)) {
				return results;
			}

			current = current.children.get(c);

			int prefixLength = commonPrefixLength(
					word.substring(i),
					current.substr);

			i += prefixLength;
		}

		if (current.isEndOfWord) {
			Pair pair = new Pair(word, current.rank);
			results.add(pair);
			return results;
		}

		current.getChildrenSubstringWithRank(word, current.substr, results);

		return results;
	}

	private int commonPrefixLength(String a, String b) {
		int prefixLength = 0;
		int min = Math.min(a.length(), b.length());
		for (int i = 0; i < min; i++) {
			if (a.charAt(i) == b.charAt(i)) {
				prefixLength += 1;
			}
		}
		return prefixLength;
	}

	public RadixTree() {
		this.root = new RadixNode();
	}
}
