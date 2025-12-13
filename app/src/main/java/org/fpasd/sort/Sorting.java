package org.fpasd.sort;

import java.util.ArrayList;
import java.util.List;
import org.fpasd.tree.RadixTree;

public class Sorting {

    // Sort ArrayList<Pair> berdasarkan rank (ascending)
    // Rank kecil = lebih populer = muncul duluan
    public static void mergeSort(List<RadixTree.Pair> arr) {
        if (arr.size() <= 1) return;
        mergeSortHelper(arr, 0, arr.size() - 1);
    }

    public static void mergeSortHelper(
        List<RadixTree.Pair> arr,
        int left,
        int right
    ) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;

        mergeSortHelper(arr, left, mid);
        mergeSortHelper(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    public static void merge(
        List<RadixTree.Pair> arr,
        int left,
        int mid,
        int right
    ) {
        List<RadixTree.Pair> temp = new ArrayList<>();

        int i = left;
        int j = mid + 1;

        // PERUBAHAN UTAMA: Sort berdasarkan RANK, bukan length
        while (i <= mid && j <= right) {
            RadixTree.Pair itemI = arr.get(i);
            RadixTree.Pair itemJ = arr.get(j);

            // Compare by rank (ascending order)
            // Rank kecil (lebih populer) muncul duluan
            if (itemI.rank <= itemJ.rank) {
                temp.add(itemI);
                i++;
            } else {
                temp.add(itemJ);
                j++;
            }
        }

        // Copy sisa elemen
        while (i <= mid) {
            temp.add(arr.get(i++));
        }
        while (j <= right) {
            temp.add(arr.get(j++));
        }

        // Copy back ke array asli
        for (int k = 0; k < temp.size(); k++) {
            arr.set(left + k, temp.get(k));
        }
    }
}
