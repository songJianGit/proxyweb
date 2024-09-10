<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
    <link href="${ctx.contextPath}/static/admin/exam/css/style.css" rel="stylesheet">
</head>
<body style="background-color: #fff">
<div class="callout callout-success">
    <div>总题数：${paperVO.snum!}&nbsp;题</div>
    <div>当前总分：${paperVO.score!}&nbsp;分</div>
</div>
<div class="paper-box">
    <div class="paper-title">${paperVO.title!}</div>
    <#list paperVO.questionVOList as item>
        <div class="q-item-box">
            <div class="q-item-title-box">
                <label class="q-item-title">${item_index+1}.&nbsp;${item.title!}</label>
                <label class="q-item-score">【${item.qscore!}分】</label>
            </div>
            <div class="q-item-op-box">
                <#list item.questionOptionList as option>
                    <div class="q-item-op">
                        <#if item.qtype==0 || item.qtype==1>
                            <input class="q-item-op-ipt" id="lable-i${option.id!}"
                                   name="q-item-op-i${item.id!}" value="${option.id!}" type="radio"/>
                        </#if>
                        <#if item.qtype==2>
                            <input class="q-item-op-ipt" id="lable-i${option.id!}"
                                   name="q-item-op-i${item.id!}" value="${option.id!}" type="checkbox"/>
                        </#if>
                        <label class="q-item-op-label" for="lable-i${option.id!}">
                            ${option.title!}
                        </label>
                    </div>
                </#list>
            </div>
            <div>
                答案：${item.answer!}
            </div>
        </div>
    </#list>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
