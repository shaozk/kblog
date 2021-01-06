package org.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @ResponseBody
    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    public String helloWorld() {
        System.out.println("hello,world");
        return "hello";
    }


}
