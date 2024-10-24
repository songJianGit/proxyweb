package com.xxsword.xitem.admin.model;

import lombok.Data;

@Data
public class JSONDBComm {
    private String key;// 主键
    private String cdate;// 创建时间
    private String ldate;// 更新时间
    private String comm;// 命令
    private String notes;// 备注
    private Integer initStart;// 是否在java启动时，一起启动（若已启动则不管）
    private Integer dbType;// 业务类型（默认为tcp穿透） 1-tcp穿透 2-cmd
}
