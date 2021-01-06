package org.example.blog.controller;

import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.response.ResponseState;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        System.out.println("hello,world");
        return "hello";
    }


    @GetMapping("/test-json")
    public ResponseResult testJson() {
        return ResponseResult.SUCCESS().setData("hello");
    }

    @PostMapping("test-login")
    public ResponseResult testLogin(@RequestBody User user) {
        System.out.println(user.toString());
        ResponseResult loginSuccess = new ResponseResult(ResponseState.LOGIN_SUCCESS);
        return loginSuccess.setData("登录成功");
    }




}
