package com.reallinxu;

public class Test {

    public static void main(String[] args) {
        MyMap<String, String> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 500; i++) {
            myHashMap.put("key" + i, "value" + i);
        }

        for (int i = 0; i < 500; i++) {
            System.out.println("key" + i + ",value is : " + myHashMap.get("key" + i));
        }
    }

}
