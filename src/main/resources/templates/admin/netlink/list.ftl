<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div .border-div {
            border: 1px solid #cecece;
            margin: 13px;
            padding: 5px;
        }

        .span-w {
            display: inline-block;
            min-width: 13px;
        }

        table tr td {
            word-break: break-all;
        }
    </style>
</head>
<body>

<div class="body-div">
    <div class="border-div">项目名称：${conf.project_name!}</div>
    <div class="border-div">
        控制台信息
        <#list results as item>
            <p>${item!}</p>
        </#list>
    </div>
    <div class="border-div">
        过滤后信息
        <#list resultHandle as results>
            <div class="border-div">
                <p>
                    <#list results.comm as result>
                        <span class="span-w">${result!}</span>
                    </#list>
                </p>
                <p>${results.nodes!}</p>
                <div>
                    <button type="button" onclick="editBtn('${results.key}')">编辑备注</button>
                    <button type="button" onclick="stopBtn('${results.key}')">停止</button>
                </div>
            </div>
        </#list>
    </div>
    <div>
        <button type="button" onclick="addBtn()">新增穿透</button>
    </div>
    <div class="border-div">
        DB文件信息
        <#list proxydb as db>
            <div class="border-div">
                <p>${db.key}</p>
                <p>${db.comm!}</p>
                <p>${db.notes!}</p>
                <p>
                    <button type="button" onclick="editBtn('${db.key}')">编辑</button>
                    <#if db.delBtn=='1'>
                        <button type="button" onclick="delBtn('${db.key}')">删除</button>
                    </#if>
                </p>
            </div>
        </#list>
    </div>
</div>

<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        layer_show('新增', '${ctx.contextPath}/admin/netlink/add');
    }

    function editBtn(key) {
        layer_show('编辑', '${ctx.contextPath}/admin/netlink/editValue?key=' + key);
    }

    function delBtn(key) {
        layer.confirm('确认删除？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${ctx.contextPath}/admin/netlink/delKey',
                cache: false,
                data: {
                    key: key
                },
                success: function (data) {
                    // layer.msg(data.msg);
                    reloadData();
                }
            });
        }, function () {
            // 取消
        });
    }

    function stopBtn(key) {
        layer.confirm('确认停止？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${ctx.contextPath}/admin/netlink/stop',
                cache: false,
                data: {
                    key: key
                },
                success: function (data) {
                    // layer.msg(data.msg);
                    reloadData();
                }
            });
        }, function () {
            // 取消
        });
    }

    // 重新加载数据
    function reloadData() {
        window.location.reload();
    }
</script>
</body>
</html>
