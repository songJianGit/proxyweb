package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.proxy.JSONDBCommCMD;
import com.xxsword.xitem.admin.model.proxy.CMDModel;
import com.xxsword.xitem.admin.utils.CommandUtils;
import com.xxsword.xitem.admin.utils.JSONDBFileUtil;
import com.xxsword.xitem.admin.utils.ProxyUtils;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
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
@RequestMapping("admin/cmd")
public class CMDController extends BaseController {

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        List<String> results = CommandUtils.comm(ProxyUtils.COMM_PS);
        // 控制台
//        model.addAttribute("results", results);
        // 控制台过滤
        model.addAttribute("resultHandle", ProxyUtils.commAssociationJsonFileInfo(ProxyUtils.handlePSPid1(CommandUtils.commHandle(results)), JSONDBCommCMD.class));
        // 配置文件
        model.addAttribute("conf", JSONDBFileUtil.getConf());
        // DB信息
        model.addAttribute("proxydb", ProxyUtils.getDBCommALLByDbType(2));
        return "/admin/cmd/list";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, String key, Model model) {
        JSONDBCommCMD jsondbCommCMD = new JSONDBCommCMD();
        if (StringUtils.isNotBlank(key)) {
            jsondbCommCMD = ProxyUtils.getDBComm(key, JSONDBCommCMD.class);
        }
        model.addAttribute("comm", jsondbCommCMD);
        return "/admin/cmd/edit";
    }

    @PostMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, CMDModel cmdModel) {
        return runCommAndSaveDB(cmdModel);
    }

    /**
     * 运行命令，并记录DB信息
     *
     * @param cmdModel
     * @return
     */
    private static RestResult runCommAndSaveDB(CMDModel cmdModel) {
        String comm = cmdModel.getCmd();
        if (StringUtils.isBlank(comm)) {
            return RestResult.Fail();
        }
        comm = StringEscapeUtils.unescapeHtml4(comm);
        if (!comm.contains("--forever")) {
            comm += " --forever";// 守护运行
        }
        if (!comm.contains("--daemon")) {
            comm += " --daemon";// 后台运行
        }
        if (!ProxyUtils.checkCmd(comm)) {
            return RestResult.Fail("异常命令(命令不是proxy开头，或者命令中包含了符号 && ; || | ( )");
        }
        if (StringUtils.isBlank(cmdModel.getKey())) {
            CommandUtils.comm(comm, true);// key为空时，为新增，直接运行；key有值时为复制，不运行。
        }
        String cmd_comm = comm.replaceAll(" --daemon", "").replaceAll("\"", "");
        ProxyUtils.setDBCommCMD(Utils.getMD5(cmd_comm), cmdModel, comm);
        return RestResult.OK();
    }

    @RequestMapping("run")
    @ResponseBody
    public RestResult run(HttpServletRequest request, String key) {
        if (StringUtils.isBlank(key)) {
            return RestResult.Fail();
        }
        JSONDBCommCMD jsondbCommCMD = ProxyUtils.getDBComm(key, JSONDBCommCMD.class);
        if (jsondbCommCMD == null || StringUtils.isBlank(jsondbCommCMD.getComm())) {
            return RestResult.Fail();
        }
        CommandUtils.comm(jsondbCommCMD.getComm(), true);
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
        model.addAttribute("comm", ProxyUtils.getDBComm(key, JSONDBCommCMD.class));
        return "/admin/cmd/editnotes";
    }

    @PostMapping("saveNotes")
    @ResponseBody
    public RestResult saveNotes(HttpServletRequest request, String key, String notes) {
        ProxyUtils.setDBCommNotes(key, notes, JSONDBCommCMD.class);
        return RestResult.OK();
    }
}
