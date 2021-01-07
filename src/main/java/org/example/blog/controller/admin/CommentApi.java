package org.example.blog.controller.admin;

import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/comment")
public class CommentApi {

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteImage(@PathVariable("commentId") String commentId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listImages(@PathVariable("page") int page, @PathVariable("size") int size) {
        return null;
    }

    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId) {
        return null;
    }
}
