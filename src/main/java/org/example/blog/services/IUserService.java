package org.example.blog.services;

import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    ResponseResult initManagerAccount(User user, HttpServletRequest request);

}
