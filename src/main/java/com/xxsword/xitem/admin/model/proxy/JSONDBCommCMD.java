package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

/**
 * 命令的jsonDB存储模型
 */
@Data
public class JSONDBCommCMD extends JSONDBComm {
    private String cmd;// 命令
}
