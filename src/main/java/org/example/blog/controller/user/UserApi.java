package org.example.blog.controller.user;


import lombok.extern.slf4j.Slf4j;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员账号-init-admin
     *
     * @param user
     * @return
     */
    @PostMapping("/admin-account")
    public ResponseResult initManagerAccount(@RequestBody User user, HttpServletRequest request) {
        log.info("user name ==> " + user.getUserName());
        log.info("password ==> " + user.getPassword());
        log.info("email ==> " + user.getEmail());
        return userService.initManagerAccount(user, request);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody User user) {
        return null;
    }

    /**
     * 登录sign-up
     *
     * @param captcha
     * @param user
     * @return
     */
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha, @RequestBody User user) {
        return null;
    }

    /**
     * 获取图灵验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public ResponseResult getCaptcha() {
        return null;
    }

    /**
     * 发送邮件email
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("email") String emailAddress) {
        return null;
    }



}
