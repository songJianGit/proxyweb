<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "commons/head.ftl"/>
    <style>
        .login-form {
            width: 300px;
            margin: 0 auto;
            line-height: 37px;
        }
        .label-w {
            display: inline-block;
            width: 53px;
        }
    </style>
</head>
<body>
<form action="#!" method="post" class="login-form">
    <div><label for="loginName" class="label-w">登录名</label><input id="loginName" type="text" maxlength="100"/></div>
    <div><label for="passWord" class="label-w">密码</label><input id="passWord" type="password" maxlength="100"/></div>
    <div>
        <button type="button" id="logBtn">登录</button>
    </div>
</form>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/layer-3.5.1/layer.js"></script>
<script type="text/javascript">
    // 登录按钮点击事件
    $('#logBtn').click(function () {
        logBtn();
    });

    // 登录
    function logBtn() {
        let loginName = $('#loginName').val();
        let passWord = $('#passWord').val();
        if (isBlank_login(loginName)) {
            layer.msg("请输入登录名");
            return false;
        } else if (isBlank_login(passWord)) {
            layer.msg("请输入密码");
            return false;
        } else {
            $.ajax({
                url: '${ctx.contextPath}/checkLogin',
                cache: false,
                type: 'post',
                data: {
                    'loginName': loginName,
                    'passWord': passWord
                },
                success: function (data) {
                    if (data.result) {
                        window.location.href = '${ctx.contextPath}/admin/netlink/list';
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }
    }

    function isBlank_login(val) {
        return val == undefined || val == '' || val.length == 0;
    }
</script>
</body>
</html>
