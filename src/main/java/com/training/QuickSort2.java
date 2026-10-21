package com.training;

import java.util.Arrays;

public class QuickSort2 {
	
	public static void main(String... args) {
		
		Integer a[] = {10, 1, 4, 8, 2, 3};
		Integer b[] = {10, 1, 4, 8, 2, 3};
		
		quickSortPivotBegin(a, 0, a.length-1);
		
		System.out.println(Arrays.asList(a));
		
		quickSortPartition(b, 0, b.length-1);
		
		System.out.println(Arrays.asList(b));
		
	}
	
	private static void quickSortPivotBegin(Integer[] a, int begin, int end) {
		int pivot = a[begin];
		int i = begin;
		int j = end;
		int temp;
		
		while(i < j) {
			while(a[i] <= pivot && i < j) i++;
			while(a[j] > pivot) j--;
			if(i < j) {
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}
		
		a[begin] = a[j];
		a[j] = pivot;
		
		if(begin < j-1)
			quickSortPivotBegin(a, begin, j-1);
		if(j+1 < end)
			quickSortPivotBegin(a, j+1, end);
		
	}
	
	private static void quickSortPartition(Integer[] a, int begin, int end) {
		
		if(begin < end) {
			int pivot = partition(a, begin, end);
			quickSortPartition(a, begin, pivot-1);
			quickSortPartition(a, pivot+1, end);
		}
		
		
	}
	
	private static int partition(Integer[] a, int begin, int end) {
		int pivot = a[end];
		int i = (begin -1);
		
		for(int j=begin; j<end; j++) {
			if(a[j] <= pivot) {
				i++;
				int aux = a[i];
				a[i] = a[j];
				a[j] = aux;
			}
		}
		
		int aux = a[i+1];
		a[i+1] = a[end];
		a[end] = aux;
						
		
		return i+1;
	}
 }