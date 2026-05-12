<%--

****************************************************************************

* DESC       ：意健险调查报告打印

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-6-16

* MODIFYLIST ：   id       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************/

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="AcciCheckNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>保险理赔调查报告</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>保险理赔调查报告 <B>
			</td>
		</tr>
	</table>
	<table border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="50%" colspan="3" id="tdPolicyNo">保单号码:</td>
			<td width="50%" colspan="3" id="tdRegistNo">案件号:</td>
		</tr>
		<tr>
			<td colspan="2" width="33%" id="tdInsuredName">被保险人姓名：</td>
			<td colspan="2" width="33%" id="tdDamageTypeName">事故类型：</td>
			<td colspan="2" width="34%" id="tdDamageStartDate">事故日期：</td>
		</tr>
		<tr>
			<td colspan='6'><%=strDate%></td>
		</tr>
		<tr>
			<td width="50%" colspan="3" id="tdCheckNature">调查方式:</td>
			<td width="50%" colspan="3" id="tdCheckerCode">调查员:</td>
		</tr>
		<tr>
			<td colspan='6'>调查描述:</td>
		</tr>
		<tr>
			<td colspan='6'><%=strContext%></td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='6'>&nbsp;</td>
		</tr>
		<!--include打印按钮-->
		<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
