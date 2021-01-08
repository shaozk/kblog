package org.example.blog.controller.user;


import lombok.extern.slf4j.Slf4j;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody User user) {
        // todo:1.检查当前用户名是否已经注册
        // todo:2.检查邮箱格式是否正确
        // todo:3.检查邮箱是否已经注册
        // todo:4.检查邮箱验证码是否正确
        // todo:5.检查图灵验证吗是否正确
        // 达到可以注册的条件
        // todo:6.对密码进行加密
        // todo:7.补全数据（注册IP,登陆IP，角色，头像，创建时间，更新时间）
        // todo:8.保存到数据库
        // todo:9.返回结果

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
     * 有效时长为10分钟
     *
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey) {

        try {
            userService.createCaptcha(response, captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }

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
