package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

/**
 * 命令数据模型（直接运行命令）
 */
@Data
public class ProxyCMDModel extends ProxyBase{
    private String cmd;// 命令
    private Integer dbType = 2;// 指定数据类型

}
