package org.example.blog.services;

import org.example.blog.pojo.Article;
import org.example.blog.response.ResponseResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


public interface IArticleService {

    ResponseResult postArticle(Article article);
}
