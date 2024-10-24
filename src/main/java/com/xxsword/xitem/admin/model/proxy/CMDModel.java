package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

/**
 * 命令数据模型（直接运行命令）
 */
@Data
public class CMDModel {
    private String key;// 主键
    private String cmd;// 命令
    private String notes;// 备注
    private Integer initStart;
    private Integer dbType = 2;

}
