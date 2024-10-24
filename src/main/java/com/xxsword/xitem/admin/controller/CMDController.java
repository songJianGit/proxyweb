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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("results", results);
        // 控制台过滤
        model.addAttribute("resultHandle", ProxyUtils.commAssociationJsonFileInfo(ProxyUtils.handlePSPid1(CommandUtils.commHandle(results))));
        // 配置文件
        model.addAttribute("conf", JSONDBFileUtil.getConf());
        // DB信息
        model.addAttribute("proxydb", ProxyUtils.getDBCommALL());
        return "/admin/cmd/list";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, String key, Model model) {
        JSONDBCommCMD jsondbCommCMD = new JSONDBCommCMD();
        if (StringUtils.isNotBlank(key)) {
            jsondbCommCMD = ProxyUtils.getDBCommCMD(key);
        }
        model.addAttribute("comm", jsondbCommCMD);
        return "/admin/cmd/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, CMDModel cmdModel) {
        return runCommAndSaveDB(cmdModel.getCmd(), cmdModel);
    }

    /**
     * 运行命令，并记录DB信息
     *
     * @param comm
     * @param cmdModel
     * @return
     */
    private static RestResult runCommAndSaveDB(String comm, CMDModel cmdModel) {
        if (StringUtils.isBlank(comm)) {
            return RestResult.Fail();
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
        JSONDBCommCMD jsondbCommCMD = ProxyUtils.getDBCommCMD(key);
        if (jsondbCommCMD == null || StringUtils.isBlank(jsondbCommCMD.getComm())) {
            return RestResult.Fail();
        }
        CommandUtils.comm(jsondbCommCMD.getComm(), true);
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

    @RequestMapping("editBtnNotes")
    public String editBtnNotes(HttpServletRequest request, String key, Model model) {
        model.addAttribute("comm", ProxyUtils.getDBCommCMD(key));
        return "/admin/cmd/editnotes";
    }

    @RequestMapping("saveNotes")
    @ResponseBody
    public RestResult saveNotes(HttpServletRequest request, String key, String notes) {
        ProxyUtils.setDBCommNotesCMD(key, notes);
        return RestResult.OK();
    }
}
