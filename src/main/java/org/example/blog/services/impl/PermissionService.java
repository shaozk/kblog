package org.example.blog.services.impl;

import org.example.blog.pojo.User;
import org.example.blog.services.IUserService;
import org.example.blog.utils.Constants;
import org.example.blog.utils.CookieUtil;
import org.example.blog.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service("permission")
public class PermissionService {
    @Autowired
    private IUserService userService;

    /**
     * 判断是不是管理员
     * @return
     */
    public boolean admin() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenKey = CookieUtil.getCookie(request, Constants.User.COOKIE_KEY_TOKEN);
        if (TextUtil.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if(user ==  null) {
            return false;
        }
        if(Constants.User.ROLE_ADMIN.equals(user.getRoles())) {
            return true;
        }
        return false;
    }
}
