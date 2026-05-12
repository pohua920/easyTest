<%--
****************************************************************************
* DESC       ：出险後抄单打印附加及特约信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintSub08Ini.jsp"%>
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
				<b>附加险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center" style="font-family: 宋体; font-size: 11pt;">
					<tr>
						<td align="left" width="50%">名称</td>
						<td align="left" width="30%">保额/限额</td>
						<td align="left" width="20%">每次赔偿限额</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
							if (strItemKindFlag[i].substring(1, 2).equals("2")) {
					%>
					<tr>
						<td align="left"><%=strKindName[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
						<td align="left"><%=dbDeductible[i]%></td>
					</tr>
					<%
						}
						}
					%>
				</table>
			</td>
		</tr>
		<!--      <tr>
        <td style="width:80px"><b>特别约定：</b></td>
        <td ><%=strEngage%> </td>
      </tr>
-->
	</table>
</body>
</html>