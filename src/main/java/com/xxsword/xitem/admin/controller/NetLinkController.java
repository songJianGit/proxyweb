package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.proxy.NetLinkModel;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/netlink")
public class NetLinkController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        List<String> results = CommandUtils.comm(ProxyUtils.COMM_PS);
        model.addAttribute("results", results);
        model.addAttribute("resultHandle", ProxyUtils.commAssociationJsonFileInfo(ProxyUtils.handlePSPid1(CommandUtils.commHandle(results))));
        model.addAttribute("conf", JSONFileUtil.getConf());
        model.addAttribute("proxydb", ProxyUtils.getDBCommALL());
        return "/admin/netlink/list";
    }

    @RequestMapping("add")
    public String add(HttpServletRequest request, Model model) {
        return "/admin/netlink/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, NetLinkModel netLink) {
        String comm = ProxyUtils.getProxyServerStart(netLink);
        CommandUtils.comm(comm, true);
        String cmd_comm = comm.replaceAll(" --daemon", "");// ps的时候，没有这个参数，这里去掉再做md5
        ProxyUtils.setDBComm(Utils.getMD5(cmd_comm), netLink.getNotes(), comm);
        return RestResult.OK();
    }

    @RequestMapping("stop")
    @ResponseBody
    public RestResult stop(HttpServletRequest request, String key) {
        Map<String, String[]> mapMD5 = ProxyUtils.pid1ToCommMap(ProxyUtils.handlePSPid1(CommandUtils.commHandle(CommandUtils.comm(ProxyUtils.COMM_PS))));
        String[] value = mapMD5.get(key);
        CommandUtils.comm("kill " + value[0]);
        return RestResult.OK();
    }

    @RequestMapping("delKey")
    @ResponseBody
    public RestResult delKey(HttpServletRequest request, String key) {
        ProxyUtils.delDB(key);
        return RestResult.OK();
    }

    @RequestMapping("editValue")
    public String editValue(HttpServletRequest request, String key, Model model) {
        model.addAttribute("key", key);
        if (StringUtils.isNotBlank(key)) {
            model.addAttribute("value", ProxyUtils.getDBComm(key));
        }
        return "/admin/netlink/editvalue";
    }

    @RequestMapping("saveValue")
    @ResponseBody
    public RestResult saveValue(HttpServletRequest request, String key, String value) {
        ProxyUtils.setDBComm(key, value);
        return RestResult.OK();
    }
}
