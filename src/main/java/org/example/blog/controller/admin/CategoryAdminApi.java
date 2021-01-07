package org.example.blog.controller.admin;

import org.example.blog.pojo.Category;
import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 管理中心，分类的API
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {


    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return null;
    }

    /**
     * 删除分类
     *
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    /**
     * 更新分类
     *
     * @param categoryId
     * @param category
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category ) {
        return null;
    }


    /**
     * 获取分类
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    /**
     * 获取分类列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listCategory(@PathVariable("page") int page, @PathVariable("size") int size) {
        return null;
    }

}
