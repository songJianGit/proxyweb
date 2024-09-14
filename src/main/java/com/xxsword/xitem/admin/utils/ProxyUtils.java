package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSONObject;
import com.xxsword.xitem.admin.constant.ConstantProxy;
import com.xxsword.xitem.admin.model.proxy.JSONDBComm;
import com.xxsword.xitem.admin.model.proxy.NetLinkModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Slf4j
public class ProxyUtils {

    public static final String COMM_PS = "ps -eo pid,ppid,cmd | grep proxy";
    private static final String FILE_DB_NAME = "proxydb.json";

    /**
     * 拿主进程
     *
     * @param list
     * @return
     */
    public static List<String[]> handlePSPid1(List<String[]> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }
        List<String[]> list2 = new ArrayList<>();
        for (String[] result : list) {
            if ("proxy".equals(result[2])) {// 含 proxy
                for (String r : result) {
                    if ("--forever".equals(r)) {// 含 proxy 并且含 --forever
                        list2.add(result);
                    }
                }
            }
        }
        return list2;
    }

    public static String getProxyServerStart(NetLinkModel netLink) {
        if (netLink.getServerPort() == null) {
            log.error("serverPort null");
            return null;
        }
        if (netLink.getClientPort() == null) {
            log.error("clientPort null");
            return null;
        }
        if (netLink.getBridgeIp() == null) {
            log.error("bridgeIp null");
            return null;
        }
        if (netLink.getBridgePort() == null) {
            log.error("bridgePort null");
            return null;
        }
        if (netLink.getK() == null) {
            log.error("k null");
            return null;
        }
        String comm = ConstantProxy.PROXY_SERVER_START;
        comm = comm.replaceAll("\\[0]", netLink.getServerPort().toString());
        comm = comm.replaceAll("\\[1]", netLink.getClientPort().toString());
        comm = comm.replaceAll("\\[2]", netLink.getBridgeIp());
        comm = comm.replaceAll("\\[3]", netLink.getBridgePort().toString());
        comm = comm.replaceAll("\\[4]", netLink.getK());
        return comm;
    }

    /**
     * 命令行结果 和 json文件数据记录进行关联
     *
     * @return
     */
    public static List<Map<String, Object>> commAssociationJsonFileInfo(List<String[]> commlist) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (commlist == null) {
            return mapList;
        }
        for (String[] comms : commlist) {
            List<String> comm01 = new ArrayList<>(Arrays.asList(comms).subList(2, comms.length));
            String key = Utils.getMD5(String.join(" ", comm01));
            Map<String, Object> map = new HashMap<>();
            map.put("comm", comms);
            map.put("key", key);
            map.put("nodes", getDBComm(key));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 将过滤后的数据，进一步处理
     *
     * @param list
     * @return Map<去除pid和id后的md5, 命令信息>
     */
    public static Map<String, String[]> pid1ToCommMap(List<String[]> list) {
        Map<String, String[]> map = new HashMap<>();
        if (list == null) {
            return map;
        }
        for (String[] comms : list) {
            List<String> comm01 = new ArrayList<>(Arrays.asList(comms).subList(2, comms.length));
            map.put(Utils.getMD5(String.join(" ", comm01)), comms);
        }
        return map;
    }

    public static void setDBComm(String key, String notes) {
        setDBComm(key, notes, null);
    }

    /**
     * 写入json文本
     *
     * @param key
     * @param notes
     * @param comm
     */
    public static void setDBComm(String key, String notes, String comm) {
        String filePath = UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/" + FILE_DB_NAME;
        JSONObject jsonObject = JSONFileUtil.getJSONObjectByPath(filePath);
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        JSONDBComm jsondb = jsonObject.getObject(key, JSONDBComm.class);
        if (jsondb == null) {
            jsondb = new JSONDBComm();
            jsondb.setCdate(DateUtil.now());
        }
        jsondb.setLdate(DateUtil.now());
        jsondb.setNotes(notes);
        if (StringUtils.isNotBlank(comm)) {
            jsondb.setComm(comm);
        }
        jsonObject.put(key, jsondb);
        JSONFileUtil.setJSONObjectToFile(filePath, jsonObject);
    }

    /**
     * 读取json文本数据
     *
     * @param key
     * @return
     */
    public static String getDBComm(String key) {
        JSONObject jsonObject = JSONFileUtil.getJSONObjectByPath(UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/" + FILE_DB_NAME);
        if (jsonObject == null) {
            return null;
        }
        JSONDBComm jsondb = jsonObject.getObject(key, JSONDBComm.class);
        return jsondb == null ? null : jsondb.getNotes();
    }

    public static List<Map<String, String>> getDBCommALL() {
        List<Map<String, String>> mapList = new ArrayList<>();
        JSONObject jsonObject = JSONFileUtil.getJSONObjectByPath(UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/" + FILE_DB_NAME);
        if (jsonObject == null) {
            return mapList;
        }
        Map<String, String[]> mapMD5 = ProxyUtils.pid1ToCommMap(ProxyUtils.handlePSPid1(CommandUtils.commHandle(CommandUtils.comm(ProxyUtils.COMM_PS))));
        for (String key : jsonObject.keySet()) {
            Map<String, String> map = new HashMap<>();
            map.put("key", key);
            JSONDBComm jsondb = jsonObject.getObject(key, JSONDBComm.class);
            map.put("notes", jsondb == null ? null : jsondb.getNotes());
            map.put("comm", jsondb == null ? null : jsondb.getComm());
            map.put("delBtn", mapMD5.containsKey(key) ? "0" : "1");
            mapList.add(map);
        }
        return mapList;
    }

    public static void delDB(String key) {
        JSONObject jsonObject = JSONFileUtil.getJSONObjectByPath(UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/" + FILE_DB_NAME);
        if (jsonObject != null) {
            log.info("delDB key:{}", key);
            jsonObject.remove(key);
            String filePath = UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/" + FILE_DB_NAME;
            JSONFileUtil.setJSONObjectToFile(filePath, jsonObject);
        }
    }
}
