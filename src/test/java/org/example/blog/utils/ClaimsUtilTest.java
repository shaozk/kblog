package org.example.blog.utils;


import org.example.blog.pojo.User;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

public class ClaimsUtilTest {



    @Test
    public void user2Claims() {
        User user = new User();
        user.setId("123");
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setRoles(Constants.User.ROLE_NORMAL);
        user.setUserName("康神");
        user.setState(Constants.User.DEFAULT_STATE);
        Map<String, Object> claims = ClaimsUtil.user2Claims(user);
        System.out.println(claims.toString());
    }

    @Test
    public void claims2User() {
//        Claims claims = Claims claims = JwtUtil.parseJWT(token);
//        claims.put("id", "123");
//        claims.put("user_name", "123");
//        claims.put("roles", "123");
//        claims.put("sign", "123");
//        User user = ClaimsUtil.claims2User(claims);
//        System.out.println(user.toString());
    }
}