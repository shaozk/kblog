package org.example.blog.dao;

import org.example.blog.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryDao  extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

    /**
     * 通过id获取文章分类
     * @param categoryId
     * @return
     */
    Category findOneById(String categoryId);

    /**
     * 通过更新文章状态删除文章分类
     * @param categoryId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_categories` set `state` = '0' where `id` = ?")
    int deleteCategoryByUpdateState(String categoryId);
}
