package org.example.blog.utils;

import java.util.HashMap;
import java.util.Map;

public class TestCreateToken {
    public static void main(String[] args) {

        Map<String, Object> claims = new HashMap();
        claims.put("id","1347739547248758784");
        claims.put("userName","李白");
        claims.put("role","role_normal");
        claims.put("avatar","https://cdn.sunofbeaches.com/images/default_avatar.png");
        claims.put("email","913667678@qq.com");
        String token = JwtUtil.createToken(claims);
        System.out.println(token);
    }
}
