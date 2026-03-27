<%--

****************************************************************************

* DESC       ：意健险不予立案通知书　

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-8-17

* MODIFYLIST ：   id       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************/

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	DateTime currentDate = new DateTime().current();
%>
<%-- 初始化 --%>
<%@include file="AcciNotClaimNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>意健险不予立案通知书</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 16pt;">
				<B> 不予立案通知书 <B>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: right; font-family: 宋体; font-size: 10pt;" id="tdRregistNo">报案号:</td>
		</tr>
		<tr>
			<td id="tdContext" colspan=2 align=center>
				<textarea style="wrap: hard; width: 80%" rows="15" cols="80" class=readonlyWhite readonly style="overflow:hidden;"><%=strContext%></textarea>
			</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
