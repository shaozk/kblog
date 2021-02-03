package org.example.blog.services;


import org.example.blog.pojo.Category;
import org.example.blog.response.ResponseResult;

public interface ICategoryService {

    /**
     * 添加文章分类
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);

    /**
     * 获取文章分类
     * @param categoryId
     * @return
     */
    ResponseResult getCategory(String categoryId);

    /**
     * 获取文章分类列表
     * @return
     */
    ResponseResult listCategories(int page, int size);

    /**
     * 删除文章分类
     * @param categoryId
     * @return
     */
    ResponseResult deleteCategory(String categoryId);

    /**
     * 更新文章分类
     * @param categoryId
     * @param category
     * @return
     */
    ResponseResult updateCategory(String categoryId, Category category);
}
