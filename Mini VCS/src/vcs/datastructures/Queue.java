package vcs.datastructures;

import java.util.Iterator;

/**
 * Queue implementation for FIFO (First-In-First-Out) operations in the version control system.
 * Used for processing operations in the order they were received.
 * @param <T> Type of elements stored in the queue
 */
public class Queue<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Creates an empty queue
     */
    public Queue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    /**
     * Adds an element to the end of the queue
     * @param data The element to add
     */
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        
        size++;
    }
    
    /**
     * Removes and returns the element at the front of the queue
     * @return The element removed from the front of the queue
     * @throws IllegalStateException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        T data = first.data;
        first = first.next;
        
        if (first == null) {
            last = null;
        }
        
        size--;
        return data;
    }
    
    /**
     * Returns the element at the front of the queue without removing it
     * @return The element at the front of the queue
     * @throws IllegalStateException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        return first.data;
    }
    
    /**
     * Returns the number of elements in the queue
     * @return The size of the queue
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the queue is empty
     * @return true if the queue contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns an iterable of all elements in the queue
     * @return Iterable of all elements in the queue
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = first;
            
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
