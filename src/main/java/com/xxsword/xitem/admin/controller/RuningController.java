package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.model.proxy.JSONDBCommNetLink;
import com.xxsword.xitem.admin.utils.CommandUtils;
import com.xxsword.xitem.admin.utils.JSONDBFileUtil;
import com.xxsword.xitem.admin.utils.ProxyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("admin/run")
public class RuningController extends BaseController {

    /**
     * 运行中的
     */
    @RequestMapping("runing")
    public String loginOut(HttpServletRequest request, Model model) {
        // 控制台过滤
        model.addAttribute("resultHandle", ProxyUtils.psProxy());
        // 配置文件
        model.addAttribute("conf", JSONDBFileUtil.getConf());
        // DB信息
        model.addAttribute("proxydb", ProxyUtils.getDBCommALLByDbType(1));
        return "/admin/runing";
    }

}
