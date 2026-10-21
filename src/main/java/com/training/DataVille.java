package com.training;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * 
 * DataVille
 * In the digital city of DataVille, efficient data management is critical. The city's database is represented as a binary array, databaseStorage, containing 
 * n bits of data. Each bit represents one or two types of data:
 * Standard data: Represented by 0
 * Priority data: Represented by 1To optimize the efficiency during peak operational hours, the number of transitions between 0s and 1s in the array
 * must be minimized. Transitions are points in the array where adjacent elements differ (e.g., a 0 followed by a 1 or a 1 followed by a 0).
 * Operations:
 * You can swap any two bits in the array as many times as you like
 * The goal is to rearrange the bits to minimize transitions with the minimum number of swaps.
 * Consider [1, 0, 1, 0, 1] - 1 swap minimum
 * Consider [1,0,1,1,0,0,1] - 1 swap minimum
 * Consider [1,0,0,1,1,0,0,0,1] - 2 swaps minimum
 */
public class DataVille {

    public static void main(String[] args) {
        int[] dataBaseStorage = {1,0,0,1,1,0,0,0,1};

        minimumSwapComparing(dataBaseStorage);

    }

    static int minimumSwapComparing(int[] databaseStorage) {
        IntStream.of(databaseStorage).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).forEach(System.out::println);
        
        return 1;
    }

    static int minimumSwaps(int[] dataBaseStorage) {
        int length = dataBaseStorage.length;
        int numberOfOnes = 0;
        int numberOfZero = 0;

        for (int i=0; i < length; i++) {
            if(dataBaseStorage[i] == 0) numberOfZero++;
            else numberOfOnes++;
        }

        int swapZeros = 0;
        for (int i=0; i< numberOfOnes; i++) {
            if(dataBaseStorage[i] == 0) swapZeros++;
        }

        int swapOnes = 0;
        for (int i=0; i < numberOfZero; i++) {
            if(dataBaseStorage[i] == 1) swapOnes++;
        }

        return Math.min(swapOnes, swapZeros);
    } 

}
