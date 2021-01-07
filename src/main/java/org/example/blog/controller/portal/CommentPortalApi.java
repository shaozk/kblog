package org.example.blog.controller.portal;

import org.example.blog.pojo.Comment;
import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal/comment")
public class CommentPortalApi {

    @GetMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return null;
    }

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }

    @GetMapping("/list/{commentId}")
    public ResponseResult listComments(@PathVariable("commentId") String commentId) {
        return null;
    }



}
