package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IImageService;
import org.example.blog.utils.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl implements IImageService {

    public static final String imagePath = "E:\\Code\\J2EE\\images";

    @Override
    public ResponseResult uploadImage(MultipartFile file) {
        // 判断文件是否存在
        if (file == null) {
            return ResponseResult.FAILED("图片不可以为空");
        }
        // 判断文件类型，我们只支持图片上传，比如说：png,jpg,gif
        String contType = file.getContentType();
        if (TextUtil.isEmpty(contType)) {
            return ResponseResult.FAILED("图片格式错误");
        }
        if(!"image/png".equals(contType) &&
            !"image/gif".equals(contType) &&
            !"image/jpg".equals(contType)) {
            return ResponseResult.FAILED("不支持此图片类型");
        }

        // 获取图片相关数据，比如文件类型，文件名称
        String name = file.getName();
        String originalFilename = file.getOriginalFilename();
        log.info("name ==> " + name);
        log.info("originalFilename ==> " + originalFilename);

        // 根据我们定的规则进行命名
        File targetFile = new File(imagePath + File.separator + originalFilename);
        log.info("targetFile ==>  " + targetFile);
        // 保存文件
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("图片上传失败，请稍后重试");
        }
        // 记录文件

        // 返回结果
        return ResponseResult.SUCCESS("图片上传成功");

    }

    @Override
    public void viewImage(HttpServletResponse response, String imageId) {
        File file = new File(imagePath + File.separator + "androidparty.png");
        OutputStream writer = null;
        FileInputStream fos = null;
        try {
            response.setContentType("image/png");
            writer = response.getOutputStream();
            // 读取
            fos = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len = fos.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
