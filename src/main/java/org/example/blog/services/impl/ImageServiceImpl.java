package org.example.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.ImageDao;
import org.example.blog.pojo.Image;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.services.IImageService;
import org.example.blog.services.IUserService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.IdWorker;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl extends BaseService implements IImageService {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    @Value("${k.blog.image.save-path}")
    public String imagePath;

    @Value("${k.blog.image.max-size}")
    public long maxSize;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IUserService userService;

    @Autowired
    private ImageDao imageDao;

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
        // 获取相关数据，比如说文件类型，文件名称
        String name = file.getName();
        String originalFilename = file.getOriginalFilename();
        log.info("name ==> " + name);
        log.info("originalFilename ==> " + originalFilename);
        String type = getType(contType, originalFilename);
        if (type == null) {
            return ResponseResult.FAILED("不支持此图片类型");
        }

        // 限制文件大小
        long size = file.getSize();
        log.info("maxSize ==> " + maxSize + " size ==> " + size);
        if (size > maxSize) {
            return ResponseResult.FAILED("图片最大仅支持" + (maxSize / 1024 / 1024) + "Mb");
        }
        // 创建图片的保存目录
        // 规则：配置目录/日期/类型/ID类型
        long currentMillions = System.currentTimeMillis();
        String currentDay = simpleDateFormat.format(currentMillions);

        log.info("currentDay ==> " + currentDay);
        String dayPath = imagePath + File.separator + currentDay;
        File dayPathFile = new File(dayPath);
        // 判断日期文件是否存在
        if (!dayPathFile.exists()) {
            dayPathFile.mkdirs();
        }
        String targetName = String.valueOf(idWorker.nextId());
        String targetPath = dayPath + File.separator + type + File.separator + targetName + "." + type;
        File targetFile = new File(targetPath);
        // 判断类型文件是否存在
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }

        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            log.info("targetFile ==> " + targetFile);
            // 保存文件
            file.transferTo(targetFile);
            //TODO: 保存记录到数据库
            // 返回结果，包含这个图片的名称和访问路径
            // 1.访问路径 --> 得到对应着访问来
            Map<String, String> result = new HashMap<>();
            String resultPath = currentMillions + "_" + targetName + "." + type;
            result.put("id", resultPath);
            log.info("result path ==> " + resultPath);
            // 2.名称 --> alt="图片描述"，如果不写，前端可以使用名称作为这个描述
            result.put("name", originalFilename);

            // 记录文件,保存在数据库
            Image image = new Image();
            image.setContentType(contType);
            image.setId(targetName);
            image.setCreateTime(new Date());
            image.setUpdateTime(new Date());
            image.setPath(targetFile.getPath());
            image.setName(originalFilename);
            image.setUrl(resultPath);
            image.setState("1");
            User user = userService.checkUser();
            image.setUserId(user.getId());
            imageDao.save(image);

            // 返回结果
            return ResponseResult.SUCCESS("图片上传成功").setData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.FAILED("图片上传失败，请稍后重试");


    }

    private String getType(String contType, String originalFilename) {
        String type = null;
        if (Constants.ImageType.TYPE_PNG_WITH_PREFIX.equals(contType)
                && originalFilename.endsWith(Constants.ImageType.TYPE_PNG)) {
            type = Constants.ImageType.TYPE_PNG;
        }else if (Constants.ImageType.TYPE_GIF_WITH_PREFIX.equals(contType)
                && originalFilename.endsWith(Constants.ImageType.TYPE_GIF)) {
            type = Constants.ImageType.TYPE_GIF;
        }else if (Constants.ImageType.TYPE_JPG_WITH_PREFIX.equals(contType)
                && originalFilename.endsWith(Constants.ImageType.TYPE_JPG)) {
            type = Constants.ImageType.TYPE_JPG_WITH_PREFIX;
        }
        return type;
    }

    @Override
    public void viewImage(HttpServletResponse response, String imageId) {
        // 需要目录
        // 需要日期
        String[] paths = imageId.split("_");
        String dayValue = paths[0];
        String format = simpleDateFormat.format(Long.parseLong(dayValue));
        log.info("viewImage format ==> " + format);
        // 需要ID
        String name = paths[1];
        // 需要类型
        String type = name.substring(name.length() - 3);
        // 使用日期的时间戳_ID.类型
        String targetPath = imagePath + File.separator + format + File.separator +
                type +
                File.separator + name;
        log.info("get image target path ==> " + targetPath);
        File file =  new File(targetPath);

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

    @Override
    public ResponseResult listImages(int page, int size) {
        // 处理page，size
        page = checkPage(page);
        size = checkSize(size);

        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }

        // 创建分页条件
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        // 查询
        Pageable pageable = PageRequest.of(page -1 , size, sort);
        // 返回结果
        String userId = user.getId();
        Page<Image> all = imageDao.findAll(new Specification<Image> (){

            @Override
            public Predicate toPredicate(Root<Image> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 根据用户ID
                Predicate userIdPre = cb.equal(root.get("userId").as(String.class), userId);
                // 根据状态
                Predicate statePre = cb.equal(root.get("state").as(String.class), "1");
                return cb.and(userIdPre, statePre);
            }
        }, pageable);
        return ResponseResult.SUCCESS("获取图片列表成功").setData(all);
    }

    @Override
    public ResponseResult deleteImageById(String imageId) {
        int result = imageDao.deleteImageByUpdateState(imageId);
        if (result > 0) {
            return ResponseResult.SUCCESS("删除成功");
        }
        return ResponseResult.FAILED("图片不存在");
    }
}
