<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div .border-div {
            border: 1px solid #cecece;
            margin: 13px;
            padding: 5px;
            overflow: hidden;
        }

        .span-w {
            display: inline-block;
            min-width: 13px;
        }

        table tr td {
            word-break: break-all;
        }

        .body-div p {
            word-break: break-all;
        }
    </style>
</head>
<body>

<div class="body-div">
    <#include "../commons/menu.ftl"/>
    <div class="border-div">
        DB文件信息
        <#list proxydb as db>
            <div class="border-div">
                <p>主键：${db.comm.key!}</p>
                <p>命令（控制台运行全文）：${db.comm.comm!}</p>
                <p>备注：${db.comm.notes!}</p>
                <p>java重启时是否启动：
                    <#if db.comm.initStart==1>
                        是
                    <#else>
                        否
                    </#if>
                </p>
<#--                <p>创建时间：${db.comm.cdate!}</p>-->
<#--                <p>更新时间：${db.comm.ldate!}</p>-->
                <p>
                    <button type="button" onclick="editBtnNotes('${db.comm.key!}')">编辑备注</button>
                    <button type="button" onclick="copyBtn('${db.comm.key!}')">复制</button>
                    <#if db.runFlag==0>
                        <button type="button" onclick="delBtn('${db.comm.key!}')">删除</button>
                        <button type="button" onclick="runBtn('${db.comm.key!}')">运行命令</button>
                    </#if>
                </p>
            </div>
        </#list>
    </div>
</div>

<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function copyBtn(key) {
        layer_show('复制', '${ctx.contextPath}/admin/cmd/edit?key=' + key);
    }

    function editBtnNotes(key) {
        layer_show('编辑', '${ctx.contextPath}/admin/cmd/editBtnNotes?key=' + key);
    }

    function delBtn(key) {
        layer.confirm('确认删除？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${ctx.contextPath}/admin/cmd/delKey',
                cache: false,
                data: {
                    key: key
                },
                success: function (data) {
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
                url: '${ctx.contextPath}/admin/cmd/stop',
                cache: false,
                data: {
                    key: key
                },
                success: function (data) {
                    reloadData();
                }
            });
        }, function () {
            // 取消
        });
    }

    function runBtn(key) {
        layer.confirm('确认运行？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${ctx.contextPath}/admin/cmd/run',
                cache: false,
                data: {
                    key: key
                },
                success: function (data) {
                    reloadData();
                }
            });
        }, function () {
            // 取消
        });
    }

    // 重新加载数据
    function reloadData() {
        let indexLoad = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        setTimeout(function () {
            layer.close(indexLoad);
            window.location.reload();
        }, 1000);
    }
</script>
</body>
</html>
