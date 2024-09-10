package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.domain.system.entity.*;
import com.xxsword.xitem.admin.service.system.*;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("admin/system")
public class SystemController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 后台主页
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        return "/admin/index";
    }

}
