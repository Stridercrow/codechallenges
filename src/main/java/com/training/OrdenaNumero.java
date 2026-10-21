package com.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdenaNumero {
	public static void main(String[] args) {
		int n = 4370129;
		List<Integer> a = new ArrayList<Integer>();
		OrdenaNumero o = new OrdenaNumero();
		Collections.reverse(o.convierteArreglo(n, a));
		System.out.println(a);
		o.bubbleSortDesc(a);
		System.out.println(a);		
	}
	
	public List<Integer> convierteArreglo(int n, List<Integer> a) {
		if(n < 10) {
			a.add(n);
			return a;
		} else {
			a.add(n%10);
			return convierteArreglo(n/10, a);
		}
	}
	
	public void bubbleSortDesc(List<Integer> a) {
		int aux = 0;
		for(int i = 0; i < a.size() - 1; i++) {
			for(int j = 0; j < a.size() - i - 1; j++) {
				if(a.get(j+1) > a.get(j)) {
					aux = a.get(j+1);
					a.set(j+1, a.get(j));
					a.set(j, aux);					
				}
			}
		}	
	}
}