package org.example.blog.utils;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.pojo.User;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class ClaimsUtil {
    public static final String ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String ROLES = "roles";
    public static final String AVATAR = "avatar";
    public static final String EMAIL = "email";
    public static final String SIGN = "sign";

    public static Map<String, Object> user2Claims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, user.getId());
        claims.put(USER_NAME, user.getUserName());
        claims.put(ROLES, user.getRoles());
        claims.put(AVATAR, user.getAvatar());
        claims.put(EMAIL, user.getEmail());
        claims.put(SIGN, user.getSign());
        return claims;

    }

    public static User claims2User(Claims claims) {
          User user = new User();
          String id = (String) claims.get(ID);
          String userName = (String) claims.get(USER_NAME);
          String roles = (String) claims.get(ROLES);
          String avatar = (String) claims.get(AVATAR);
          String email = (String) claims.get(EMAIL);
          String sign = (String) claims.get(SIGN);

          user.setId(id);
          user.setUserName(userName);
          user.setRoles(roles);
          user.setAvatar(avatar);
          user.setEmail(email);
          user.setSign(sign);

          return user;
    }
}
