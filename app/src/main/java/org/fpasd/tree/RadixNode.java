package org.fpasd.tree;

import java.util.ArrayList;
import java.util.HashMap;

public class RadixNode {

	public String substr;
	public HashMap<Character, RadixNode> children;
	public boolean isEndOfWord;
	public int rank;

	public RadixNode() {
		this.substr = "";
		this.isEndOfWord = false;
		this.children = new HashMap<>();
		this.rank = -1;
	}

	public RadixNode(String substr) {
		this.substr = substr;
		this.isEndOfWord = false;
		this.children = new HashMap<>();
		this.rank = -1;
	}

	public void getChildrenSubstring(
			String prefix,
			String acc,
			ArrayList<String> words) {
		if (isEndOfWord) {
			words.add(prefix + acc.substring(1));
		}

		for (RadixNode child : children.values()) {
			child.getChildrenSubstring(prefix, acc + child.substr, words);
		}
	}

	public void getChildrenSubstringWithRank(
			String prefix,
			String acc,
			ArrayList<RadixTree.Pair> words) {
		if (isEndOfWord) {
			RadixTree.Pair pair = new RadixTree.Pair(
					prefix + acc.substring(1),
					rank);
			words.add(pair);
		}
		for (RadixNode child : children.values()) {
			child.getChildrenSubstringWithRank(
					prefix,
					acc + child.substr,
					words);
		}
	}
}
