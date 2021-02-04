package org.example.blog.services.impl;

import org.example.blog.dao.SettingDao;
import org.example.blog.pojo.Setting;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IWebSizeInfoService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class WebSizeInfoImpl implements IWebSizeInfoService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SettingDao settingDao;


    @Override
    public ResponseResult getWebSizeTitle() {
        Setting title = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_TITLE);
        return ResponseResult.SUCCESS().setData(title);
    }

    @Override
    public ResponseResult putWebSizeTitle(String title) {
        if (TextUtil.isEmpty(title)) {
            return ResponseResult.FAILED("网站Title不可以为空");
        }
        Setting titleFromDb = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_TITLE);
        if (titleFromDb == null) {
            titleFromDb = new Setting();
            titleFromDb.setId(idWorker.nextId() + "");
            titleFromDb.setUpdateTime(new Date());
            titleFromDb.setCreateTime(new Date());
            titleFromDb.setKey(Constants.Setting.WEB_SIZE_TITLE);
        }
        titleFromDb.setValue(title);
        settingDao.save(titleFromDb);
        return ResponseResult.SUCCESS("网站Title更新成功");
    }

    @Override
    public ResponseResult getSeoInfo() {
        Setting description = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_DESCRIPTION);
        Setting keywords = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_KEYWORDS);
        Map<String, String> result = new HashMap<>();
        result.put(description.getKey(), description.getValue());
        result.put(keywords.getKey(), keywords.getValue());
        return ResponseResult.SUCCESS("获取SEO信息成功").setData(result);
    }

    @Override
    public ResponseResult putSeoInfo(String keywords, String description) {
        // 判空
        if (TextUtil.isEmpty(keywords)) {
            return ResponseResult.FAILED("关键字不能为空");
        }
        if (TextUtil.isEmpty(description)) {
            return ResponseResult.FAILED("网站描述不能为空");
        }
        Setting descriptionFromDb = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_DESCRIPTION);
        if (descriptionFromDb == null) {
            descriptionFromDb = new Setting();
            descriptionFromDb.setId(idWorker.nextId() + "");
            descriptionFromDb.setCreateTime(new Date());
            descriptionFromDb.setUpdateTime(new Date());
            descriptionFromDb.setKey(Constants.Setting.WEB_SIZE_DESCRIPTION);
        }
        descriptionFromDb.setValue(description);
        settingDao.save(descriptionFromDb);

        Setting keyWordsFromDb = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_KEYWORDS);
        if (keyWordsFromDb == null) {
            keyWordsFromDb = new Setting();
            keyWordsFromDb.setId(idWorker.nextId() + "");
            keyWordsFromDb.setCreateTime(new Date());
            keyWordsFromDb.setUpdateTime(new Date());
            keyWordsFromDb.setKey(Constants.Setting.WEB_SIZE_KEYWORDS);
        }
        keyWordsFromDb.setValue(keywords);
        settingDao.save(keyWordsFromDb);
        return ResponseResult.SUCCESS("更新SEO信息成功");

    }

    @Override
    public ResponseResult getSizeViewCount() {
        Setting viewCountFromDb = settingDao.findOneByKey(Constants.Setting.WEB_SIZE_VIEW_COUNT);
        if (viewCountFromDb == null) {
            viewCountFromDb = new Setting();
            viewCountFromDb.setId(idWorker.nextId() + "");
            viewCountFromDb.setKey(Constants.Setting.WEB_SIZE_VIEW_COUNT);
            viewCountFromDb.setUpdateTime(new Date());
            viewCountFromDb.setCreateTime(new Date());
            viewCountFromDb.setValue("1");
        }
        Map<String, Integer> result = new HashMap<>();
        result.put(viewCountFromDb.getKey(), Integer.valueOf(viewCountFromDb.getValue()));
        return ResponseResult.SUCCESS("获取网站浏览量成功").setData(result);
    }
}
