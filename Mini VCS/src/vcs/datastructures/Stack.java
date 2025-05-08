package vcs.datastructures;

import java.util.Iterator;

/**
 * Stack implementation for LIFO (Last-In-First-Out) operations in the version control system.
 * Used for traversal operations like navigating commit history.
 * @param <T> Type of elements stored in the stack
 */
public class Stack<T> implements Iterable<T> {
    private Node<T> top;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
    
    /**
     * Creates an empty stack
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }
    
    /**
     * Pushes an element onto the top of the stack
     * @param data The element to push
     */
    public void push(T data) {
        top = new Node<>(data, top);
        size++;
    }
    
    /**
     * Removes and returns the element at the top of the stack
     * @return The element removed from the top of the stack
     * @throws IllegalStateException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        T data = top.data;
        top = top.next;
        size--;
        
        return data;
    }
    
    /**
     * Returns the element at the top of the stack without removing it
     * @return The element at the top of the stack
     * @throws IllegalStateException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        return top.data;
    }
    
    /**
     * Returns the number of elements in the stack
     * @return The size of the stack
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the stack is empty
     * @return true if the stack contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns an iterable of all elements in the stack (from top to bottom)
     * @return Iterable of all elements in the stack
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
