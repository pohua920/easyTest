<%--
****************************************************************************
* DESC       ：出险後抄单打印分保信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintReinsIni.jsp"%>
<html>
<head>
</head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="5">分保情况</td>
		</tr>
		<tr>
			<td>分保类别</td>
			<%
				if (dbShareRate0 != 0) {
			%>
			<td>法定分保</td>
			<%
				}
				if (dbShareRate2 != 0) {
			%>
			<td>合约分保</td>
			<%
				}
				if (dbShareRate3 != 0) {
			%>
			<td>临时分保</td>
			<%
				}
				if (dbShareRate1 != 0) {
			%>
			<td>自留比例</td>
			<%
				}
			%>
		</tr>
		<tr>
			<td>分保比例(%)</td>
			<%
				if (dbShareRate0 != 0) {
			%>
			<td><%=dbShareRate0%></td>
			<%
				}
				if (dbShareRate2 != 0) {
			%>
			<td><%=dbShareRate2%></td>
			<%
				}
				if (dbShareRate3 != 0) {
			%>
			<td><%=dbShareRate3%></td>
			<%
				}
				if (dbShareRate1 != 0) {
			%>
			<td><%=dbShareRate1%></td>
			<%
				}
			%>
		</tr>
		<!-- zhulei leave:联共保信息，待添加
<%if (strCoinsFlag.equals("共保") || strCoinsFlag.equals("主共保")) {
				//以下要打出共保信息：%>
      <tr><td></td></tr>
      <tr><td></td></tr>
<%}%>
-->
	</table>
</body>
</html>