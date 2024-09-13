package com.xxsword.xitem.admin.constant;

public class ConstantProxy {

    // bridge 端的启动
    public static final String BRIDGE_START = "proxy bridge -p \":[0]\" -C proxy.crt -K proxy.key --log proxy-bridge.log --forever --daemon";
    // server 端的启动
    public static final String BRIDGE_SERVER = "proxy server -r :[0]@:[1] -P [2]:[3] -C proxy.crt -K proxy.key --k [4] --forever --log proxy-server-[0]-[1].log";

}
