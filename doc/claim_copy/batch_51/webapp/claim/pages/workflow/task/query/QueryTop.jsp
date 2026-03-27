<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-02-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<%
    pageContext.setAttribute("damageStartDate",request.getParameter("damageStartDate"));//查询结果页面该项有值
    pageContext.setAttribute("damageEndDate",request.getParameter("damageEndDate"));//查询结果页面该项有值
%>
<html locale="true">
<head>
<title><s:text name="title.wfLogBeforeEdit.nodeFlag" /><%--工作流节点状态 --%></title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script src="/claim/pages/workflow/task/js/WfLogStatusList.js"></script>
<script language="javascript">
function submitForm(field) {
    fm.pageNo.value = "1";
    fm.searchFlag.value = "true";
    // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
    field.disabled = true;
    fm.submit(); //提交
}
//确认是否要做简易赔案的操作
function checkQuickCase(registNo) { <%--请确认要把报案为'"+registNo+"'转为简易赔案並进行处理？--%>
    var message = "<s:text name='prompt.workFlow.checkQuickCase'/>'" + registNo + "'<s:text name='prompt.workFlow.checkQuickCase2'/>";
    if (window.confirm(message) == false) {
        return false;
    }
    return true;
}
$(document).ready(function(){
  	$(document).keydown(function (e) {
	    var e = e?e:window.event;
	    var code = e.keyCode?e.keyCode:e.which;
	    if (code == 13) {
	        $("#button").click();
	    }
	})
});
</script>
</head>
<body onload="ShowAlertMessage();">
    <input type="hidden" name="testMessage" class="common" value="${requestScope.swfLog.alertMessage}">
    <form name="fm" action="/claim/wfLogQuery.do" method="post" onSubmit="return validateForm(this);">
        <%@include file="/pages/workflow/task/query/QueryStatusTitle.jsp"%>