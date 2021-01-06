package org.example.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        System.out.println("hello,world");
        return "hello";
    }

    /*
    @GetMapping("/test-json")
    public User testJson() {
        User user = new User("邵梓康",20,"男");
        House house = new House("豪宅","湖南汨罗");
        user.setHouse(house);
        return user;
    }
    */



}
