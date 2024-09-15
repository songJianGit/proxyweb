package com.xxsword.xitem.admin.model.proxy;

import com.xxsword.xitem.admin.model.JSONDBComm;
import lombok.Data;

@Data
public class JSONDBCommNetLink extends JSONDBComm {
    private Integer serverPort;// 服务器监听端口
    private Integer clientPort;// 客户端穿透端口
    private String bridgeIp;// bridge的IP
    private Integer bridgePort;// bridge的端口
    private String k;// 客户端标识
}
