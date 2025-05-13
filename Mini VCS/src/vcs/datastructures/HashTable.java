package vcs.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * HashTable implementation for efficient key-value lookups in the version control system.
 * Used for quick lookup of files by name or path.
 * @param <K> Type of keys stored in the hash table
 * @param <V> Type of values stored in the hash table
 */
public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private Entry<K, V>[] table;
    private int size;

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Creates a hash table with default capacity
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a hash table with the specified capacity
     * @param capacity Initial capacity of the table
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }

    /**
     * Computes the hash code for a key
     * @param key The key to hash
     * @return The hash code
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    /**
     * Inserts a key-value pair into the hash table
     * @param key The key
     * @param value The value
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        // Resize if necessary
        if (size >= table.length * LOAD_FACTOR_THRESHOLD) {
            resize(2 * table.length);
        }

        int index = hash(key);

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }

        table[index] = new Entry<>(key, value, table[index]);
        size++;
    }

    /**
     * Gets the value associated with the given key
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (key.equals(entry.key)) {
                return entry.value;
            }
        }

        return null;
    }

    /**
     * Removes the specified key and its associated value from the hash table
     * @param key The key to remove
     * @return The value associated with the key, or null if not found
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        Entry<K, V> prev = null;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (key.equals(current.key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Checks if the hash table contains the specified key
     * @param key The key to check
     * @return true if the hash table contains the key
     */
    public boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value pairs in the hash table
     * @return The size of the hash table
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the hash table is empty
     * @return true if the hash table contains no key-value pairs
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Resizes the hash table to the specified capacity
     * @param capacity The new capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        HashTable<K, V> temp = new HashTable<>(capacity);

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                temp.put(entry.key, entry.value);
                entry = entry.next;
            }
        }

        this.table = temp.table;
        this.size = temp.size;
    }

    /**
     * Returns all keys in the hash table
     * @return Iterable of all keys in the hash table
     */
    public Iterable<K> keys() {
        List<K> keys = new ArrayList<>();

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }

        return keys;
    }

    /**
     * Clears all entries from the hash table.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        this.table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY]; // Reset to default capacity
        this.size = 0;
    }
}