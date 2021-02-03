package org.example.blog.controller.admin;

import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/image")
public class ImageAdminApi {

    @PostMapping
    public ResponseResult uploadImage() {
        return null;
    }

    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listImages(@PathVariable("page") int page, @PathVariable("size") int size) {
        return null;
    }



}
