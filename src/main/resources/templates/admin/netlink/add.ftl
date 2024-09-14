<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div label {
            display: inline-block;
            width: 115px;
        }

        .line-div {
            margin: 5px 0;
        }
    </style>
</head>
<body>
<div class="body-div">
    <div>
        <div class="line-div">
            <label for="serverPort">服务器监听端口</label>
            <input id="serverPort" placeholder="服务器监听端口" type="number"/>*
        </div>
        <div class="line-div">
            <label for="clientPort">客户端穿透端口</label>
            <input id="clientPort" placeholder="客户端穿透端口" type="number"/>*
        </div>
        <div class="line-div">
            <label for="bridgeIp">bridgeIp</label>
            <input id="bridgeIp" placeholder="bridgeIp" type="text" maxlength="20"/>*
        </div>
        <div class="line-div">
            <label for="bridgePort">bridgePort</label>
            <input id="bridgePort" placeholder="bridgePort" type="number"/>*
        </div>
        <div class="line-div">
            <label for="k">客户端标识</label>
            <input id="k" placeholder="客户端标识" type="text" maxlength="100"/>*
        </div>
        <div class="line-div">
            <label for="notes">备注</label>
            <input id="notes" placeholder="备注" type="text" maxlength="200"/>
        </div>
    </div>
    <div class="line-div">
        <button type="button" onclick="addBtn()">保存</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        let serverPort = $("#serverPort").val();
        let clientPort = $("#clientPort").val();
        let bridgeIp = $("#bridgeIp").val();
        let bridgePort = $("#bridgePort").val();
        let k = $("#k").val();
        let notes = $("#notes").val();
        if (isBlank(serverPort)) {
            layer.msg("请填写服务器监听端口");
            return false;
        }
        if (isBlank(clientPort)) {
            layer.msg("请填写客户端穿透端口");
            return false;
        }
        if (isBlank(bridgeIp)) {
            layer.msg("请填写bridgeIp");
            return false;
        }
        if (isBlank(bridgePort)) {
            layer.msg("请填写bridgePort");
            return false;
        }
        if (isBlank(k)) {
            layer.msg("请填写客户端标识");
            return false;
        }
        $.ajax({
            url: '${ctx.contextPath}/admin/netlink/save',
            cache: false,
            data: {
                serverPort: serverPort,
                clientPort: clientPort,
                bridgeIp: bridgeIp,
                bridgePort: bridgePort,
                k: k,
                notes: notes
            },
            success: function (data) {
                parent.reloadData();
                // layer_close();
            }
        });
    }

    $(function () {
        $('#k').on('input', function () {
            let value = $(this).val();
            // 使用正则表达式移除非英文和非数字字符
            $(this).val(value.replace(/[^a-zA-Z0-9]/g, ''));
        });
    });
</script>
</body>
</html>
