<%--
****************************************************************************
* DESC       ：财产险拒赔案件通知书列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="PropCancelNoticeNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>财产险拒赔案件通知书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<p align="center">
		<strong><font size="5"> 财产保险有限公司</font></strong>
	</p>
	<p align="center">
		<font size="6" face="隶书">拒 赔 案 件 通 知 书</font>
	</p>
	<table width="92%" border="0" align="center" cellspacing="0">
		<tr>
			<td width="20%" height="36">被保险人:</td>
			<td width="30%" id="tdInsuredName">&nbsp;</td>
			<td width="20%">赔案编号：</td>
			<td width="30%" id="tdCompensateNo">&nbsp;</td>
		</tr>
	</table>
	<table width="96%" border="1" align="center" cellspacing="0">
		<tr>
			<td width="20%" height="36">承保险别</td>
			<td width="30%" id="tdRiskCode">&nbsp;</td>
			<td width="20%">保单号码</td>
			<td width="30%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr>
			<td height="36">出险地点</td>
			<td id="tdDamageAddress">&nbsp;</td>
			<td>保险标的</td>
			<td id="tdLossName">&nbsp;</td>
		</tr>
		<tr>
			<td height="36">出险时间</td>
			<td id="tdDamageStartDate">&nbsp;</td>
			<td>保险金额</td>
			<td id="tdSumAmount">&nbsp;</td>
		</tr>
	</table>
	<table width="92%" border="0" align="center" cellspacing="0">
		<tr>
			<td height="36">
				<b>出险原因及损失情况</b>
			</td>
		</tr>
		<tr>
			<td colspan="8" height="175" valign="top" id="tdRegistText"></td>
		</tr>
		<tr>
			<td height="36">
				<b>拒赔理由</b>
			</td>
		</tr>
		<tr>
			<td colspan="8" height="175" valign="top" id="tdCancelReason"></td>
		</tr>
		<tr>
			<td width="60%" height="36" align="center">&nbsp;</td>
			<td width="20%" height="36" align="center">经办：</td>
			<td width="20%" height="36" align="center">签发：</td>
		</tr>
		<br>
		<br>
		<br>
		<tr>
			<td width="60%" height="36" align="center">&nbsp;</td>
			<td width="20%" height="36" align="center">签张：</td>
			<td width="20%" height="36" align="center">&nbsp;年&nbsp;月&nbsp;日：</td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<%-- include列印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
