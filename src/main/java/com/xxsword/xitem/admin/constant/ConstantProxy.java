package com.xxsword.xitem.admin.constant;

public class ConstantProxy {

    private static final String LOG_PATH = "/root/goProxy";// 指定goProxy的日志路径
    private static final String SSL_KEY_PATH = "/root/goProxy/proxy.key";// goProxy的证书
    private static final String SSL_CRT_PATH = "/root/goProxy/proxy.crt";// goProxy的证书

    // bridge 端的启动
    public static final String PROXY_BRIDGE_START = "proxy bridge -p \":[0]\" -C " + SSL_CRT_PATH + " -K " + SSL_KEY_PATH + " --log " + LOG_PATH + "/proxy-bridge.log --forever --daemon";
    // server 端的启动
    public static final String PROXY_SERVER_START = "proxy server -r \":[0]@:[1]\" -P \"[2]:[3]\" -C " + SSL_CRT_PATH + " -K " + SSL_KEY_PATH + " --k [4] --log " + LOG_PATH + "/proxy-server-[0]-[1].log --forever --daemon";

}
