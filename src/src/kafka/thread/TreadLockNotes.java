package src.kafka.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TreadLockNotes {
}



class SynchronizedHashMapWithReadWriteLock {

    Map<String,String> syncHashMap = new HashMap<>();
    ReadWriteLock lock = new ReentrantReadWriteLock();
    // ...
    Lock writeLock = lock.writeLock();

    public void put(String key, String value) {
        try {
            writeLock.lock();
            syncHashMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
    public String remove(String key){
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    Lock readLock = lock.readLock();
    //...
    public String get(String key){
        try {
            readLock.lock();
            return syncHashMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }
}

class KeyLockHashMap<K, V> {
    private final HashMap<K, V> map = new HashMap<>();
    private final HashMap<K, ReentrantReadWriteLock> locks = new HashMap();

    public V read(K key) {
        Lock lock = getLock(key).readLock();
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    public void write(K key, V value) {
        Lock lock = getLock(key).writeLock();
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    private ReentrantReadWriteLock getLock(K key) {
        locks.putIfAbsent(key, new ReentrantReadWriteLock());
        return locks.get(key);
    }

    public static void main(String[] args) {
        KeyLockHashMap<String, Integer> keyLockHashMap = new KeyLockHashMap<>();

        keyLockHashMap.write("one", 1);
        keyLockHashMap.write("two", 2);

        System.out.println("Read 'one': " + keyLockHashMap.read("one"));
        System.out.println("Read 'two': " + keyLockHashMap.read("two"));
    }
}

