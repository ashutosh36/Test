package src.kafka.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private final int capacity;
    private final Map<String, Node> cache;
    private final Node head;
    private final Node tail;

    static class Node {
        String key;
        String value;
        Node prev;
        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public String get(String key) {
        Node node = cache.get(key);
        if (node != null) {
            // Move the accessed node to the front of the list
            moveToHead(node);
            return node.value;
        }
        return null;
    }

    public void put(String key, String value) {
        if (cache.size() >= capacity) {
            // Remove the least recently used element
            Node removed = removeTail();
            cache.remove(removed.key);
            System.out.println("Evicted "+ removed.value);
        }

        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToHead(newNode);
    }

    private void moveToHead(Node node) {
//        StringBuilder stringBuilder= new StringBuilder();
//        sb.s
        removeNode(node);
        addToHead(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private Node removeTail() {
        Node removed = tail.prev;
        removeNode(removed);
        return removed;
    }

    public void printCache() {
        Node current = head.next;
        while (current != tail) {
            System.out.println(current.key + ": " + current.value);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);

        cache.put("1", "One");
        cache.put("2", "Two");
        cache.put("3", "Three");

        System.out.println("Initial cache:");
        cache.printCache();

        System.out.println("Get 1: " + cache.get("1"));

        cache.put("4", "Four"); // Should evict "2" based on LRU

        System.out.println("Updated cache:");
        cache.printCache();
    }
}
