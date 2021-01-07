package org.example.blog.controller.portal;

import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        return null;
    }

    @GetMapping("/list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleByCategoryId(@RequestParam("categoryId") String categoryId,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        return null;
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArcitleDetial(@RequestParam("articleId") String articleId) {
        return null;
    }

    @GetMapping("/recommend/{articleId}")
    public ResponseResult getRecommendArticles(@RequestParam("articleId") String articleId) {
        return null;
    }


}
