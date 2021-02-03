package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.CategoryDao;
import org.example.blog.pojo.Category;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.ICategoryService;
import org.example.blog.utils.Constants;
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
    public ResponseResult getCategory(String categoryId) {
        Category category = categoryDao.findOneById(categoryId);
        if (category == null) {
            return ResponseResult.FAILED("分类不存在");
        }
        return ResponseResult.SUCCESS("获取分类成功").setData(category);
    }

    @Override
    public ResponseResult listCategories(int page,int size) {
        // 参数检查
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        // 创建条件
        Sort sort = new Sort(Sort.Direction.DESC, "createTime", "order");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        // 查询
        Page<Category> all = categoryDao.findAll(pageable);
        // 返回结果
        return ResponseResult.SUCCESS("获取分类列表成功").setData(all);

    }

    @Override
    public ResponseResult deleteCategory(String categoryId) {
        int result = categoryDao.deleteCategoryByUpdateState(categoryId);
        if (result == 0) {
            return ResponseResult.FAILED("分类不存在");
        }
        return ResponseResult.SUCCESS("删除分类成功");
    }

    @Override
    public ResponseResult updateCategory(String categoryId, Category category) {
        // 找出需要更新的数据
        Category categoryFromDb = categoryDao.findOneById(categoryId);
        if (categoryFromDb == null) {
            return ResponseResult.FAILED("分类不存在");
        }
        // 对更新内容进行判断，有些字段不允许为空
        String name = category.getName();
        if (!TextUtil.isEmpty(name)) {
            categoryFromDb.setName(name);
        }
        String pinyin = category.getPinyin();
        if (!TextUtil.isEmpty(pinyin)) {
            categoryFromDb.setPinyin(pinyin);
        }
        String description = category.getDescription();
        if (!TextUtil.isEmpty(description)) {
            categoryFromDb.setDescription(description);
        }
        // 保存数据
        categoryDao.save(categoryFromDb);

        // 返回结果
        return ResponseResult.SUCCESS("更新分类成功");
    }
}
