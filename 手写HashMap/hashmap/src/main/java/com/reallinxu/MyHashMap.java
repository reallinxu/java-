package com.reallinxu;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V> {

    //数组默认初始化长度
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    //阈值比例
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int defaultInitSize;

    private float defaultLoadFactor;

    //Map中entry数量
    private int entryUseSize;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int defaultInitialCapacity, float defaultLoadFactor) {
        if (defaultInitialCapacity < 0) {
            throw new IllegalArgumentException("Illegal intial capacity: " + defaultInitialCapacity);
        }

        if (defaultLoadFactor <= 0 || Float.isNaN(defaultLoadFactor)) {
            throw new IllegalArgumentException("illegal load factor: " + defaultLoadFactor);
        }

        this.defaultInitSize = defaultInitialCapacity;
        this.defaultLoadFactor = defaultLoadFactor;

        table = new MyEntry[this.defaultInitSize];
    }

    //数组
    private MyEntry<K, V>[] table = null;

    @Override
    public V put(K k, V v) {
        V oldValue = null;
        //扩容
        if (entryUseSize >= defaultInitSize * defaultLoadFactor) {
            resize(2 * defaultInitSize);
        }
        //得到Hash值，计算出数组中的位置
        int index = myHash(k) & (defaultInitSize - 1);
        if (table[index] == null) {
            table[index] = new MyEntry<K, V>(k, v, null);
            ++entryUseSize;
        } else {
            //需要遍历单链表
            MyEntry<K, V> myEntry = table[index];
            MyEntry<K, V> e = myEntry;
            while (e != null) {
                if (k == e.getKey() || k.equals(e.getKey())) {
                    oldValue = e.value;
                    e.value = v;
                    return oldValue;
                }
                e = e.next;
            }
            //头插法,jdk1.8统一使用尾插法
            table[index] = new MyEntry<K, V>(k, v, myEntry);
            ++entryUseSize;
        }
        return oldValue;
    }

    @Override
    public V get(K k) {

        int index = myHash(k) & (defaultInitSize - 1);

        if (table[index] == null) {
            return null;
        } else {
            MyEntry<K, V> entry = table[index];
            do {
                if (k == entry.getKey() || k.equals(entry.getKey())) {
                    return entry.getValue();
                } else {
                    entry = entry.next;
                }
            } while (entry != null);
        }
        return null;
    }

    private int myHash(Object k) {
        int hashCode = k.hashCode();
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    private void resize(int i) {
        MyEntry[] newTable = new MyEntry[i];
        //改变了数组的大小
        defaultInitSize = i;
        entryUseSize = 0;
        rehash(newTable);
    }

    private void rehash(MyEntry<K, V>[] newTable) {

        //得到老的entry集合，注意遍历单链表
        List<MyEntry<K, V>> entryList = new ArrayList<MyEntry<K, V>>();
        for (MyEntry<K, V> entry : table) {
            if (entry != null) {
                do {
                    entryList.add(entry);
                    entry = entry.next;
                } while (entry != null);
            }
        }

        //覆盖旧的引用
        if (newTable.length > 0) {
            table = newTable;
        }

        //所谓重新的hash就是重新put entry到hashMap
        for (MyEntry<K, V> myEntry : entryList) {
            put(myEntry.getKey(), myEntry.value);
        }
    }
}
