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
     *
     * 使用场景：注册、找回密码、修改邮箱（会输入新的邮箱）
     * 注册（register）：如果已经注册过了，提示该邮箱已经住注册
     * 找回密码（forget）：如果没有注册过，提示该邮箱没有注册
     * 修改邮箱（update）（新的邮箱）：如果已经注册了，提示该邮箱已经注册过了
     *
     * @param type
     * @param request
     * @param emailAddress
     * @return
     */
    ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress);

    /**
     * 用户注册
     * @param user
     * @param emailCode
     * @param captchaCode
     * @param captchaKey
     * @param request
     * @return
     */
    ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request);

    /**
     * 用户登陆
     * @param captcha
     * @param captchaKey
     * @param user
     * @param request
     * @param response
     * @return
     */
    ResponseResult doLogin(String captcha, String captchaKey, User user, HttpServletRequest request, HttpServletResponse response);
}
