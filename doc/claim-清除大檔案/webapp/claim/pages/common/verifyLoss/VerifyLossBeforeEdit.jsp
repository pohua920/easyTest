<%--
****************************************************************************
* DESC       ：录入核损前输入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title><s:text name="verifyLoss.EntryInformation" /></title>
<%-- 录入核损信息 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/verifyLossBeforeEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="certainLoss.inputReportNo" />
				</td>
			</tr>
			<%-- 输入报案号 --%>
			<tr>
				<td class='title2' align="center">
					<s:text name="prpLregist.registNo" />
					：
				</td>
				<%-- 报案号 --%>
				<td class='input2'>
					<input type=text name="RegistNo" class="common">
				</td>
			</tr>
			<tr>
				<td class='button' colspan=2>
					<input type=submit class='button' value="<s:text name='button.next.value'/>">
					<%-- 下一步 --%>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html:html>
