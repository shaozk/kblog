package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.SettingDao;
import org.example.blog.dao.UserDao;
import org.example.blog.pojo.Setting;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IUserService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

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
}
