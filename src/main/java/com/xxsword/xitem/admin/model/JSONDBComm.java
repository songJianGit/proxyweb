package com.xxsword.xitem.admin.model;

import lombok.Data;

@Data
public class JSONDBComm {
    private String key;// 主键
    private String cdate;// 创建时间
    private String ldate;// 更新时间
    private String comm;// 命令
    private String notes;// 备注
}
