package com.xxsword.xitem.admin.interceptor;

import com.xxsword.xitem.admin.constant.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 验证用户是否登录
        Object o = request.getSession().getAttribute(Constant.USER_INFO);
        if (o == null) {
            return back(request, response);
        }
        return true;
    }

    private boolean back(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/adminLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
