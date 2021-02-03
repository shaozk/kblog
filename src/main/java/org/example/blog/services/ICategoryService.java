package org.example.blog.services;


import org.example.blog.pojo.Category;
import org.example.blog.response.ResponseResult;

public interface ICategoryService {

    /**
     * 添加分类
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);

    ResponseResult getCategory(Category category);



}
