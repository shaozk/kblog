package org.example.blog.controller;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dao.LabelDao;
import org.example.blog.pojo.Labels;
import org.example.blog.pojo.User;
import org.example.blog.response.ResponseResult;
import org.example.blog.response.ResponseState;
import org.example.blog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.util.Date;

@Transactional
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker ;

    @GetMapping("/hello-world")
    public String helloWorld() {
        log.info("hello,world");
        return "hello";
    }


    @GetMapping("/test-json")
    public ResponseResult testJson() {
        return ResponseResult.SUCCESS().setData("hello");
    }

    @PostMapping("test-login")
    public ResponseResult testLogin(@RequestBody User user) {
        log.info(user.toString());
        ResponseResult loginSuccess = new ResponseResult(ResponseState.LOGIN_SUCCESS);
        return loginSuccess.setData("登录成功");
    }

    @PostMapping("/label")
    public ResponseResult addLabel(@RequestBody Labels labels){
        // 判断数据是否有效

        // 补全数据
        labels.setId(idWorker.nextId() + "");
        labels.setCreateTime(new Date());
        labels.setUpdateTime(new Date());

        // 保存数据
        labelDao.save(labels);
        return ResponseResult.SUCCESS("测试标签添加成功");
    }

    // 测试图灵验证码
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        //specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        // 验证码存入session
        request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }





}
