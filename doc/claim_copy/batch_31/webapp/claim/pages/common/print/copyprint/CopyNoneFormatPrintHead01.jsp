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
<%@include file="CopyNoneFormatPrintHead01Ini.jsp"%>
<html>
<head></head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="5" align="center" style="font-size: 14pt;">
				<b> 财产保险有限公司&nbsp;&nbsp;<%=strRiskName%>承保理赔信息
				</b>
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="4" />
			</td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<%-- <tr><td colspan="5">报案号：<%= strRegistNo %></td></tr>--%>
		<tr>
			<td colspan="5">
				保险单号：<%=strPolicyNo%></td>
		</tr>
		<tr>
			<td colspan="3">
				总保额：<%=dbSumAmount%></td>
			<td colspan="2">
				总保费：<%=dbSumpremium%></td>
		</tr>
		<tr>
			<td colspan="5">
				被保险人名称：<%=strPolicyName%></td>
		</tr>
		<tr>
			<td colspan="5">
				联系电话：<%=strPolicyPhonenumber%></td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td colspan="5">
				报案号：<%=strRegistNo%></td>
		</tr>
		<tr>
			<td colspan="5">
				保险单号：<%=strPolicyNo%></td>
		</tr>
		<tr>
			<td colspan="5">
				被保险人名称：<%=strPolicyName%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="5">
				保险地址：1、<%=strAddressName[0]%></td>
		</tr>
		<%
			for (i = 1; i < strAddressName.length; i++) {
		%>
		<tr>
			<td colspan="5" align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=i + 1%>、<%=strAddressName[i]%></td>
		</tr>
		<%
			}
		%>
		<%--<tr><td colspan="5"> 赔偿限额：每次事故赔偿限额:</td></tr>
	<tr><td align="right">其中：</td><td colspan="4">财产损失赔偿限额： </td></tr>
	<tr><td > </td><td colspan="4">人身伤亡赔偿限额：<%=strCurrency05%> <%=strLimit05%> </td></tr>
	<tr><td > </td><td colspan="4">每人赔偿限额：<%=strCurrency04%> <%=strLimit04%> </td></tr>
	
	<tr><td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;利损险总保额：<%=strCurrency%><%=dbAmount100%></td></tr>

	<tr><td colspan="5">每次事故绝对免赔：<%=strCurrency07%> <%=strLimit06%> </td></tr>
	--%>
		<tr>
			<td colspan="3">
				投保日期：<%=OperateDate%></td>
			<td colspan="2">
				輸入日期：<%=UnderWriteEndDate%></td>
		</tr>
		<tr>
			<td colspan="3">
				签单日期：<%=SignDate%></td>
			<td colspan="2">
				出单日期：<%=InputDate%></td>
		</tr>
		<tr>
			<td colspan="5">
				保险期限：从<%=StartDate%>
				至
				<%=EndDate%></td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="5">
				归属机构名称：<%=strComcname%></td>
		</tr>
		<tr>
			<td colspan="5">免赔信息：</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td colspan="5">
				是否涉及联保、共保：<%=strCoinsFlag%></td>
		</tr>
		<tr>
			<td valign="top" colspan="5">
				<b>特别约定：</b><%=strEngage%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>