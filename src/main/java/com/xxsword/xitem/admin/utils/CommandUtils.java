package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CommandUtils {
    public static List<String> comm(String command) {
        return comm(command, false);
    }

    /**
     * 执行命令
     *
     * @param command
     * @param asyn    是否异步
     * @return
     */
    public static List<String> comm(String command, boolean asyn) {
        if ("true".equalsIgnoreCase(SystemConfig.getProperty("command.test.flag"))) {
            return ceShi();
        }
        if (StringUtils.isBlank(command)) {
            return null;
        }
        if (ProxyUtils.checkCmdRUN(command)) {
            log.warn("该命令已经在运行:{}", command);
            return null;// 检查其是否已经运行，如果已经在运行，就不再重复执行
        }
        if (asyn) {
            asyn(command);
            return null;
        } else {
            return wait(command);
        }
    }

    private static void asyn(String command) {
        try {
            // 创建 ProcessBuilder 实例
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
            // 启动子进程
            Process process = pb.start();
            // 可选：关闭输入流和错误流，以避免占用资源
            process.getInputStream().close();
            process.getErrorStream().close();
            log.info("Command executed:{}", command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> wait(String command) {
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
                log.info("Command executed successfully:{}", command);
            } else {
                log.warn("Command execution failed with exit code: {},command:{}", exitVal, command);
            }
            return stringList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> ceShi() {
        List<String> stringList = new ArrayList<>();
        stringList.add("3886     1 proxy bridge -p :33080 -C proxy.crt -K proxy.key --log proxy-bridge.log --forever");
        stringList.add("3894  3886 proxy bridge -p :33080 -C proxy.crt -K proxy.key --log proxy-bridge.log");
        stringList.add("4117     1 proxy server -r :8084@:22 -P 45.125.34.229:33080 -C proxy.crt -K proxy.key --k shuMeiPI --forever --log proxy-server-8084-22.log");
        stringList.add("4124  4117 proxy server -r :8084@:22 -P 45.125.34.229:33080 -C proxy.crt -K proxy.key --k shuMeiPI --log proxy-server-8084-22.log");
        stringList.add("20700  9205 java -jar proxyweb-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod");
        stringList.add("21484     1 proxy server -r :33090@:2123 -P 127.0.0.1:33080 -C /root/goProxy/proxy.crt -K /root/goProxy/proxy.key --k shuMiPI --log /root/goProxy/proxy-server-33090-2123.log --forever");
        stringList.add("21489 21484 proxy server -r :33090@:2123 -P 127.0.0.1:33080 -C /root/goProxy/proxy.crt -K /root/goProxy/proxy.key --k shuMiPI --log /root/goProxy/proxy-server-33090-2123.log");
        stringList.add("21974  2332 grep --color=auto proxy");
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
