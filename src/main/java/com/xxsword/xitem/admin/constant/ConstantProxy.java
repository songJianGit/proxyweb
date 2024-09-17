package com.xxsword.xitem.admin.constant;

import com.alibaba.fastjson2.JSONObject;
import com.xxsword.xitem.admin.utils.JSONDBFileUtil;

public class ConstantProxy {

    // bridge 端的启动
    public static String PROXY_BRIDGE_START = "proxy bridge -p \":[0]\" -C [SSL_CRT_PATH] -K [SSL_KEY_PATH] --log [LOG_PATH]/proxy-bridge.log --forever --daemon";
    // server 端的启动
    public static String PROXY_SERVER_START = "proxy server -r \":[0]@:[1]\" -P \"[2]:[3]\" -C [SSL_CRT_PATH] -K [SSL_KEY_PATH] --k [4] --log [LOG_PATH]/proxy-server-[0]-[1].log --forever --daemon";

    static {
        JSONObject goProxy = JSONDBFileUtil.getConfGoProxy();
        String crtPath = goProxy.getString("crt_path");
        String keyPath = goProxy.getString("key_path");
        String logPath = goProxy.getString("log_path");

        PROXY_BRIDGE_START = PROXY_BRIDGE_START.replaceAll("\\[SSL_CRT_PATH]", crtPath);// goProxy的证书
        PROXY_BRIDGE_START = PROXY_BRIDGE_START.replaceAll("\\[SSL_KEY_PATH]", keyPath);// goProxy的证书
        PROXY_BRIDGE_START = PROXY_BRIDGE_START.replaceAll("\\[LOG_PATH]", logPath);// goProxy的日志路径

        PROXY_SERVER_START = PROXY_SERVER_START.replaceAll("\\[SSL_CRT_PATH]", crtPath);// goProxy的证书
        PROXY_SERVER_START = PROXY_SERVER_START.replaceAll("\\[SSL_KEY_PATH]", keyPath);// goProxy的证书
        PROXY_SERVER_START = PROXY_SERVER_START.replaceAll("\\[LOG_PATH]", logPath);// goProxy的日志路径
    }

}
