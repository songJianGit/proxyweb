package com.xxsword.xitem.admin.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProxyUtils {
    /**
     * 拿pid为1的命令行响应
     *
     * @param list
     * @return
     */
    public static List<String[]> handlePSPid1(List<String[]> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }
        List<String[]> list2 = new ArrayList<>();
        for (String[] result : list) {
            if ("1".equals(result[1]) && "proxy".equals(result[2])) {
                list2.add(result);
            }
        }
        return list2;
    }
}
