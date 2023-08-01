package com.microservice.songservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="demo/")
public class DemoController {

    @GetMapping("hello")
    public String sayHello() {
        return "Hello from DemoController";
    }
}
