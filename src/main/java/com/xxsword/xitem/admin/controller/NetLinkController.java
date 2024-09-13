package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.CommandUtils;
import com.xxsword.xitem.admin.utils.ProxyUtils;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/netlink")
public class NetLinkController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        List<String> results = CommandUtils.ceShi();
        model.addAttribute("results", results);
        model.addAttribute("resultHandle", ProxyUtils.handlePSPid1(CommandUtils.commHandle(results)));
        model.addAttribute("conf", Utils.getConf());
        return "/admin/netlink/list";
    }
    @RequestMapping("add")
    public String add(HttpServletRequest request, Model model) {
        return "/admin/netlink/add";
    }
}
