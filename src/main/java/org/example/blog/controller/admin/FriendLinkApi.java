package org.example.blog.controller.admin;

import org.example.blog.pojo.FriendLink;
import org.example.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkApi {
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return null;
    }

    @DeleteMapping("/{friendId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendId") String friendId) {
        return null;
    }

    @PutMapping("/{friendId}")
    public ResponseResult updateFriendLink(@PathVariable("friendId") String friendId) {
        return null;
    }

    @GetMapping("/{friendId}")
    public ResponseResult getFriendLink(@PathVariable("friendId") String friendId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listFriendLink(@PathVariable("page") int page, @PathVariable("size") int size) {
        return null;
    }

}
