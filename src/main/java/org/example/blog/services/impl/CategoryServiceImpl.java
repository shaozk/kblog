package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.CategoryDao;
import org.example.blog.pojo.Category;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.ICategoryService;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public ResponseResult addCategory(Category category) {
        // 先检查数据
        // 必须要有的数据有：
        // 分类名称、分类的pinyin、顺序、描述
        if (TextUtil.isEmpty(category.getName())) {
            return ResponseResult.FAILED("分类名称不可以为空");
        }
        if (TextUtil.isEmpty(category.getPinyin())) {
            return ResponseResult.FAILED("分类拼音不可以为空");
        }
        if (TextUtil.isEmpty(category.getDescription())) {
            return ResponseResult.FAILED("分类描述不可以为空");
        }

        // 补全数据
        category.setId(idWorker.nextId() + "");
        category.setStatus("1");
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        // 保存数据
        categoryDao.save(category);
        // 返回结果
        return ResponseResult.SUCCESS("添加分类成功");
    }

    @Override
    public ResponseResult getCategory(Category category) {
        return null;
    }
}
