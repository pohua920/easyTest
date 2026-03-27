<%--
**************************************************************************** 
* DESC       ：查勘调度处理页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-07-21
* MODIFYLIST ：   Name       Date            Reason/Contents
                
****************************************************************************
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<head>
<html:html locale="true">
<!--对title处理-->
<title><s:text name="title.scheduleBeforeEdit.attemperOperate" /></title>
<%--调度处理 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/schedule/js/DAAScheduleCheckEdit.js"></script>
</head>
<%
	String editType = request.getParameter("editType");
		////System.out.println(editType);
		if (editType.equals("SHOW") || editType.equals("DELETE")) {
%>
<body class="interface" onload="initPage();readonlyAllInput();disabledAllButton('buttonArea');">
	<%
		} else {
	%>

<body class=interface onload="initPage();">
	<%
		}
	%>
	<form name=fm action="/claim/scheduleSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<%-- 1.调度主信息头信息 --%>
		<%
			////System.out.println("-- 1.调度主信息头信息 --");
		%>
		<%@include file="/pages/DAA/schedule/DAAScheduleCheckMainEdit.jsp"%>
		<%-- 3.调度提示信息 --%>
		<%-- // @include file="/pages/DAA/claim/DAAClaimFeeEdit.jsp" --%>
		<%-- 4.报案信息补充说明 --%>
		<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
		<%-- 4.调度保存按钮 --%>
		<%@include file="/pages/DAA/schedule/DAAScheduleCheckSave.jsp"%>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
