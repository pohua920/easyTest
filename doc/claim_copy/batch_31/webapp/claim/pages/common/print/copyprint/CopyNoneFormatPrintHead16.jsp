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
<%@include file="CopyNoneFormatPrintHead16Ini.jsp"%>
<html>
<head></head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="5" style="font-size: 14pt;" align="center">
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
		<tr></tr>
		<td colspan="5">
			保险单号：<%=strPolicyNo%></td>
		</tr>
		<tr></tr>
		<td colspan="5">
			被保险人名称：<%=strPolicyName%></td>
		</tr>
		<tr></tr>
		<td colspan="5">
			被保险人地址：<%=strPolicyAddress%></td>
		</tr>
		<tr>
			<td width="10%"></td>
			<td width="20%" align="center">产品名称</td>
			<td width="20%" align="center">产品规格类型</td>
			<td width="20%" align="center">预计销售额</td>
			<td width="20%" align="center">币种</td>
		</tr>
		<%
			for (i = 0; i < arrItemDetailName.length; i++) {
				if (arrItemFlag[i].substring(1, 2).equals("1")) {
		%>
		<tr>
			<td align="center"><%=i + 1%>、
			</td>
			<td align="center"><%=arrItemDetailName[i]%></td>
			<td align="center"><%=arrModel[i]%></td>
			<td align="center"><%=dbAmount[i]%></td>
			<td align="center"><%=arrCurrency[i]%></td>
		</tr>
		<%
			}
			}
		%>
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="5">
				预计销售额合计：<%=strCurrency%><%=dbAmountSum%></td>
		</tr>
		<tr>
			<td colspan="5">
				每次事故绝对免赔：<%=strCurrency05%><%=strLimit05%></td>
		</tr>
		<tr>
			<td colspan="5">
				销售網域：<%=strSaleArea%></td>
		</tr>
		<tr>
			<td colspan="5">
				承保網域：<%=strInsureArea%></td>
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
				追溯期：<%=bkWardStartDate%></td>
		</tr>
		<tr>
			<td colspan="5">
				是否涉及联保、共保：<%=strCoinsFlag%></td>
		</tr>
		<tr>
			<td valign="top">
				<b>特别约定：</b>
			</td>
			<td colspan="4"><%=strEngage%></td>
		</tr>
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td align="left" style="width: 25%">赔偿限额：</td>
			<td colspan="4">
				每次事故赔偿限额：<%=strCurrency01%><%=dbLimit01%></td>
		</tr>
		<tr>
			<td align="right">其中：</td>
			<td colspan="4">
				财产损失赔偿限额：<%=strCurrency03%><%=strLimit03%></td>
		</tr>
		<tr>
			<td align="right"></td>
			<td colspan="4">
				人身伤亡赔偿限额：<%=strCurrency07%><%=strLimit07%></td>
		</tr>
		<tr>
			<td align="right"></td>
			<td colspan="4">
				每次事故免赔额：<%=strCurrency02%><%=strLimit02%></td>
		</tr>
		<tr>
			<td align="right">保险期内累计赔偿限额：</td>
			<td colspan="4"><%=strCurrency%><%=new DecimalFormat("#,##0.00").format(dbSumAmount)%></td>
			<td></td>
		</tr>
	</table>
</body>
</html>