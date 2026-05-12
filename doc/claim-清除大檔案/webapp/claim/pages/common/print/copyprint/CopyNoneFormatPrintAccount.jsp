<%--
****************************************************************************
* DESC       ：出险後抄单打印保费到帳信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintAccountIni.jsp"%>
<html>
<head>
</head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="2">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>保费到帳情况：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center" style="font-family: 宋体; font-size: 11pt;">
					<tr>
						<td align="left">付款期数</td>
						<td align="left">付款金额</td>
						<td align="left">付款日期</td>
					</tr>
					<%
						for (i = 0; i < strPlanEndorNo.length; i++) {
					%>
					<tr>
						<td align="left"><%=iPlanNo[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbRealFee[i])%></td>
						<td align="left"><%=strPayDate[i]%></td>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>