package org.fpasd.sort;

import org.fpasd.tree.RadixTree;
import java.util.ArrayList;
import java.util.List;

public class Sorting {

	public static void mergeSort(List<RadixTree.Pair> arr) {
		if (arr.size() <= 1)
			return;
		mergeSortHelper(arr, 0, arr.size() - 1);
	}

	public static void mergeSortHelper(
			List<RadixTree.Pair> arr,
			int left,
			int right) {
		if (left >= right)
			return;
		int mid = (left + right) / 2;

		mergeSortHelper(arr, left, mid);
		mergeSortHelper(arr, mid + 1, right);

		merge(arr, left, mid, right);
	}

	public static void merge(
			List<RadixTree.Pair> arr,
			int left,
			int mid,
			int right) {
		List<RadixTree.Pair> temp = new ArrayList<>();

		int i = left;
		int j = mid + 1;

		while (i <= mid && j <= right) {
			RadixTree.Pair itemI = arr.get(i);
			RadixTree.Pair itemJ = arr.get(j);
			int d1 = itemI.word.length();
			int d2 = itemJ.word.length();

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
