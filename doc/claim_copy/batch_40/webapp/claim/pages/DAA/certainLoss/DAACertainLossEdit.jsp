
<%--
****************************************************************************
* DESC       ：定损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<html:html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.certainLossBeforeEdit.editCertainLoss" /></title>
<!--定损登记-->
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/certainLoss/js/DAACertainLossEdit.js"></script>
<script src="/claim/DAA/certainLoss/js/DAACertainLossPersonEdit.js"></script>
<script src="/claim/DAA/certainLoss/js/DAACertainLossRepairComponentEdit.js"></script>
</head>
<%
	String editType = request.getParameter("editType");
		//System.out.println(editType);
		if (editType.equals("SHOW")) {
%>
<body onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
	<%
		} else {
	%>

<body onload="initPage();initSet();">
	<%
		}
	%>
	<form name="fm" action="/claim/certainLossSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<%-- 1.定损/代定损主信息 --%>
		<%@include file="/pages/DAA/certainLoss/DAACertainLossMainEdit.jsp"%>
		<%-- 保存通用按钮 --%>
		<%@include file="/pages/DAA/certainLoss/DAACertainLossSave.jsp"%>
		<%--<%@include file="/pages/DAA/certainLoss/DAACertainLossPrint.jsp"%>--%>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<%@include file="/common/meta_js.jsp"%>
</html:html>
