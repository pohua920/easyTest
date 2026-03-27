<%--
****************************************************************************
* DESC       ：出险後抄单打印头信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-16
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintRegistInfoIni.jsp"%>
<html>
<head></head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>报案信息：</b>
			</td>
		</tr>
		<td colspan="5">
			报案号：<%=strRegistno%></td>
		<tr>
			<td colspan="2">
				报案时间：<%=strRegistReportDate%></td>
			<td colspan="2">
				联系人：<%=strRregistLinkerName%>
			<td colspan="1">
				联系电话：<%=strRegistPhoneNumber%></td>
		</tr>
		<tr>
			<td colspan="2">
				出险时间：<%=strRegistDamageStartDate%>
			<td colspan="2">
				出险地点：<%=strRegistDamageAddress%>
			<td colspan="1">
				出险原因：<%=strRegistDamageCode%></td>
		</tr>
		<tr>
			<td colspan="5">
				报案内容摘要：<%=strRegistContext%></td>
		</tr>
	</table>
</body>
</html>