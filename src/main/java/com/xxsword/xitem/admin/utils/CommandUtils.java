package com.xxsword.xitem.admin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CommandUtils {
    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public static List<String> comm(String command) {
        if (StringUtils.isBlank(command)) {
            return null;
        }
        try {
            // 使用Runtime来执行命令
//            Process process = Runtime.getRuntime().exec(command);
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

            // 创建一个新的输入流来读取命令的结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 逐行读取输出
            String line;
            List<String> stringList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
//                log.info(line);
                stringList.add(line);
            }

            // 关闭流
            reader.close();

            // 等待进程结束，并获取退出码
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("Command executed successfully.");
            } else {
                log.warn("Command execution failed with exit code: " + exitVal);
            }
            return stringList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> ceShi() {
        List<String> stringList = new ArrayList<>();
        stringList.add("733       1 /usr/sbin/gssproxy -D");
        stringList.add("100901       1 proxy bridge -p :33080 -C proxy.crt -K proxy.key --log proxy-bridge.log --forever");
        stringList.add("100907  100901 proxy bridge -p :33080 -C proxy.crt -K proxy.key --log proxy-bridge.log");
        stringList.add("100971       1 proxy server -r :34120@:9092 -P 127.0.0.1:33080 -C proxy.crt -K proxy.key --k thinkpadt480s --forever --log proxy-server-34120-9092.log");
        stringList.add("100978  100971 proxy server -r :34120@:9092 -P 127.0.0.1:33080 -C proxy.crt -K proxy.key --k thinkpadt480s --log proxy-server-34120-9092.log");
        return stringList;
    }

    /**
     * 将响应信息拆解，并剔除空格
     *
     * @param list
     * @return
     */
    public static List<String[]> commHandle(List<String> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }
        List<String[]> list2 = new ArrayList<>();
        for (String result : list) {
            if (StringUtils.isBlank(result)) {
                continue;
            }
            list2.add(Arrays.stream(result.split(" "))
                    .filter(str -> !str.isEmpty())
                    .toArray(String[]::new));
        }
        return list2;
    }
}
