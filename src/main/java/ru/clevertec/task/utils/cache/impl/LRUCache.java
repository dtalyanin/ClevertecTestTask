package ru.clevertec.task.utils.cache.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.task.utils.cache.Cache;

import java.util.HashMap;
import java.util.Map;

@Component
public class LRUCache<T> implements Cache<T> {

    private final int capacity;
    private final Map<Integer, Node<T>> elements;
    private final Node<T> head;
    private final Node<T> tail;

    public LRUCache(@Value("${cache.capacity}") int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity for cache should be more than 0, but was " + capacity);
        }

        this.capacity = capacity;
        this.elements = new HashMap<>();

        this.head = new Node<>();
        this.tail = new Node<>();

        head.next = tail;
        tail.prev = head;
    }

    @Override
    public T get(int key) {
        Node<T> current = elements.get(key);
        T value = null;
        if (current != null) {
            moveToHead(current);
            value = current.value;
        }
        return value;
    }

    @Override
    public void put(int key, T value) {
        if (elements.containsKey(key)) {
            Node<T> current = elements.get(key);
            current.value = value;
            moveToHead(current);
        } else {
            if (elements.size() == capacity) {
                elements.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node<T> node = new Node<>(key, value);
            elements.put(key, node);
            addToHead(node);
        }
    }

    @Override
    public void delete(int key) {
        Node<T> removed = elements.remove(key);
        if (removed != null) {
            remove(removed);
        }
    }

    private void addToHead(Node<T> node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void moveToHead(Node<T> node) {
        remove(node);
        addToHead(node);
    }


    private void remove(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private static class Node<T> {
        int key;
        T value;
        Node<T> prev;
        Node<T> next;

        public Node() {
        }

        public Node(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
