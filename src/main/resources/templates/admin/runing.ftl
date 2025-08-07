<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "./commons/head.ftl"/>
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

        table {
            border-spacing: 0;
            margin-top: 13px;
        }

        table tr td {
            word-break: break-all;
            border: 1px solid #cecece;
            padding: 3px 5px;
            min-width: 73px;
        }

        td, th {
            padding: 0;
        }

        .body-div p {
            word-break: break-all;
        }
    </style>
</head>
<body>

<div class="body-div">
    <#include "./commons/menu.ftl"/>
    <div class="border-div" style="padding: 13px 13px 23px 13px">
        <button type="button" onclick="addBridgeBtn()">新增bridge</button>
        <button type="button" onclick="addBtn()">新增server</button>
        <button type="button" onclick="addBtnCMD()">新增cmd</button>
        <table>
            <tr>
                <td>主键</td>
                <td>命令（PS显示部分）</td>
                <td>备注</td>
                <td>操作</td>
            </tr>
            <#list resultHandle as results>
                <tr>
                    <td>${results.key!}</td>
                    <td>
                        <#list results.comm as result>
                            <span class="span-w">${result!}</span>
                        </#list>
                    </td>
                    <td>${results.nodes!}</td>
                    <td>
                        <button type="button" onclick="stopBtn('${results.key!}')">停止进程</button>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
</div>

<#include "./commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        layer_show('新增', '${ctx.contextPath}/admin/netlink/edit');
    }

    function addBridgeBtn() {
        layer_show('新增', '${ctx.contextPath}/admin/netlink/editBridge');
    }

    function addBtnCMD() {
        layer_show('新增', '${ctx.contextPath}/admin/cmd/edit');
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
