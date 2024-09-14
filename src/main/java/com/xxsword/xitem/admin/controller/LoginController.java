package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONObject;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.utils.JSONFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController extends BaseController {

    /**
     * 用户登出
     */
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession();
        return "redirect:login";
    }

    /**
     * 用户登录
     */
    @GetMapping("adminLogin")
    public String login(HttpServletRequest request) {
        request.getSession();
        return "/admin/login";
    }

    @PostMapping("checkLogin")
    @ResponseBody
    public RestResult checkLogin(HttpServletRequest request, String loginName, String passWord) {
        if (StringUtils.isBlank(loginName)) {
            return RestResult.Codes(Codes.LOGIN_FAIL);
        }
        if (StringUtils.isBlank(passWord)) {
            return RestResult.Codes(Codes.LOGIN_FAIL);
        }
        JSONObject jsonObject = JSONFileUtil.getConf();
        String name = jsonObject.getString("user_login_name");
        String pwd = jsonObject.getString("user_password");
        if (name.equals(loginName) && passWord.equals(pwd)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setLoginName(loginName);
            userInfo.setPassword(passWord);
            request.getSession().setAttribute(Constant.USER_INFO, userInfo);
            return RestResult.Codes(Codes.LOGIN_OK);
        }
        return RestResult.Codes(Codes.LOGIN_FAIL);
    }

}
