package ru.clevertec.task.utils.cache.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.task.utils.cache.Cache;

import java.util.HashMap;
import java.util.Map;

@Component
public class LFUCache<T> implements Cache<T> {

    private final int capacity;
    private final Map<Integer, Node<T>> elements;
    private final Node<T> head;
    private final Node<T> tail;

    public LFUCache(@Value("${cache.capacity}") int capacity) {
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
            changeCurrentPosition(current);
            value = current.value;
        }
        return value;
    }

    @Override
    public void put(int key, T value) {
        if (elements.containsKey(key)) {
            Node<T> current = elements.get(key);
            current.value = value;
            changeCurrentPosition(current);
        } else {
            if (elements.size() == capacity) {
                elements.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node<T> node = new Node<>(key, value);
            elements.put(key, node);
            moveByFrequency(node);
        }
    }

    @Override
    public void delete(int key) {
        Node<T> removed = elements.remove(key);
        if (removed != null) {
            remove(removed);
        }
    }

    private void remove(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void changeCurrentPosition(Node<T> current) {
        remove(current);
        current.count++;
        moveByFrequency(current);
    }

    private void moveByFrequency(Node<T> node) {
        Node<T> curr = head.next;
        while (curr != null) {
            if (curr.count > node.count) {
                curr = curr.next;
            } else {
                node.prev = curr.prev;
                node.next = curr;
                node.next.prev = node;
                node.prev.next = node;
                break;
            }
        }
    }

    private static class Node<T> {
        int key;
        T value;
        int count;
        Node<T> prev;
        Node<T> next;

        public Node() {
        }

        public Node(int key, T value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
}
