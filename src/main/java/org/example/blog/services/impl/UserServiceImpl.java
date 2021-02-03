package org.example.blog.services.impl;

import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.RefreshTokenDao;
import org.example.blog.dao.SettingDao;
import org.example.blog.dao.UserDao;
import org.example.blog.pojo.RefreshToken;
import org.example.blog.pojo.Setting;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.response.ResponseState;
import org.example.blog.services.IUserService;
import org.example.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SettingDao settingDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Random random;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TaskService taskService;

    @Autowired
    private Gson gson;

    @Autowired
    private RefreshTokenDao refreshTokenDao;


    public static final int[] captcha_font_types = {
            Captcha.FONT_1,
            Captcha.FONT_2,
            Captcha.FONT_3,
            Captcha.FONT_4,
            Captcha.FONT_5,
            Captcha.FONT_6,
            Captcha.FONT_7,
            Captcha.FONT_8,
            Captcha.FONT_9,
            Captcha.FONT_10,
    };

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    private HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {

        // 检查是否有初始化
        Setting managerAccountState = settingDao.findOneByKey(Constants.Setting.HAS_MANAGER_ACCOUNT_STATE);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("管理员账号已经初始化");
        }

        // 检查数据
        if (TextUtil.isEmpty(user.getUserName())) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (TextUtil.isEmpty(user.getPassword())) {
            return ResponseResult.FAILED("密码不能为空");
        }
        if (TextUtil.isEmpty(user.getEmail())) {
            return ResponseResult.FAILED("邮箱不能为空");
        }

        // 补充数据
        user.setId(String.valueOf(idWorker.nextId()));
        user.setRoles(Constants.User.ROLE_ADMIN);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setState(Constants.User.DEFAULT_STATE);
        String remoteAddr = request.getRemoteAddr();
        String localAddr = request.getLocalAddr();
        user.setLoginIp(remoteAddr);
        user.setRegIp(remoteAddr);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        // 对密码进行加密
        String password = user.getPassword();
        String encode = bCryptPasswordEncoder.encode(password);
        user.setPassword(encode);

        // 保存到数据库
        userDao.save(user);

        // 更新已经添加的标记
        Setting setting = new Setting();
        setting.setId(idWorker.nextId() + "");
        setting.setKey(Constants.Setting.HAS_MANAGER_ACCOUNT_STATE);
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        setting.setValue("1");
        settingDao.save(setting);
        return ResponseResult.SUCCESS("初始化成功");
    }

    /**
     * 创建图灵验证码
     * （人类验证码）
     * @param response
     * @param captchaKey
     * @throws Exception
     */
    @Override
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception{
        if(TextUtil.isEmpty(captchaKey) || captchaKey.length() < 13) {
            return;
        }
        long key;
        try {
            key = Long.parseLong(captchaKey);
        } catch(Exception e) {
            return;
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int captchaType = random.nextInt(3);
        Captcha targetCaptcha;
        if(captchaType == 0){
            targetCaptcha = new SpecCaptcha(200, 60, 5);
        } else if(captchaType == 1) {
            // gif
            targetCaptcha = new GifCaptcha(200, 60);
        } else {
            // 算数
            targetCaptcha = new ArithmeticCaptcha(200, 60);
            targetCaptcha.setLen(2);    // 两位数运算
        }
        int index = random.nextInt(captcha_font_types.length);
        targetCaptcha.setFont(captcha_font_types[index]);
        targetCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = targetCaptcha.text().toLowerCase();
        log.info("图灵验证码 ==> " + content);
        // 保存到redis
        // 删除时机：
        // 1、自然删除，也就是10分钟后自己删除
        // 2、
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 60 * 10);
        targetCaptcha.out(response.getOutputStream());

    }

    @Override
    public ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress) {
        if (emailAddress == null) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        // 根据类型查询邮箱是否存在
        if("register".equals(type) || "update".equals(type)) {
            User userByEmail = userDao.findOneByEmail(emailAddress);
            if (userByEmail != null) {
                return ResponseResult.FAILED("该邮箱已注册");
            }
        } else if("forget".equals(type)) {
            User userByEmail = userDao.findOneByEmail(emailAddress);
            if (userByEmail == null) {
                return ResponseResult.FAILED("该邮箱未注册");
            }
        }
        // 1.防止不断发送
        // 同一个邮箱，间隔要超过30秒，同一个ip1小时内最多只能发10次（如果时短信，最多5次）
        String remoteAddr =request.getRemoteAddr();
        log.info("sendEmail ==> " + remoteAddr);
        if (remoteAddr != null) {
            remoteAddr = remoteAddr.replaceAll(":","_");
        }
        // 拿出来，如果没有，那就过了
        log.info("remoteAddr ==> " + Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        Integer ipSendTime = (Integer) redisUtil.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if(ipSendTime != null && ipSendTime > 10) {
            return ResponseResult.FAILED("您发送验证码太频繁了！");
        }
        Object hasEmailSend = redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (hasEmailSend != null) {
            return ResponseResult.FAILED("你发送验证码也太频繁了吧！");
        }

        // 2.检查邮箱地址是否正确
        boolean isEmailFormatOk = TextUtil.isEmailAddressOk(emailAddress);
        if (!isEmailFormatOk) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }

        // 0 ~ 999999
        int code = random.nextInt(999999);
        if(code < 100000) {
            code += 100000;
        }
        log.info("code ==> " + code);
        // 3.发送验证码,6位数，100000~999999
        try {
            taskService.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            return ResponseResult.FAILED("验证码发送失败，请稍后重发");
        }
        // 4.做记录
        if (ipSendTime == null) {
            ipSendTime = 0;
        }
        ipSendTime++;
        // 1个小时有效期
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime, 60 * 60);
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "send", 30);
        // 保存code，十分钟内有效
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT + emailAddress, String.valueOf(code), 60 * 10);
        return ResponseResult.SUCCESS("验证码发送成功");
    }

    @Override
    public ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request) {
        // 1.检查当前用户名是否已经注册
        String userName = user.getUserName();
        if (TextUtil.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        User userByName = userDao.findOneByUserName(userName);
        if (userByName != null) {
            return ResponseResult.FAILED("该用户名已经注册");
        }
        // 2.检查邮箱格式是否正确
        String email = user.getEmail();
        if (TextUtil.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (!TextUtil.isEmailAddressOk(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }
        // 3.检查邮箱是否已经注册
        User userByEmail = userDao.findOneByEmail(email);
        if (userByEmail != null) {
            return ResponseResult.FAILED("该邮箱地址已经注册");
        }
        // 4.检查邮箱验证码是否正确
        String emailVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (TextUtil.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILED("邮箱验证码无效");
        }
        if(!emailVerifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码不正确");
        } else {
            // 正确，干掉redis的内容
            redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT);
        }

        // 5.检查图灵验证吗是否正确
        String captchaVerifyCode = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (TextUtil.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("人类验证码已过期");
        }
        if(!captchaVerifyCode.equals(captchaVerifyCode)) {
            return ResponseResult.FAILED("人类验证码不正确");
        } else {
            redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        }
        // 达到可以注册的条件
        // 6.对密码进行加密
        String password = user.getPassword();
        if (TextUtil.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // 7.补全数据（注册IP,登陆IP，角色，头像，创建时间，更新时间）
        String ipAddress = request.getRemoteAddr();
        user.setRegIp(ipAddress);
        user.setLoginIp(ipAddress);
        user.setId(idWorker.nextId() + "");
        user.setState("1");
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setRoles(Constants.User.ROLE_NORMAL);

        // 8.保存到数据库
        userDao.save(user);

        // 9.返回结果
        return ResponseResult.GET(ResponseState.JOIN_IN_SUCCESS);

        /*
        注册流程：
        1.前段获取图灵验证码
        localhost:2021/user/captcha?captcha_key=3815825449258
        图灵验证码：brczp

        2.输入邮箱地址，获取邮箱验证码
        localhost:2021/user/verify_code?email=913667678@qq.com&type=register
        邮箱验证码：596035

        3.注册提交（body：用户名、用户邮箱、用户密码；参数：图灵验证码的key、图灵验证码、邮箱验证码）
        localhost:2021/user?captcha_key=3815825449258&captcha_code=brczp&email_code=596035

         */

    }

    @Override
    public ResponseResult doLogin(String captcha, String captchaKey, User user,
                                  HttpServletRequest request, HttpServletResponse response) {
        String captchaValue = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (!captcha.equals(captchaValue)) {
            return ResponseResult.FAILED("人类验证码不正确");
        }
        // 验证成功，删除redis里的验证码
        redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);

        // 有可能是邮箱，也有可能是用户名
        String userName = user.getUserName();
        if (TextUtil.isEmpty(userName)) {
            return ResponseResult.FAILED("账号不能为空");
        }
        String password = user.getPassword();
        if (TextUtil.isEmpty(password)) {
            return ResponseResult.FAILED("密码不可以为空");
        }

        User userFromDb = userDao.findOneByUserName(userName);
        if (userFromDb == null) {
            userFromDb = userDao.findOneByEmail(userName);
        }
        if (userFromDb == null) {
            return ResponseResult.FAILED("用户名或密码不正确");
        }
        // 用户存在，对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userFromDb.getPassword());
        if (!matches) {
            return ResponseResult.FAILED("用户名或密码不正确");
        }
        // 密码是正确的
        // 判断用户状态，如果是非正常状态，则返回结果
        if (!"1".equals(userFromDb.getState())) {
            return ResponseResult.FAILED("当前账号已被禁止");
        }
        createToken(response, userFromDb);

        return ResponseResult.SUCCESS("登陆成功");
    }

    /**
     *
     * @param response
     * @param userFromDb
     * @return token_key
     */
    private String createToken(HttpServletResponse response, User userFromDb) {
        int deleteResult = refreshTokenDao.deleteAllByUserId(userFromDb.getId());
        log.info("deleteResult --> " + deleteResult);
        // 生成token
        Map<String, Object> claims = ClaimsUtil.user2Claims(userFromDb);
        // token默认有效3小时
        String token = JwtUtil.createToken(claims);

        // 返回token的Md5码，token保存在redis里
        // 前端访问的时候，携带token的md5key，从redis中获取即可
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());

        // 保存token到redis里，有效期为2小时，key是tokenKey
        redisUtil.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeValue.HOUR_2);

        // 把tokenKey写到cookies里
        // 这个要动态获取，可以从request里获取
        CookieUtil.setUpCookie(response, Constants.User.COOKIE_KEY_TOKEN, tokenKey);

        // 生成refreshToken,保存一个月
        String refreshTokenValue = JwtUtil.createRefreshToken(userFromDb.getId(), Constants.TimeValue.MONTH);

        // 保存在数据库里
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(idWorker.nextId() + "");
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUserId(userFromDb.getId());
        refreshToken.setTokenKey(tokenKey);
        refreshToken.setCreateTime(new Date());
        refreshToken.setUpdateTime(new Date());
        refreshTokenDao.save(refreshToken);
        return tokenKey;
    }

    private User parseByTokenKey(String tokenKey){
        String token = (String) redisUtil.get(Constants.User.KEY_TOKEN + tokenKey);
        if (tokenKey != null) {
            try {
                Claims claims = JwtUtil.parseJWT(token);
                return ClaimsUtil.claims2User(claims);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public User checkUser() {
        // 拿到token_key
        String tokenKey = CookieUtil.getCookie(getRequest(), Constants.User.COOKIE_KEY_TOKEN);
        User user = parseByTokenKey(tokenKey);
        if (user == null) {
            // 解析出错
            // 1.去mysql查询refreshToken
            RefreshToken refreshToken = refreshTokenDao.findOneByTokenKey(tokenKey);
            // 2.如果不存在，就是当前访问没有登陆
            if (refreshToken == null) {
                return null;
            }
            // 3.如果存在，就解析refreshToken
            try {
                JwtUtil.parseJWT(refreshToken.getRefreshToken());
                // 5.如果refreshToken有效，创建新的token，和新的refreshToken
                String userId = refreshToken.getUserId();
                User userFromDb = userDao.findOneById(userId);
                // 删掉refreshToken的记录
                refreshTokenDao.deleteById(refreshToken.getId());
                String newTokenKey = createToken(getResponse(), userFromDb);
                // 返回token
                return parseByTokenKey(newTokenKey);

            }catch (Exception e1) {
                // 4.如果refreshToken过期了，当前访问没有登陆，提示用户登陆
                return null;
            }
        }

        return user;

    }

    @Override
    public ResponseResult getUserInfo(String userId) {
        // 从数据库获取
        User userById = userDao.findOneById(userId);
        // 判断结果
        if (userById == null) {
            return ResponseResult.FAILED("用户不存在");
        }
        // 复制对象，注意要清空密码及其他一些隐私信息
        String userJson = gson.toJson(userById);
        User newUser = gson.fromJson(userJson, User.class);
        newUser.setPassword("");
        newUser.setEmail("");
        newUser.setRegIp("");
        newUser.setLoginIp("");

        // 返回结果
        return ResponseResult.SUCCESS("获取成功").setData(userById);
    }

    @Override
    public ResponseResult checkEmail(String email) {
        User user = userDao.findOneByEmail(email);
        return user == null ? ResponseResult.FAILED("该邮箱未注册") : ResponseResult.SUCCESS("该邮箱已经注册");
    }

    @Override
    public ResponseResult checkUserName(String userName) {
        User user = userDao.findOneByUserName(userName);
        return user == null ? ResponseResult.FAILED("该用户名未注册") : ResponseResult.SUCCESS("该用户名已经注册");
    }

    @Override
    public ResponseResult updateUserInfo(String userId, User user) {
        // 从token中获取，为了校验权限
        // 只有用户才可以修改自己的信息
        User userFromTokenKey = checkUser();
        if (userFromTokenKey == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        User userFromDb = userDao.findOneById(userFromTokenKey.getId());
        log.info("当前用户ID ==> " + userFromTokenKey.getId());
        log.info("用户ID ==> " + user.getId());

        // 判断用户的ID是否一致，如果一致才可以修改
        if (!userFromDb.getId().equals(userId)) {
            return ResponseResult.FAILED("无权限修改");
        }

        // 可以进行修改
        // 用户名
        String userName = user.getUserName();
        if (!TextUtil.isEmpty(user.getUserName())) {
            User userByUserName = userDao.findOneByUserName(userName);
            if (userByUserName != null) {
                return ResponseResult.FAILED("该用户名已注册");
            }
            userFromDb.setUserName(userName);
        }
        // 头像
        if (!TextUtil.isEmpty(user.getAvatar())) {
            userFromDb.setAvatar(user.getAvatar());
        }
        // 签名，可以为空
        userFromDb.setSign(user.getSign());

        // 保存到数据库
        userDao.save(userFromDb);
        return ResponseResult.SUCCESS("用户信息更新成功");

    }

    @Override
    public ResponseResult doLogout() {
        // 拿到token_key,判断账号是否登录
        String tokenKey = CookieUtil.getCookie(getRequest(), Constants.User.COOKIE_KEY_TOKEN);
        if (TextUtil.isEmpty(tokenKey)) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        // 删除redis里的token
        redisUtil.del(Constants.User.KEY_TOKEN + tokenKey);
        // 删除mysql里的refreshToken
        refreshTokenDao.deleteAllByTokenKey(tokenKey);
        // 删除cookie里的token_key
        CookieUtil.deleteCookie(getResponse(), Constants.User.COOKIE_KEY_TOKEN);
        return ResponseResult.SUCCESS("退出登录成功");
    }

    @Override
    public ResponseResult updateEmail(String email, String verifyCode) {
        // 1、确保用户已经登录了
        User user = this.checkUser();
        // 没有登录
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        // 2、对比验证码，确保新的邮箱地址是属于当前用户的
        String redisVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if(TextUtil.isEmpty(redisVerifyCode) || !redisVerifyCode.equals(verifyCode)) {
            return ResponseResult.FAILED("验证码错误");
        }
        // 验证码正确，删除验证码
        redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        // 可以修改邮箱
        int result = userDao.updateEmailById(email, user.getId());
        return result > 0 ? ResponseResult.SUCCESS("邮箱修改成功") : ResponseResult.FAILED("邮箱修改失败");
    }

    @Override
    public ResponseResult updateUserPassword(String verifyCode, User user) {
        // 检查邮箱是否有填写
        String email = user.getEmail();
        if (TextUtil.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱不可以为空");
        }
        // 根据邮箱去redis里拿验证
        // 进行对比
        String redisVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (redisVerifyCode == null || !redisVerifyCode.equals(verifyCode)) {
            return ResponseResult.FAILED("验证码错误");
        }
        redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        int result = userDao.updatePasswordByEmail(bCryptPasswordEncoder.encode(user.getPassword()), email);
        // 修改密码
        return result > 0 ? ResponseResult.SUCCESS("密码修改成功") : ResponseResult.FAILED("密码修改失败");

    }

    @Override
    public ResponseResult deleteUserById(String userId) {
        // 可以删除了
        int result = userDao.deleteUserByState(userId);
        if (result > 0) {
            return ResponseResult.SUCCESS("删除成功");
        }

        return null;
    }

    @Override
    public ResponseResult listUser(int page, int size) {
        // 检验当前操作的用户是谁
        User curUser = checkUser();
        if (curUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        // 判断角色
        if(!Constants.User.ROLE_ADMIN.equals(curUser.getRoles())) {
            return ResponseResult.PERMISSION_DENIED();
        }
        // 可以获取用户列表
        // 分页查询
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        // size也限制一下，每一页不能少于五个
        if(size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }

        // 根据注册日期来排序
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<User> all = userDao.findAll(pageable);
        return ResponseResult.SUCCESS("获取用户列表成功").setData(all);

    }


}
