package com.training;

/**
 * ArraysSimilar
 * 
 * Two arrays are called similar if one can be obtained from another by
 * swapping at most one pair of elements in one of the arrays.
 * Given two arrays a and b, check wheter they are similar.
 * 
 * Example:
 * For a=[1,2,3] and b=[1,2,3] the output should be solution (a,b)=true
 * The arrays are equal, no need to swap any elements.
 * 
 * For a=[1,2,3] and b=[2,1,3] the output should be solution (a,b)=true
 * We can obtain b from a by swapping 2 and 1 in b.
 * 
 * For a=[1,2,2] and b=[2,1,1] the output should be solution (a,b)=false
 * Any swap of any two elements either in a or in b won't make a and b equal
 * 
 */

public class ArraysSimilar {

    public static void main(String[] args) {

        int a[] = {1,2,3};
        int b[] = {2,1,3};

        int mismatches = 0;
        int mismatchIdx1 = 0;
        int mismatchIdx2 = 0;

        boolean areSimilar = false;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                mismatches++;
                if (mismatches == 1) {
                    mismatchIdx1 = i;
                } else if (mismatches == 2) {
                    mismatchIdx2 = i;
                } else {
                    break;
                }
            }
        }

        if (mismatches == 2) {
            areSimilar = a[mismatchIdx1] == b[mismatchIdx2] && a[mismatchIdx2] == b[mismatchIdx1];
        } else if (mismatches == 0) {
            areSimilar = true;
        }

        System.out.println("Are similar? " + areSimilar);
        
    }
}
