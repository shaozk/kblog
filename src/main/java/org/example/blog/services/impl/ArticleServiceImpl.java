package org.example.blog.services.impl;

import org.example.blog.dao.ArticleDao;
import org.example.blog.pojo.Article;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IArticleService;
import org.example.blog.services.IUserService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ArticleServiceImpl extends BaseService implements IArticleService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public ResponseResult postArticle(Article article) {
        // 检查用户，获取用户对象
        User user = userService.checkUser();
        // 未登录
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        // 检查数据
        String title = article.getTitle();
        if (TextUtil.isEmpty(title)) {
            return ResponseResult.FAILED("标题不可以为空");
        }
        if (title.length() > Constants.Article.TITLE_MAX_LENGTH) {
            return ResponseResult.FAILED("文章标题不可以超过" + Constants.Article.TITLE_MAX_LENGTH + "个字符");
        }
        String content = article.getContent();
        if (TextUtil.isEmpty(content)) {
            return ResponseResult.FAILED("内容不可以为空");
        }
        String type = article.getType();
        if (TextUtil.isEmpty(type)) {
            return ResponseResult.FAILED("类型不可以为空");
        }
        if(!"0".equals(type) && !"1".equals(type)) {
            return ResponseResult.FAILED("类型格式不正确");
        }
        String summary = article.getSummary();
        if (TextUtil.isEmpty(summary)) {
            return ResponseResult.FAILED("摘要不可以为空");
        }
        if (summary.length() > Constants.Article.SUMMARY_MAX_LENGTH) {
            return ResponseResult.FAILED("摘要不可以超出" + Constants.Article.SUMMARY_MAX_LENGTH + "个字符");
        }
        String labels = article.getLabels();
        if (TextUtil.isEmpty(labels)) {
            return ResponseResult.FAILED("标签不可以为空");
        }

        // 补充数据
        article.setId(idWorker.nextId() + "");
        article.setUserId(user.getId());
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());

        // 保存到数据库
        articleDao.save(article);

        // 返回结果
        return ResponseResult.SUCCESS("文章发表成功");
    }
}
