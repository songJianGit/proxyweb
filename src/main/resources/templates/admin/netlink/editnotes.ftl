<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .body-div label {
            display: inline-block;
            width: 37px;
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
            <label>主键</label>
            <span style="word-break: break-all">${comm.key!}</span>
        </div>
        <div class="line-div">
            <label for="notes">备注</label>
            <input id="notes" placeholder="备注" value="${comm.notes!}"/>
        </div>
    </div>
    <div class="line-div">
        <button type="button" onclick="addBtn()">保存</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function addBtn() {
        let notes = $("#notes").val();
        if (isBlank(notes)) {
            layer.msg("请填写备注");
            return false;
        }
        $.ajax({
            url: '${ctx.contextPath}/admin/netlink/saveNotes',
            cache: false,
            data: {
                key: '${comm.key!}',
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
