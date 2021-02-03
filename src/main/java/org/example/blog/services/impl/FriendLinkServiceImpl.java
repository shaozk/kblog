package org.example.blog.services.impl;

import org.example.blog.dao.FriendLinkDao;
import org.example.blog.pojo.FriendLink;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IFriendLinkService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class FriendLinkServiceImpl implements IFriendLinkService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public ResponseResult addFriendLink(FriendLink friendLink) {
        // 判断数据
        String url = friendLink.getUrl();
        if (TextUtil.isEmpty(url)) {
            return ResponseResult.FAILED("链接url不可以为空");
        }
        String logo = friendLink.getLogo();
        if (TextUtil.isEmpty(logo)) {
            return ResponseResult.FAILED("logo不可以为空");
        }
        String name = friendLink.getName();
        if (TextUtil.isEmpty(name)) {
            return ResponseResult.FAILED("对方网站名不可以为空");
        }

        // 补全数据
        friendLink.setId(idWorker.nextId() + "");
        friendLink.setState("1");
        friendLink.setUpdateTime(new Date());
        friendLink.setCreateTime(new Date());

        // 保存数据
        friendLinkDao.save(friendLink);

        // 返回结果
        return ResponseResult.SUCCESS("友情链接添加成功");

    }

    @Override
    public ResponseResult getFriendLink(String friendLinkId) {
        FriendLink friendLink = friendLinkDao.findOneById(friendLinkId);
        if (friendLink == null) {
            return ResponseResult.FAILED("友情链接不存在");
        }
        return ResponseResult.SUCCESS("获取友情链接成功").setData(friendLink);
    }

    @Override
    public ResponseResult listFriendLinks(int page, int size) {
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        // 创建条件
        Sort sort = new Sort(Sort.Direction.DESC, "createTime", "orde");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<FriendLink> all = friendLinkDao.findAll(pageable);
        return ResponseResult.SUCCESS("获取友情链接列表成功").setData(all);
    }

    @Override
    public ResponseResult deleteFriendLink(String friendLinkId) {
        int result = friendLinkDao.deleteAllById(friendLinkId);
        if (result == 0) {
            return ResponseResult.FAILED("友情链接不存在");
        }
        return ResponseResult.SUCCESS("友情链接删除成功");
    }

    @Override
    public ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink) {
        FriendLink friendLinkFromDb = friendLinkDao.findOneById(friendLinkId);
        // 判空
        if (friendLinkFromDb == null) {
            return ResponseResult.FAILED("友情链接不存在");
        }
        // 更新数据
        String logo = friendLink.getLogo();
        if (!TextUtil.isEmpty(logo)) {
            friendLinkFromDb.setLogo(logo);
        }
        String name = friendLink.getName();
        if (!TextUtil.isEmpty(name)) {
            friendLinkFromDb.setName(name);
        }
        String url = friendLink.getUrl();
        if (!TextUtil.isEmpty(url)) {
            friendLinkFromDb.setUrl(url);
        }
        // 保存新数据
        friendLinkDao.save(friendLinkFromDb);
        // 返回结果
        return ResponseResult.SUCCESS("友情链接更新成功");

    }
}
