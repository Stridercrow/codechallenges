package com.training;

import java.util.HashSet;
import java.util.Set;

public class SimpleLinkedList {

    static class SimpleLinkedListNode {
        int data;
        SimpleLinkedListNode next;

        public SimpleLinkedListNode(int data) {
            this.data = data;            
        }
    }
    
    private SimpleLinkedListNode head;

    public SimpleLinkedListNode getHead() {
        return head;
    }

    public void setHead(SimpleLinkedListNode head) {
        this.head = head;
    }

    public void add(int data) {
        SimpleLinkedListNode newNode = new SimpleLinkedListNode(data);
        if(head == null) {
            head = newNode;
            return;
        }
         
        SimpleLinkedListNode current = head;
        while(current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        
    }

    // 1 -> 2 -> 2 -> 2 -> 4 -> 1
    public static SimpleLinkedListNode removeDuplicates (SimpleLinkedListNode head) {
        SimpleLinkedListNode current = head;
        Set<Integer> distinct = new HashSet<>();

        if(current != null) {
            distinct.add(current.data);
        }
        
        while(current.next != null) {
            if(current.next != null && !distinct.add(current.next.data)) {
                current.next = current.next.next;
                continue;
            }

            current = current.next;
        }
        
        return head;
    }

    public static void main(String [] args) {
        SimpleLinkedList simpleLinkedList = new SimpleLinkedList();
        simpleLinkedList.add(1);
        simpleLinkedList.add(2);
        simpleLinkedList.add(2);
        simpleLinkedList.add(2);
        simpleLinkedList.add(4);
        simpleLinkedList.add(1);

        SimpleLinkedListNode current = simpleLinkedList.getHead();

        StringBuilder string = new StringBuilder();

        while (current != null) {
            string.append(current.data + " -> ");
            current = current.next;
        }

        System.out.println(string.toString());

        SimpleLinkedListNode updatedCurrent = SimpleLinkedList.removeDuplicates(simpleLinkedList.getHead());
        StringBuilder string2 = new StringBuilder();

        while (updatedCurrent != null) {
            string2.append(updatedCurrent.data + " -> ");
            updatedCurrent = updatedCurrent.next;
        }

        System.out.println(string2.toString());

    }
}
