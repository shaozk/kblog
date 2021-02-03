package org.example.blog.services;

import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {
    /**
     * 初始化管理员账号
     * @param user
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

    /**
     * 检查用户是否有登陆
     * @return
     */
    User checkUser();

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ResponseResult getUserInfo(String userId);

    /**
     * 检查邮箱
     * @param email
     * @return
     */
    ResponseResult checkEmail(String email);

    /**
     * 检查用户
     * @param userName
     * @return
     */
    ResponseResult checkUserName(String userName);

    /**
     * 更新用户信息
     * @param userId
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(String userId, User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult doLogout();

    /**
     * 更新邮箱
     * @param email
     * @param verifyCode
     * @return
     */
    ResponseResult updateEmail(String email, String verifyCode);

    /**
     * 更新密码
     * @param verifyCode
     * @param user
     * @return
     */
    ResponseResult updateUserPassword(String verifyCode, User user);

    /**
     * 删除用户
     * 并不是真的删除，而是修改状态
     * PS：需要管理员权限
     * @param userId
     * @return
     */
    ResponseResult deleteUserById(String userId);

    /**
     * 用户列表
     * @param page
     * @param size
     * @return
     */
    ResponseResult listUser(int page, int size);


}
