package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.proxy.JSONDBCommNetLink;
import com.xxsword.xitem.admin.model.proxy.ProxyNetLinkModel;
import com.xxsword.xitem.admin.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/netlink")
public class NetLinkController extends BaseController {

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        // 控制台
//        model.addAttribute("results", results);
        // 控制台过滤
        model.addAttribute("resultHandle", ProxyUtils.psProxy());
        // 配置文件
        model.addAttribute("conf", JSONDBFileUtil.getConf());
        // DB信息
        model.addAttribute("proxydb", ProxyUtils.getDBCommALLByDbType(1));
        return "/admin/netlink/list";
    }

    @RequestMapping("list2")
    public String list2(HttpServletRequest request, Model model) {
        // 配置文件
        model.addAttribute("conf", JSONDBFileUtil.getConf());
        // DB信息
        model.addAttribute("proxydb", ProxyUtils.getDBCommALLByDbType(1));
        return "/admin/netlink/list2";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, String key, Model model) {
        JSONDBCommNetLink jsondbCommNetLink = new JSONDBCommNetLink();
        if (StringUtils.isNotBlank(key)) {
            jsondbCommNetLink = ProxyUtils.getDBComm(key, JSONDBCommNetLink.class);
        }
        model.addAttribute("comm", jsondbCommNetLink);
        return "/admin/netlink/edit";
    }

    @RequestMapping("editBridge")
    public String editBridge(HttpServletRequest request) {
        return "/admin/netlink/editbridge";
    }

    @PostMapping("saveBridge")
    @ResponseBody
    public RestResult saveBridge(HttpServletRequest request, ProxyNetLinkModel netLink) {
        return runCommAndSaveDB(ProxyUtils.getProxyBridgeStart(netLink), netLink);
    }

    @PostMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, ProxyNetLinkModel netLink) {
        return runCommAndSaveDB(ProxyUtils.getProxyServerStart(netLink), netLink);
    }

    /**
     * 运行命令，并记录DB信息
     *
     * @param comm
     * @param netLink
     * @return
     */
    private static RestResult runCommAndSaveDB(String comm, ProxyNetLinkModel netLink) {
        if (StringUtils.isBlank(comm)) {
            return RestResult.Fail();
        }
        if (!ProxyUtils.checkCmd(comm)) {
            return RestResult.Fail("异常命令(命令不是proxy开头，或者命令中包含了符号 && ; || | ( )");
        }
        if (StringUtils.isBlank(netLink.getKey())) {
            CommandUtils.comm(comm, true);// key为空时，为新增，直接运行；key有值时为复制，不运行。
        }
        ProxyUtils.setDBCommNetLink(ProxyUtils.getCmdKeyBy(comm), netLink, comm);
        return RestResult.OK();
    }

    @RequestMapping("run")
    @ResponseBody
    public RestResult run(HttpServletRequest request, String key) {
        if (StringUtils.isBlank(key)) {
            return RestResult.Fail();
        }
        JSONDBCommNetLink jsondbCommNetLink = ProxyUtils.getDBComm(key, JSONDBCommNetLink.class);
        if (jsondbCommNetLink == null || StringUtils.isBlank(jsondbCommNetLink.getComm())) {
            return RestResult.Fail();
        }
        CommandUtils.comm(jsondbCommNetLink.getComm(), true);
        return RestResult.OK();
    }

    @RequestMapping("stop")
    @ResponseBody
    public RestResult stop(HttpServletRequest request, String key) {
        Map<String, String[]> mapMD5 = ProxyUtils.pid1ToCommMapFast();
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

    @RequestMapping("editBtnNotes")
    public String editBtnNotes(HttpServletRequest request, String key, Model model) {
        model.addAttribute("comm", ProxyUtils.getDBComm(key, JSONDBCommNetLink.class));
        return "/admin/netlink/editnotes";
    }

    @PostMapping("saveNotes")
    @ResponseBody
    public RestResult saveNotes(HttpServletRequest request, String key, String notes) {
        ProxyUtils.setDBCommNotes(key, notes, JSONDBCommNetLink.class);
        return RestResult.OK();
    }
}
