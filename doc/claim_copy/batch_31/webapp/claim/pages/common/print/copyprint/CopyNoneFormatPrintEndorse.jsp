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
<%@include file="CopyNoneFormatPrintEndorseIni.jsp"%>
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
				<b>批改情况：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" style="font-family: 宋体; font-size: 11pt;">
					<tr>
						<td align="center" width="30%">批单号</td>
						<%
							if ("CopyPrintNew".equals(printType)) {
						%>
						<td align="center" width="30%">批改内容</td>
						<%
							} else {
						%>
						<td align="center" width="30%">批改原因</td>
						<%
							}
						%>
						<td align="center" width="20%">批改时间</td>
						<td align="center" width="20%">核保人</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr width="90%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strEndorseNo.length; i++) {
					%>
					<tr>
						<td align="center"><%=strEndorseNo[i]%></td>
						<%
							if ("CopyPrintNew".equals(printType)) {
						%>
						<td align="center"></td>
						<%
							} else {
						%>
						<td align="center"><%=strEndorType[i]%></td>
						<%
							}
						%>
						<td align="center"><%=strEndorDate[i]%></td>
						<td align="center"><%=strUnderWriteName[i]%></td>
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