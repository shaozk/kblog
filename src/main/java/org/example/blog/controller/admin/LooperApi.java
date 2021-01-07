package org.example.blog.controller.admin;

import org.example.blog.pojo.Looper;
import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/loop")
public class LooperApi {

    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper) {
        return null;
    }

    @DeleteMapping("/{looperId}")
    public ResponseResult deleteImage(@PathVariable("looperId") String looperId) {
        return null;
    }

    @PutMapping("/{looperId}")
    public ResponseResult updateImage(@PathVariable("looperId") String looperId) {
        return null;
    }

    @GetMapping("/{looperId}")
    public ResponseResult getImage(@PathVariable("looperId") String looperId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listImages(@PathVariable("page") int page, @PathVariable("size") int size) {
        return null;
    }
}
