package org.example.blog.services.impl;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.SettingDao;
import org.example.blog.dao.UserDao;
import org.example.blog.pojo.Setting;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IUserService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.RedisUtil;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
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


    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {

        // 检查是否有初始化
        Setting managerAccountState = settingDao.findOneByKey(Constants.Setting.HAS_MANAGER_ACCOUNT_STATE);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("管理员账号已近初始化");
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
        log.info("remoteAddr == > " + remoteAddr);
        log.info("localAddr == > " + localAddr);
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
        log.info("captcha ==> " + index);
        targetCaptcha.setFont(captcha_font_types[index]);
        targetCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = targetCaptcha.text().toLowerCase();
        log.info("captcha content ==> " + content);
        // 保存到redis
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 60 * 10);
        targetCaptcha.out(response.getOutputStream());


    }


}
