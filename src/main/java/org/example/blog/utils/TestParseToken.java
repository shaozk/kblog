package org.example.blog.utils;

import io.jsonwebtoken.Claims;

public class TestParseToken {
    public static void main(String[] args) {
        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoicm9sZV9ub3JtYWwiLCJpZCI6IjEzNDc3Mzk1NDcyNDg3NTg3ODQiLCJhdmF0YXIiOiJodHRwczovL2Nkbi5zdW5vZmJlYWNoZXMuY29tL2ltYWdlcy9kZWZhdWx0X2F2YXRhci5wbmciLCJ1c2VyTmFtZSI6IuadjueZvSIsImV4cCI6MTYxMDE2MzU0OSwiZW1haWwiOiI5MTM2Njc2NzhAcXEuY29tIn0.4sHt1tummPiG_p_-MeFEu87McX_y6qYAQztF2gqd8uQ");

        Object id = claims.get("id");
        Object user = claims.get("userName");
        Object role = claims.get("role");
        Object avatar = claims.get("avatar");
        Object email = claims.get("email");

        System.out.println("id ==> " + id);
        System.out.println("user ==> " + user);
        System.out.println("role ==> " + role);
        System.out.println("avatar ==> " + avatar);
        System.out.println("email ==> " + email);


    }
}
