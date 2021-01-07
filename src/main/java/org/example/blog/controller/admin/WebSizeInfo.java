package org.example.blog.controller.admin;

import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfo {

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return null;
    }

    @PutMapping("/title")
    public ResponseResult upWebSizeTitle(@RequestParam("title") String title) {
        return null;
    }
    @GetMapping("/seo")
    public ResponseResult getSeoInfo() {
        return null;
    }

    @PutMapping("/seo")
    public ResponseResult putSeoInfo(@RequestParam("keywords") String keywords,
                                     @RequestParam("description") String description) {
        return null;
    }

    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount() {
        return null;
    }





}
