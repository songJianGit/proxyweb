<div class="border-div" style="padding: 13px 7px">
    <div style="float: left">项目名称：${conf.project_name!}</div>
    <div style="float: left;margin-left: 10%">
        <a href="${ctx.contextPath}/admin/run/runing">运行中的</a>
        <a href="${ctx.contextPath}/admin/netlink/list2">TCP穿透</a>
        <a href="${ctx.contextPath}/admin/cmd/list2">CMD命令</a>
    </div>
    <div style="float: right">${Session.puser.loginName}<a href="${ctx.contextPath}/loginOut">退出登录</a></div>
</div>