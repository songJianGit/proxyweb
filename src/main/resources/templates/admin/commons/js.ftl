<script type="text/javascript" src="${ctx.contextPath}/static/plugins/jquery-cookie/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/layer-3.5.1/layer.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"> var contextPath = '${ctx.contextPath}';</script>
<script type="text/javascript">

    // 打开弹出层 参数解释： title 标题 url 请求的url w 弹出层宽度（缺省调默认值） h 弹出层高度（缺省调默认值） full 弹出是否立即全屏
    function layer_show(title, url, w, h, full, closeBtn) {
        if (title == null || title == '') {
            title = false;
        }
        if (w == null || w == '') {
            w = '79%';
        }
        if (h == null || h == '') {
            h = ($(window).height() - 50) + 'px'
        }
        let index = layer.open({
            type: 2,
            area: [w, h],
            shadeClose: false,// 点击遮罩区域，关闭弹层
            title: title,
            content: url,
            closeBtn: closeBtn
        });
        if (full) {
            layer.full(index);
        }
        return index;
    }

    /* 关闭弹出层 */
    function layer_close() {
        let index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function getSelectionId() {
        let obj = $("#table-pagination").bootstrapTable('getSelections');
        if (obj.length == 0) {
            alert("请选择要处理的记录.");
            return false;
        } else if (obj.length == 1) {
            return obj[0].id;
        } else {
            layer.msg("不能选择多条记录.");
            return false;
        }
    }

    function getSelectionIds() {
        let objs = $("#table-pagination").bootstrapTable('getSelections');
        let obids = [];
        if (objs.length == 0) {
            alert("请选择要处理的记录.");
            return false;
        } else {
            for (let i = 0; i < objs.length; i++) {
                obids.push(objs[i].id);
            }
            return obids;
        }
    }

    // js的空判断
    function isBlank(val) {
        if (val == undefined || val == '' || val.length == 0) {
            return true;
        }
        return false;
    }

    function isNotBlank(val) {
        return !isBlank(val);
    }

</script>
