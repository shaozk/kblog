package org.example.blog.controller.admin;

import org.example.blog.response.ResponseResult;
import org.example.blog.services.IWebSizeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeAdminInfo {

    @Autowired
    private IWebSizeInfoService webSizeInfoService;

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return webSizeInfoService.getWebSizeTitle();
    }

    @PutMapping("/title")
    public ResponseResult upWebSizeTitle(@RequestParam("title") String title) {
        return webSizeInfoService.putWebSizeTitle(title);
    }
    @GetMapping("/seo")
    public ResponseResult getSeoInfo() {
        return webSizeInfoService.getSeoInfo();
    }

    @PutMapping("/seo")
    public ResponseResult putSeoInfo(@RequestParam("keywords") String keywords,
                                     @RequestParam("description") String description) {
        return webSizeInfoService.putSeoInfo(keywords, description);
    }

    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount() {
        return webSizeInfoService.getSizeViewCount();
    }





}
