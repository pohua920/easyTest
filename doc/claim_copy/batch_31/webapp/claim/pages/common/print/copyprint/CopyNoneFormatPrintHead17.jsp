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
<%@include file="CopyNoneFormatPrintHead17Ini.jsp"%>
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
		<tr></tr>
		<td>保险地址：</td>
		<td>1、</td>
		<td colspan="3"><%=arrKindAddress[0]%></td>
		<%
			for (i = 1; i < arrKindAddress.length; i++) {
		%>
		<tr>
			<td></td>
			<td><%=i + 1%>、
			</td>
			<td colspan="3"><%=arrKindAddress[i]%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="5">赔偿限额：</td>
		</tr>
		<tr>
			<td></td>
			<td>雇员分类</td>
			<td>人数</td>
			<td>死亡/伤残赔偿限额</td>
			<td>每人医药费用赔偿限额</td>
		</tr>
		<%
			for (i = 0; i < arrKindName.length; i++) {
				if (arrItemFlag[i].substring(1, 2).equals("1")) {
		%>
		<tr>
			<td><%=i + 1%></td>
			<td><%=arrModeName[i]%></td>
			<td><%=arrQuantity[i]%></td>
			<%
				if (arrItemCode[i].equals("0001")) {
			%>
			<td><%=dbAmount[i]%></td>
			<td></td>
			<%
				} else if (arrItemCode[i].equals("0002")) {
			%>
			<td></td>
			<td><%=dbAmount[i]%></td>
			<%
				}//if
			%>
		</tr>
		<%
			}
			}//for
		%>
		<tr>
			<td colspan="5">
				每次事故绝对免赔：<%=strCurrency05%>
				<%=strLimit05%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				投保日期：<%=OperateDate%></td>
			<td colspan="3">
				輸入日期：<%=UnderWriteEndDate%></td>
		</tr>
		<tr>
			<td colspan="2">
				签单日期：<%=SignDate%></td>
			<td colspan="3">
				出单日期：<%=InputDate%></td>
		</tr>
		<tr>
			<td colspan="5">
				保险期限：从<%=StartDate%>
				至
				<%=EndDate%></td>
		</tr>
		<tr>
			<td colspan="5">
				是否涉及联保、共保：<%=strCoinsFlag%></td>
		</tr>
		<tr>
			<td valign="top">
				<b>特别约定：</b>
			</td>
			<td colspan="4">
				<pre><%=strEngage%></pre>
			</td>
		</tr>
	</table>
</body>
</html>