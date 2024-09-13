<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div div {
            border: 1px solid #808080;
            margin: 5px;
        }
    </style>
</head>
<body>

<div class="body-div">
    <div>bridge端口：${conf.bridge_port}</div>
    <div>
        原始信息
        <#list results as item>
            <div>${item!}</div>
        </#list>
    </div>
    <div>
        处理后信息
        <#list resultHandle as results>
            <div>
                <#list results as result>
                    <span>${result!}</span>
                </#list>
            </div>
        </#list>
    </div>
    <div>
        <button type="button" onclick="addBtn()">新增穿透</button>
    </div>
</div>

<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        layer_show(false, '${ctx.contextPath}/admin/netlink/add');
    }
</script>
</body>
</html>
