package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.constant.ConstantProxy;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.proxy.NetLinkModel;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.CommandUtils;
import com.xxsword.xitem.admin.utils.ProxyUtils;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, NetLinkModel netLink) {
        String comm = ConstantProxy.BRIDGE_SERVER;
        comm = comm.replaceAll("\\[0\\]", netLink.getServerPort().toString());
        comm = comm.replaceAll("\\[1\\]", netLink.getClientPort().toString());
        comm = comm.replaceAll("\\[2\\]", netLink.getBridgeIp());
        comm = comm.replaceAll("\\[3\\]", netLink.getBridgePort().toString());
        comm = comm.replaceAll("\\[4\\]", netLink.getK());
        log.info(comm);
        return RestResult.OK();
    }
}
