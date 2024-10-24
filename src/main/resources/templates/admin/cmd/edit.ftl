<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div label {
            display: inline-block;
            width: 155px;
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
            <label for="cmd">命令</label>
            <textarea id="cmd" maxlength="500">${comm.cmd!}</textarea>
        </div>
        <div class="line-div">
            <label for="notes">备注</label>
            <input id="notes" placeholder="备注" type="text" maxlength="200" value="${comm.notes!}"/>
        </div>
        <div class="line-div">
            <label>java重启时是否启动</label>
            <input name="initStart" type="radio" value="1"
                   <#if comm.initStart??><#if comm.initStart==1>checked</#if></#if>/>是
            <input name="initStart" type="radio" value="0"
                   <#if comm.initStart??><#if comm.initStart==0>checked</#if></#if>/>否
        </div>
    </div>
    <div class="line-div">
        <button type="button" onclick="addBtn()">保存</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        let cmd = $("#cmd").val();
        let notes = $("#notes").val();
        let initStart = $("input[name='initStart']:checked").val();
        if (isBlank(initStart)) {
            initStart = 1;
        }
        if (isBlank(cmd)) {
            layer.msg("请填写需要执行的命令");
            return false;
        }
        $.ajax({
            url: '${ctx.contextPath}/admin/cmd/save',
            cache: false,
            data: {
                key: '${comm.key!}',
                cmd: cmd,
                notes: notes,
                initStart: initStart
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
