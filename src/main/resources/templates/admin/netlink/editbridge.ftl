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
            <label for="bridgePort">bridgePort</label>
            <input id="bridgePort" placeholder="bridgePort" type="number"/>*
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
        let bridgePort = $("#bridgePort").val();
        let notes = $("#notes").val();
        if (isBlank(bridgePort)) {
            layer.msg("请填写bridgePort");
            return false;
        }
        $.ajax({
            url: '${ctx.contextPath}/admin/netlink/saveBridge',
            cache: false,
            data: {
                bridgePort: bridgePort,
                notes: notes
            },
            success: function (data) {
                parent.reloadData();
                // layer_close();
            }
        });
    }
</script>
</body>
</html>
