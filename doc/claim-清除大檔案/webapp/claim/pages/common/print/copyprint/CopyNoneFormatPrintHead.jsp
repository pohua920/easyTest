<%--
****************************************************************************
* DESC       ：出险後抄单打印头信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<!--<%@include file="CopyNoneFormatPrintHeadIni.jsp"%>-->
<html>
<head>
</head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="2">
				报案号：<%=strRegistNo%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险单号：<%=strPolicyNo%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			被保险人名称：<%=strPolicyName%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			被保险人地址：<%=strPolicyAddress%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险金额：<%=strCurrency%><%=new DecimalFormat("#,##0.00").format(dbSumAmount)%></td>
		</tr>
		<tr></tr>
		<td colspan="2">保险标的：</td>
		</tr>
		<tr></tr>
		<td colspan="2">保险标的地址：</td>
		</tr>
		<tr></tr>
		<td colspan="2">免赔说明：</td>
		</tr>
		<tr></tr>
		<td>
			投保日期：<%=OperateDate%></td>
		<td>
			輸入日期：<%=UnderWriteEndDate%></td>
		</tr>
		<tr></tr>
		<td>
			签单日期：<%=SignDate%></td>
		<td>
			出单日期：<%=InputDate%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险期间：<%=StartDate%>
			到
			<%=EndDate%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			是否涉及联保、共保：<%=strCoinsFlag%></td>
		</tr>
	</table>
</body>
</html>