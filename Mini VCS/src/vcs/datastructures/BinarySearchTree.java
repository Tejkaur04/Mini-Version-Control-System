package vcs.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree implementation for efficient storage and retrieval of file versions.
 * Used to quickly look up file versions by key.
 * @param <K> Type of keys for the tree (must be comparable)
 * @param <V> Type of values stored in the tree
 */
public class BinarySearchTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Creates an empty binary search tree
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair into the tree
     * @param key The key
     * @param value The value
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // Update value if key already exists
        }

        return node;
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

        Node<K, V> node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node<K, V> get(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * Removes the specified key and its associated value from the tree
     * @param key The key to remove
     * @return The value associated with the key, or null if not found
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        V value = get(key);
        if (value == null) {
            return null;
        }

        root = remove(root, key);
        size--;
        return value;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            // Case 1: No children
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: One child
            else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Case 3: Two children
            else {
                Node<K, V> successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = remove(node.right, successor.key);
            }
        }

        return node;
    }

    private Node<K, V> findMin(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Checks if the tree contains the specified key
     * @param key The key to check
     * @return true if the tree contains the key
     */
    public boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value pairs in the tree
     * @return The size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the tree is empty
     * @return true if the tree contains no key-value pairs
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an in-order traversal of the keys in the tree
     * @return Iterable of keys in ascending order
     */
    public Iterable<K> keys() {
        List<K> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(Node<K, V> node, List<K> list) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.left, list);
        list.add(node.key);
        inOrderTraversal(node.right, list);
    }
}
