package org.example.blog.services;

import org.example.blog.pojo.Looper;
import org.example.blog.response.ResponseResult;

public interface ILoopService {

    /**
     * 添加轮播图
     * @param looper
     */
    ResponseResult addLoop(Looper looper);

    /**
     * 删除轮播图
     * @param looperId
     * @return
     */
    ResponseResult deleteLoop(String looperId);

    /**
     * 更新轮播图
     * @param looperId
     * @return
     */
    ResponseResult updateLoop(String looperId, Looper looper);

    /**
     * 获取轮播图
     * @param looperId
     * @return
     */
    ResponseResult getLoop(String looperId);

    /**
     * 获取轮播图列表
     * @param page
     * @param size
     * @return
     */
    ResponseResult listLoops(int page, int size);
}
