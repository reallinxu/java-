package com.reallinxu;

public class MyEntry<K, V> implements MyMap.Entry<K, V> {

    private K key;

    public V value;

    public MyEntry<K, V> next;

    public MyEntry() {
    }

    public MyEntry(K key, V value, MyEntry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
