<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div label {
            display: inline-block;
            width: 30px;
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
            <label>键</label>
            <span style="word-break: break-all">${key}</span>
        </div>
        <div class="line-div">
            <label for="value">值</label>
            <input id="value" placeholder="值" value="${value!}"/>
        </div>
    </div>
    <div class="line-div">
        <button type="button" onclick="addBtn()">保存</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        let value = $("#value").val();
        if (isBlank(value)) {
            layer.msg("请填写备注");
            return false;
        }
        $.ajax({
            url: '${ctx.contextPath}/admin/netlink/saveValue',
            cache: false,
            data: {
                key: '${key}',
                value: value
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
