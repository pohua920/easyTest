<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<html:html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/compensate/js/DAACompensateEdit.js"></script>
<script src="/claim/DAA/compensate/js/DAAPersonLossEdit.js"></script>
</head>
<body class=interface onload="initPage();">
	<form name=fm action="/claim/compensateApprove.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<%-- 1.报案主信息 ??为什么和别的命名规则不一致？lixiang --%>
		<%@include file="/GAA/compensate/GAACompensateMainHeadEdit.jsp"%>
		<%-- 2.特别约定 --%>
		<%@include file="/GAA/compensate/GAACompensateCengage.jsp"%>
		<%-- 3.赔付标的信息 --%>
		<%@include file="/GAA/compensate/GAACompensateLlossEdit.jsp"%>
		<%-- 3.赔付人员信息 --%>
		<%@include file="/GAA/compensate/GAACompensatePersonLossEdit.jsp"%>
		<%-- 3.赔款费用 --%>
		<%@include file="/GAA/compensate/GAACompensateChargeEdit.jsp"%>
		<%-- 1.报案主信息 --%>
		<%@include file="/GAA/compensate/GAACompensateMainTailEdit.jsp"%>
		<%-- 4.理算报告 --%>
		<%@include file="/GAA/compensate/GAACompensateTextEdit.jsp"%>
		<%-- 保存通用按钮 --%>
		<table cellpadding="0" cellspacing="0" width="80%" style="display:">
			<tr>
				<td align="center">
					<input type="button" class="button" name="buttonApprove" value="<s:text name='button.nuclear.value'/>" onClick="approveSubmit();">
				</td>
				<%-- 复  核 --%>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
