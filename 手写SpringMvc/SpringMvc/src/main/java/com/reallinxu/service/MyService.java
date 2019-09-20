package com.reallinxu.service;

import com.reallinxu.anno.Service;

@Service
public class MyService {
    public String hello() {
        System.out.println("Hello World!");
        return "Hello World!";
    }
}
