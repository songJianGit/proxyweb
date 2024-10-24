package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

/**
 * 内网穿透的业务数据模型
 */
@Data
public class NetLinkModel {
    private String key;// 主键
    private Integer serverPort;// 服务器监听端口
    private Integer clientPort;// 客户端穿透端口
    private String bridgeIp;// bridge的IP
    private Integer bridgePort;// bridge的端口
    private String k;// 客户端标识
    private String notes;// 备注
    private Integer initStart;
}
