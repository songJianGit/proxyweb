package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.utils.CaptchaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
@Slf4j
public class LoginController extends BaseController {

    /**
     * 用户登出
     */
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession();
        return "redirect:login";
    }

    /**
     * 用户登录
     */
    @GetMapping("adminLogin")
    public String login(HttpServletRequest request) {
        request.getSession();
        return "/admin/login";
    }

    @PostMapping("checkLogin")
    @ResponseBody
    public RestResult checkLogin(HttpServletRequest request, String loginName, String passWord, String captcha) {
        return RestResult.Codes(Codes.LOGIN_OK);
    }

    /**
     * 验证码
     */
    @GetMapping("getCaptcha")
    public void getYzm(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            String verifyCode = CaptchaUtils.generateCaptcha(0, 37, response.getOutputStream());
//            verifyCode = "1234";
            request.getSession().setAttribute(Constant.CAPTCHA, verifyCode.toLowerCase());
        } catch (Exception e) {
            log.error("获取验证码异常：{}", e.getMessage());
        }
    }


    @RequestMapping("test2024")
    @ResponseBody
    public RestResult test2024(HttpServletRequest request) {
        ps();
        return RestResult.OK();
    }

    public static void ps() {
        String command = "ps -eo pid,ppid,cmd | grep proxy";

        try {
            // 使用Runtime来执行命令
//            Process process = Runtime.getRuntime().exec(command);
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

            // 创建一个新的输入流来读取命令的结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 逐行读取输出
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // 关闭流
            reader.close();

            // 等待进程结束，并获取退出码
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("Command executed successfully.");
            } else {
                log.info("Command execution failed with exit code: " + exitVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
