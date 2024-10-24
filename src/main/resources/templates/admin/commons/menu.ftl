<div class="border-div">
    <div>项目名称：${conf.project_name!}</div>
    <div>${Session.puser.loginName}<a href="${ctx.contextPath}/loginOut">退出登录</a></div>
</div>
<div class="border-div">
    <a href="${ctx.contextPath}/admin/netlink/list">TCP穿透</a>
    <a href="${ctx.contextPath}/admin/cmd/list">CMD</a>
</div>