<%--

************************************************************

*DESC          : 联共保理赔赔款分摊计算书打印

*AUTHOR        : guoxu

*CREATEDATE    : 2005-12-28

*MODIFYLIST    :   id       Date            Reason/Contents

*              ---------------------------------------------

************************************************************

--%>
<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.util.*"%>
<!--初始化页面-->
<%@include file="CoinsCompensatePrintIni.jsp"%>
<html>
<head>
<title>联共保理赔赔款分摊计算书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<style>
body {
	font-size: 12pt;
}
</style>
</head>
<body bgcolor="#FFFFFF" style="font-size: 10pt;">
	<hr width="96%">
	<h3 align="center">财产保险有限公司联、共保赔款分摊计算书</h3>
	<table width="93%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr align="center">
			<td colspan=1 style="font-size: 10pt;"><%=coinsFlag%></td>
		</tr>
	</table>
	<br>
	<table width="93%" align="center" cellspacing="0" cellpadding="0" border="0">
		<%
			for (int n = 0; n < j; n++) {
		%>
		<tr>
			<td colspan=1 width="18%" style="font-size: 10pt;">
				联、共保方
				<%=n + 1%>:<%=strCoinsName[n]%></td>
			<td colspan=1 width="12%" style="font-size: 10pt;">
				份额:<%=dbCoinsRate[n]%>%
			</td>
			<%
				sumCoinsRate += dbCoinsRate[n];
			%>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				应支付赔款金额:<%=strCurrency[n]%><%=coinsSumPaid0[n]%>
			</td>
			<%
				sumCoinsPaid0 += coinsSumPaid0[n];
			%>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				应支付费用金额:<%=strCurrency[n]%><%=coinsSumPaid1[n]%>
			</td>
			<%
				sumCoinsPaid1 += coinsSumPaid1[n];
			%>
			<%
				sumAllPaid = sumCoinsPaid0 + sumCoinsPaid1;
			%>
			<td colspan=1 width="20%" style="font-size: 10pt;">
				两项小计:<%=strCurrency[n]%><%=coinsSumPaid0[n] + coinsSumPaid1[n]%>
			</td>
		</tr>
		<tr>
			<td colspan=1 width="18%" style="font-size: 10pt;"></td>
			<td colspan=1 width="12%" style="font-size: 10pt;"></td>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				保单号码:<%=strPolicyNo[n]%>
			</td>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				立案号码:<%=strClaimNo%>
			</td>
			<td colspan=1 width="20%" style="font-size: 10pt;"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<%
			}
		%>
	</table>
	<hr width="96%">
	<table width="93%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan=1 width="18%" style="font-size: 10pt;"></td>
			<td colspan=1 width="12%" style="font-size: 10pt;">
				份额合计:<%=sumCoinsRate%>%
			</td>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				应支付赔款金额合计:<%=strCurerncy1%><%=sumCoinsPaid0%></td>
			<td colspan=1 width="25%" style="font-size: 10pt;">
				应支付费用金额合计:<%=strCurerncy1%><%=sumCoinsPaid1%></td>
			<td colspan=1 width="20%" style="font-size: 10pt;">
				赔款费用总计:<%=strCurerncy1%><%=sumAllPaid%></td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<table width="93%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr align="right">
			<td style="font-size: 10pt;">
				财产保险有限公司
				<%=comName%>
			</td>
		</tr>
		<tr align="right">
			<td style="font-size: 10pt;"><%=mDateTime%></td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>