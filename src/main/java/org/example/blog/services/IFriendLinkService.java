package org.example.blog.services;


import org.example.blog.pojo.FriendLink;
import org.example.blog.response.ResponseResult;

public interface IFriendLinkService {

    /**
     * 添加友情链接
     * @param friendLink
     * @return
     */
    ResponseResult addFriendLink(FriendLink friendLink);

    /**
     * 获取好友链接
     * @param friendLinkId
     * @return
     */
    ResponseResult getFriendLink(String friendLinkId);

    /**
     * 获取好友链接列表
     * @param page
     * @param size
     * @return
     */
    ResponseResult listFriendLinks(int page, int size);

    /**
     * 删除友情链接
     * @param friendLinkId
     * @return
     */
    ResponseResult deleteFriendLink(String friendLinkId);

    /**
     * 更新友情链接
     * @param friendLinkId
     * @param friendLink
     * @return
     */
    ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink);

}
