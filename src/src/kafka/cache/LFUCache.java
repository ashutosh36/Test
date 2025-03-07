package src.kafka.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {
    private final int capacity;
    private final Map<String, String> cache;
    private final Map<String, Integer> frequency;
    private final Map<Integer, LinkedHashSet<String>> frequencyList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.frequency = new HashMap<>(capacity);
        this.frequencyList = new HashMap<>();
        this.frequencyList.put(1, new LinkedHashSet<>());
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }

        // Update frequency and reorganize the frequencyList
        int freq = frequency.get(key);
        frequency.put(key, freq + 1);

        frequencyList.get(freq).remove(key);

        if (!frequencyList.containsKey(freq + 1)) {
            frequencyList.put(freq + 1, new LinkedHashSet<>());
        }
        frequencyList.get(freq + 1).add(key);

        return cache.get(key);
    }

    public void put(String key, String value) {
        if (capacity <= 0) {
            return;
        }

        if (cache.size() >= capacity) {
            // Evict the least frequently used item
            int minFreq = frequencyList.keySet().iterator().next();
            String evicted = frequencyList.get(minFreq).iterator().next();
            frequencyList.get(minFreq).remove(evicted);
            cache.remove(evicted);
            frequency.remove(evicted);
        }

        cache.put(key, value);
        frequency.put(key, 1);
        frequencyList.get(1).add(key);
    }

    public void printCache() {
        System.out.println("Cache:");
        for (String key : cache.keySet()) {
            System.out.println(key + ": " + cache.get(key));
        }
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(3);

        cache.put("1", "One");
        cache.put("2", "Two");
        cache.put("3", "Three");

        System.out.println("Initial cache:");
        cache.printCache();

        System.out.println("Get 1: " + cache.get("1"));
        System.out.println("Get 2: " + cache.get("2"));

        cache.put("4", "Four"); // Should evict "3" based on LFU

        System.out.println("Updated cache:");
        cache.printCache();
    }
}
