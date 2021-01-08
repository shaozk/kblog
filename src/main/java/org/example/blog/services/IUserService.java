package org.example.blog.services;

import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {
    /**
     * 初始化管理员账号
     * @param user
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(User user, HttpServletRequest request);

    /**
     * 创建图灵验证码
     * 有效时长为10分钟
     * @param response
     * @param captchaKey
     * @throws Exception
     */
    void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    /**
     * 发送邮件email
     * @param request
     * @param emailAddress
     * @return
     */
    ResponseResult sendEmail(HttpServletRequest request, String emailAddress);
}
