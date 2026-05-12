<%--
****************************************************************************
* DESC       ：已决未决赔款画面
* AUTHOR     ： wangli 
* CREATEDATE ： 2005-05-31
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
<title><s:text name="title.compensate.haveOutstandingClaims" />
	<!-- 已决未决赔款 --></title>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table class="common">
		<tr class=listtitle>
			<td>
				<s:text name="prompt.queRegist.RegistNo" />
			</td>
			<!-- 报案号 -->
			<td>
				<s:text name="compensate.haveClaims" />
			</td>
			<!-- 已决赔款 -->
			<td>
				<s:text name="compensate.outstandingClaims" />
			</td>
			<!-- 未决赔款 -->
		</tr>
		<tr class=common>
			<td align=right>
				<bean:write name="compensateFeeDto" property="registNo" />
			</td>
			<td align=right>
				<bean:write name="compensateFeeDto" property="sumPaid" />
			</td>
			<td align=right>
				<bean:write name="compensateFeeDto" property="sumNoPaid" />
			</td>
		</tr>
	</table>
	<table class=common>
		<tr>
			<td align=center>
				<input type="button" value="<s:text name="button.close.value"/>" name='button_Peril_Close_Context' class="button" alt="关闭" onclick="window.close();">
				<!-- 关 闭 -->
			</td>
		</tr>
	</table>
</body>
</html:html>
