package com.reallinxu.controller;

import com.reallinxu.anno.Autowired;
import com.reallinxu.anno.Controller;
import com.reallinxu.anno.RequestMapping;
import com.reallinxu.service.MyService;

@Controller
public class MyController {

    @Autowired
    public MyService myService;

    @RequestMapping(value = "/hello")
    public String hello(){
        return myService.hello();
    }
}
