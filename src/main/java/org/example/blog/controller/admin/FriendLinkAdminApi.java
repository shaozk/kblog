package org.example.blog.controller.admin;

import org.example.blog.pojo.FriendLink;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IFriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkAdminApi {

    @Autowired
    private IFriendLinkService friendLinkService;

    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.addFriendLink(friendLink);
    }

    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{friendId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendId") String friendId) {
        return friendLinkService.deleteFriendLink(friendId);
    }

    @PreAuthorize("@permission.admin()")
    @PutMapping("/{friendId}")
    public ResponseResult updateFriendLink(@PathVariable("friendId") String friendId, @RequestBody FriendLink friendLink) {
        return friendLinkService.updateFriendLink(friendId, friendLink);
    }

    @PreAuthorize("@permission.admin()")
    @GetMapping("/{friendId}")
    public ResponseResult getFriendLink(@PathVariable("friendId") String friendId) {
        return friendLinkService.getFriendLink(friendId);
    }

    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listFriendLink(@PathVariable("page") int page, @PathVariable("size") int size) {
        return friendLinkService.listFriendLinks(page, size);
    }

}
