package org.example.blog.services;

import org.example.blog.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IImageService {

    /**
     * 上传图片
     * @param file
     * @return
     */
    ResponseResult uploadImage(MultipartFile file);

    /**
     * 获取图片
     * @param imageId
     * @return
     */
    void viewImage(HttpServletResponse response, String imageId);

    /**
     * 获取图片列表
     * @param page
     * @param size
     * @return
     */
    ResponseResult listImages(int page, int size);

    /**
     * 删除图片
     * @param imageId
     * @return
     */
    ResponseResult deleteImageById(String imageId);
}
