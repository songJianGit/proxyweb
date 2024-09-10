<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "commons/head.ftl"/>
    <style>
        .login-form .has-feedback {
            position: relative;
        }

        .login-form .has-feedback .form-control {
            padding-left: 36px;
        }

        .login-form .has-feedback .mdi {
            position: absolute;
            top: 0;
            left: 0;
            right: auto;
            width: 36px;
            height: 36px;
            line-height: 36px;
            z-index: 4;
            color: #dcdcdc;
            display: block;
            text-align: center;
            pointer-events: none;
        }

        .login-form .has-feedback.row .mdi {
            left: 15px;
        }
    </style>
</head>
<body class="center-vh">
<div class="card card-shadowed p-5 w-420 mb-0 mr-2 ml-2">
    <div class="text-center mb-3">
        <h5>xitem 后台管理</h5>
    </div>

    <form action="#!" method="post" class="login-form">
        <div class="form-group has-feedback">
            <span class="mdi mdi-account" aria-hidden="true"></span>
            <input type="text" class="form-control" id="loginName" placeholder="登录名">
        </div>

        <div class="form-group has-feedback">
            <span class="mdi mdi-lock" aria-hidden="true"></span>
            <input type="password" class="form-control" id="passWord" placeholder="密码">
        </div>

        <div class="form-group has-feedback row">
            <div class="col-7">
                <span class="mdi mdi-check-all form-control-feedback" aria-hidden="true"></span>
                <input type="text" id="captchaInput" class="form-control" placeholder="验证码">
            </div>
            <div class="col-5 text-right">
                <img src="${ctx.contextPath}/getCaptcha" class="pull-right" id="captchaImg" style="cursor: pointer;"
                     onclick="changeImage();"
                     title="点击刷新" alt="captcha">
            </div>
        </div>

        <div class="form-group">
            <button type="button" class="btn btn-block btn-primary" onclick="logBtn()">立即登录</button>
        </div>
    </form>

    <p class="text-center text-muted mb-0">Copyright © 2024 <a href="#!">xitem</a>. All right
        reserved</p>
</div>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/layer-3.5.1/layer.js"></script>
<script type="text/javascript">
    // 登录按钮点击事件
    $('#logBtn').click(function () {
        logBtn();
    });

    // 验证码
    function changeImage() {
        let captchaImg = document.getElementById('captchaImg');
        if (captchaImg != undefined) {
            captchaImg.src = '${ctx.contextPath}/getCaptcha?d=' + Math.random();
        }
    }

    // 登录
    function logBtn() {
        let loginName = $('#loginName').val();
        let passWord = $('#passWord').val();
        let captchaInput = $('#captchaInput').val();
        if (isBlank_login(loginName)) {
            layer.msg("请输入登录名");
            return false;
        } else if (isBlank_login(passWord)) {
            layer.msg("请输入密码");
            return false;
        } else if (isBlank_login(captchaInput)) {
            layer.msg("请输入验证码");
            changeImage();
            return false;
        } else {
            $.ajax({
                url: '${ctx.contextPath}/checkLogin',
                cache: false,
                type: 'post',
                data: {
                    'loginName': loginName,
                    'passWord': passWord,
                    'captcha': captchaInput
                },
                success: function (data) {
                    if (data.result) {
                        window.location.href = '${ctx.contextPath}/admin/system/index';
                    } else {
                        $('#captchaInput').val("");
                        changeImage();
                        layer.msg(data.msg);
                    }
                }
            });
        }
    }

    function isBlank_login(val) {
        if (val == undefined || val == '' || val.length == 0) {
            return true;
        }
        return false;
    }
</script>
</body>
</html>
