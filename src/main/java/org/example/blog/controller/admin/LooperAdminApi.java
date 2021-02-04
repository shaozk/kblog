package org.example.blog.controller.admin;

import org.example.blog.pojo.Looper;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.ILoopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/loop")
public class LooperAdminApi {

    @Autowired
    private ILoopService loopService;

    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper) {
        return loopService.addLoop(looper);
    }

    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLoop(@PathVariable("looperId") String looperId) {
        return loopService.deleteLoop(looperId);
    }

    @PutMapping("/{looperId}")
    public ResponseResult updateLoop(@PathVariable("looperId") String looperId, @RequestBody Looper looper) {
        return loopService.updateLoop(looperId, looper);
    }

    @GetMapping("/{looperId}")
    public ResponseResult getLoop(@PathVariable("looperId") String looperId) {
        return loopService.getLoop(looperId);
    }

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listLoops(@PathVariable("page") int page, @PathVariable("size") int size) {
        return loopService.listLoops(page, size);
    }
}
