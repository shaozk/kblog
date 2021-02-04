package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.LoopDao;
import org.example.blog.pojo.Looper;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.ILoopService;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
public class LoopServiceImpl extends BaseService implements ILoopService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private LoopDao loopDao;

    @Override
    public ResponseResult addLoop(Looper looper) {
        // 检查数据
        String title = looper.getTitle();
        if (TextUtil.isEmpty(title)) {
            return ResponseResult.FAILED("标题不能为空");
        }
        String imageUrl = looper.getImageUrl();
        if(TextUtil.isEmpty(imageUrl)) {
            return ResponseResult.FAILED("图片不能为空");
        }
        String targetUrl = looper.getTargetUrl();
        if(TextUtil.isEmpty(targetUrl)) {
            return ResponseResult.FAILED("跳转链接不能为空");
        }

        // 补充数据
        looper.setId(idWorker.nextId() + "");
        looper.setState("1");
        looper.setCreateTime(new Date());
        looper.setUpdateTime(new Date());

        // 保存数据
        loopDao.save(looper);

        // 返回结果
        return ResponseResult.SUCCESS("添加轮播图成功");
    }

    @Override
    public ResponseResult deleteLoop(String looperId) {
        int result = loopDao.deleteAllById(looperId);
        if (result == 0) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        return ResponseResult.SUCCESS("删除成功");
    }

    @Override
    public ResponseResult updateLoop(String looperId, Looper looper) {
        // 找到
        Looper looperFromDb = loopDao.findOneById(looperId);
        if (looperFromDb == null) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        // 判空
        String title = looper.getTitle();
        if (!TextUtil.isEmpty(title)) {
            looperFromDb.setTitle(title);
        }
        String targetUrl = looper.getTargetUrl();
        if (!TextUtil.isEmpty(targetUrl)) {
            looperFromDb.setTargetUrl(targetUrl);
        }
        String imageUrl = looper.getImageUrl();
        if (!TextUtil.isEmpty(imageUrl)) {
            looperFromDb.setImageUrl(imageUrl);
        }
        String state = looper.getState();
        if (!TextUtil.isEmpty(state)) {
            looperFromDb.setState(state);
        }
        // 补充数据
        looperFromDb.setOrder(looper.getOrder());
        looperFromDb.setUpdateTime(new Date());

        // 保存数据
        loopDao.save(looperFromDb);

        // 返回结果
        return ResponseResult.SUCCESS("轮播图更新成功");
    }

    @Override
    public ResponseResult getLoop(String looperId) {
        Looper looper = loopDao.findOneById(looperId);
        if (looper == null) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        return ResponseResult.SUCCESS("获取轮播图成功").setData(looper);
    }

    @Override
    public ResponseResult listLoops(int page, int size) {
        page = checkPage(page);
        size = checkSize(size);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Looper> all = loopDao.findAll(pageable);
        return ResponseResult.SUCCESS("获取轮播图列表成功").setData(all);
    }
}
