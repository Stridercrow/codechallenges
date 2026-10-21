package com.training;

import java.util.Arrays;

/**
 * QuickSortLomuto
 * 
 * int a[] = [7, 2, 1, 6, 8, 5, 3, 4]
 * i tracks the last position of elements < pivot
 * j scans through the array
 * When arr[j] <= pivot, swap arr[i+1] and arr[j], increment i
 * Finally, swap pivot into position i+1
 * 
 */

public class QuickSortLomuto {

    public static void main(String[] args) {
        int[] arr= {7, 2, 1, 6, 8, 5, 3, 4};

        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));

    }

    static void quickSort(int[] arr, int startIdx, int endIdx) {
        if(startIdx < endIdx) {
            int pivotIdx = partitionLomuto(arr, startIdx, endIdx);
            quickSort(arr, startIdx, pivotIdx - 1);
            quickSort(arr, pivotIdx + 1, endIdx);
        }
    }

    static int partitionLomuto(int[] arr, int startIdx, int endIdx) {
        int pivot = arr[endIdx];
        int i = startIdx - 1;
        
        for(int j = startIdx; j < endIdx; j++) {
            if(arr[j] <= pivot) {
                int tmp = arr[i+1];
                arr[i+1] = arr[j];
                arr[j] = tmp;
                i++;
            }
        }
        int tmp = arr[i+1];
        arr[i+1] = arr[endIdx];
        arr[endIdx] = tmp;

        return i + 1;
    }

}
 