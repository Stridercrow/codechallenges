package com.training;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implement a modified stack that, in addition to using push and pop, allows you to find
 * the current minimum element in the stack by using a min operation.
 * 
 * Example
 * For operations = [ "push 10", "min", "push 5", "min", "push 8", "min", "pop", "min", "pop", "min"]
 * the output should be
 * solutions(operations) = [10,5,5,5,10]
 * 
 * The operations array contains 5 instances of the min operation. The results array contains 5 numbers,
 * each representing the minimum element in the stack at the moment when min was called
 * 
 * [Input/Output] execution time limit 3 seconds in Java
 * 
 */

public class CustomStackImplementation {

    public static void main(String[] args) {

        String[] operations = { "push 10", "min", "push 5", "min", "push 8", "min", "pop", "min", "pop", "min"};
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> minStack = new ArrayDeque<>();
        Integer solution[] = new Integer[5];
        int minCount = 0;

        for (int i = 0; i < operations.length; i++) {
            if (operations[i].contains("push")) {
                Integer value = Integer.valueOf(operations[i].substring(5));
                stack.push(value);
                if(minStack.isEmpty()) {
                    minStack.push(value);
                } else {
                    minStack.push(Math.min(minStack.peek(), value));
                }
            } else if (operations[i].equals("pop")) {
                stack.pop();
                minStack.pop();
            } else if (operations[i].equals("min")) {
                solution[minCount++] = minStack.peek();
            }
        }
        
        for (int i = 0; i < solution.length; i++) {
            System.out.println(solution[i].intValue());
        }
    }    
} 