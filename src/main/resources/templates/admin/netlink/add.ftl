<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div>
    <div><label for="server">服务器监听端口</label><input id="server" placeholder="服务器监听端口" type="number"/></div>
    <div><label for="client">客户端穿透端口</label><input id="client" placeholder="客户端穿透端口" type="number"/></div>
</div>
<div>
    <button type="button" onclick="addBtn()">新增穿透</button>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        let server = $("#server").val();
        let client = $("#client").val();
        layer.msg("server: " + server + ", client:" + client);
    }
</script>
</body>
</html>
