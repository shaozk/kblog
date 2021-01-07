package org.example.blog.controller.portal;

import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {
    public ResponseResult doSearch(@RequestParam("keywords") String keywords, @RequestParam("page") int page) {
        return null;
    }
}
