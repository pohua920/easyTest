<%--

****************************************************************************

* DESC       ：赔款通知书

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-6-16

* MODIFYLIST ：   id       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************/

--%>

<%@ page contentType="text/html; charset=GBK" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 

<%@page import="com.sinosoft.claim.dto.domain.PrpLclaimDto"%> 



<%-- 初始化 --%>

<%
	DateTime currentDate = new DateTime().current();

	DateTime damageStartDate = new DateTime();

	PrpLclaimDto prpLclaimDto = (PrpLclaimDto) request.getAttribute("prpLclaimDto");

	String damageStartYear = "";

	String damageStartMonth = "";

	String damageStartDay = "";

	String damageStartHour = "";

	String damageStartMinute = "";

	if (prpLclaimDto != null) {

		damageStartDate = prpLclaimDto.getDamageStartDate();

		String strDamageStartDate = damageStartDate.toString();

		if (damageStartDate != null) {

			damageStartYear = strDamageStartDate.substring(0, strDamageStartDate.indexOf("-"));

			strDamageStartDate = strDamageStartDate.substring(strDamageStartDate.indexOf("-") + 1);

			damageStartMonth = strDamageStartDate.substring(0, strDamageStartDate.indexOf("-"));

			damageStartDay = strDamageStartDate.substring(strDamageStartDate.indexOf("-") + 1);

			damageStartYear = String.valueOf(damageStartDate.getYear());

			damageStartMonth = String.valueOf(damageStartDate.getMonth());

			damageStartDay = String.valueOf(damageStartDate.getDay());

		}

	}
%>
<html>
<head>
<title>赔 款 通 知 书</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 16pt;">
				<B> 财产保险有限公司 <B>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 12pt;">
				<B>赔&nbsp;&nbsp;款&nbsp;&nbsp;通&nbsp;&nbsp;知&nbsp;&nbsp;书 <B>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 10pt;">（由收款单位留存）</td>
		</tr>
	</table>
	<table border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="100%" colspan="3">
				尊敬的被保险人：
				<bean:write name='prpLclaimDto' property='insuredName' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="100%" colspan="3">
				&nbsp;&nbsp;&nbsp;您（单位）报来的
				<bean:write name='prpLclaimDto' property='riskCodeName' filter='true' />
				损案， 保险单号为
				<bean:write name='prpLclaimDto' property='policyNo' filter='true' />
				出险日期为
				<%=damageStartYear%>
				年
				<%=damageStartMonth%>
				月
				<%=damageStartDay%>
				日，出险地点为
				<bean:write name='prpLclaimDto' property='damageAddress' filter='true' />
				，此案本公司的立案编号为
				<bean:write name='prpLclaimDto' property='claimNo' filter='true' />
				。经本公司审核，现已结案，确定赔款人民币/美元：
				<bean:write name='prpLclaimDto' property='sumPaid' filter='true' />
				￥/ $ 上款请在所附赔款收据签章後前来领取。<br> &nbsp;&nbsp;&nbsp;<br> &nbsp;&nbsp;&nbsp;<br>
		</tr>
		<tr>
			<td width="100%" colspan="3" align='right'>
				保险人签章&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
			</td>
		</tr>
		<tr>
			<td width="100%" colspan="3" align='right'>
				日期
				<%=String.valueOf(currentDate.getYear())%>
				年
				<%=String.valueOf(currentDate.getMonth())%>
				月
				<%=String.valueOf(currentDate.getDay())%>
				日<br>
			</td>
		</tr>
		</tr>
		</tr>
		<!--include打印按钮-->
		<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
