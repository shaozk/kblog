package org.example.blog.controller.user;


import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * 初始化管理员账号
     *
     * @param user
     * @return
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User user, HttpServletRequest request) {
        log.info("userName ==> " + user.getUserName());
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
    @PostMapping("/sign_up")
    public ResponseResult register(@RequestBody User user,
                                   @RequestParam("email_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey,
                                   HttpServletRequest request) {
        return userService.register(user, emailCode, captchaCode, captchaKey, request);
    }

    /**
     * 用户登录
     * <p>需要提交的数据
     * 1.用户账号-可以是昵称，可以是邮箱-->做了唯一处理
     * 2.密码
     * 3.图灵验证码
     * 4.图灵验证的key
     * </p>
     *
     * @param captcha
     * @param user
     * @return
     */
    @PostMapping("/sign_in/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha") String captcha,
                                @PathVariable("captcha_key") String captchaKey,
                                @RequestBody User user,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        return userService.doLogin(captcha, captchaKey, user, request, response);
    }

    /**
     * 获取图灵验证码
     * 有效时长为10分钟
     *
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey) throws Exception {
            userService.createCaptcha(response, captchaKey);
    }

    /**
     * 发送邮件email
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("email") String emailAddress) {
        return userService.sendEmail(type, request, emailAddress);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user_info/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 更新用户信息
     * 1.头像
     * 2.用户名
     * 3.密码（单独修改）
     * 4.签名
     * 5.邮箱（单独修改）
     * @param userId
     * @param user
     * @return
     */
    @PutMapping("/user_info/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUserInfo(userId, user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUserById(userId);
    }

    /**
     * 获取用户列表
     * 权限：管理员
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listUser(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.listUser(page, size);    }

    /**
     * 修改密码
     * 普通修改：通过旧密码对比来更新密码
     * 找回密码：发送验证码到邮箱/手机进行找回密码
     * 步骤：
     * 1、用户填写邮箱
     * 2、用户获取验证码type=forget
     * 3、填写验证码
     * 4、填写新的密码
     * 5、提交数据（数据包括：邮箱和新密码、验证码）
     * @param user
     * @return
     */
    @PutMapping("/password/{verifyCode}")
    public ResponseResult updatePassword(@PathVariable("verifyCode") String verifyCode, @RequestBody User user) {
        return userService.updateUserPassword(verifyCode, user);
    }

    /**
     * 检查邮箱是否已经注册
     * @param email
     * @return SUCCESS --> 已经注册
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "表示当前邮箱已经注册了"),
            @ApiResponse(code = 400, message = "表示当前邮箱未注册")
    })
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    /**
     * 检查用户是否注册
     * @param userName
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "表示用户名已经注册了"),
            @ApiResponse(code = 400, message = "表示用户名未注册")
    })
    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("userName") String userName) {
        return userService.checkUserName(userName);
    }

    /**
     * 更新邮箱
     * <p>
     * 条件：
     * 1、必须已经登录
     * 2、新的邮箱没有注册过
     * <p>
     * 步骤：
     * 1、已经登录
     * 2、输入新的邮箱地址
     * 3、获取验证码type = update
     * 4、输入验证码
     * 5、提交数据
     * </p>
     * 需要提交的数据：
     * 1、新的邮箱地址
     * 2、验证码
     * 3、其他信息（可从token里获取）
     * @param email
     * @param verifyCode
     * @return
     */
    @PutMapping("/email")
    public ResponseResult updateEmail(@RequestParam("email") String email,
                                      @RequestParam("verify_code") String verifyCode) {
        return userService.updateEmail(email, verifyCode);
    }

    /**
     * 退出登录
     * 拿到token_kye
     * > 删除redis里对应的token
     * > 删除mysql里对应的refreshToken
     * > 删除cookie里的token
     * @return
     */
    @GetMapping("/logout")
    public ResponseResult logout() {
        return userService.doLogout();
    }





}
