<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔/注销案件通知书打印页面
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%-- 初始化 --%>
<%@include file="DAAPressnoticeNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td align="center" colspan="2" height="40" style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height="40">
			<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险拒赔通知书<B>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<span style="font-family: 宋体; font-size: 11pt;"> 被保险人： <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</u> &nbsp;&nbsp;&nbsp;&nbsp;非常遗憾地通知您，根据 <u><%=strCancelReason%> </u> 的规定， 本公司&nbsp; <u>&nbsp;<%
 	if (ConstantCodes.KINDCODE_D_BZ.equals(prpItemKindDto.getKindCode())) {
 %> <%=StringConvert.encode(prpLclaimDto.getPolicyNo())%> <%
 	} else {
 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%
 	}
 %> &nbsp;
		</u>&nbsp; （交强险保单号）、 <u>&nbsp; <%
 	if (ConstantCodes.KINDCODE_D_BZ.equals(prpItemKindDto.getKindCode())) {
 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <%=StringConvert.encode(prpLclaimDto.getPolicyNo())%> <%
 	}
 %> &nbsp;
		</u>&nbsp;（商业保险保单号） 项下承保的 <u>&nbsp;&nbsp;<%=prpLthirdPartyDto.getLicenseNo()%>&nbsp;&nbsp;
		</u> （号牌号码）机动车辆於&nbsp;&nbsp; <%=strDamageStartDate%> 在 <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=strInsuredAddress%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</u> （出险地点）发生的事故损失不属於保险责任赔偿范围。对此本公司不能给予赔付，请予理解。
		</span>
	</table>
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td colspan="2" height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;欢迎您对本公司的工作提出意见。</td>
		</tr>
		<tr height="40">
			<td colspan="2" height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;此致</td>
		</tr>
		<tr height="40">
			<td colspan="2" height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr height="40">
			<td height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;被保险人签收：</td>
			<td height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;保险人（签章）：</td>
		</tr>
		<tr height="40">
			<td height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;月 &nbsp;&nbsp;日</td>
			<td height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;月 &nbsp;&nbsp;日</td>
		</tr>
	</table>
	<hr width=90%>
	<br>
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td height="40" align="left" style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;拒赔案件情况备注：</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
