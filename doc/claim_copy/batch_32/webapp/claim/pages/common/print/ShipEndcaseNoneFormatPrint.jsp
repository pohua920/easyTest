<%--

****************************************************************************

* DESC       ：货运险赔案终结报告书　

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-6-16

* MODIFYLIST ：   id       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************/

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%--@include file="PropCompensateNoticeNoneFormatPrintIni.jsp"--%>
<html>
<head>
<title>货运险赔案终结报告书</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 16pt;">
				<B>货运险赔案终结报告书 <B>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: right; font-family: 宋体; font-size: 10pt;">
				案号:
				<bean:write name='prpLclaimDto' property='claimNo' filter='true' />
			</td>
		</tr>
	</table>
	<table border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="20%">被保险人</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='insuredName' filter='true' />
			</td>
			<td width="20%">总公司案号</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='caseNo' />
			</td>
		</tr>
		<tr>
			<td width="20%">货物名称</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='lossName' filter='true' />
			</td>
			<td width="20%">保单号码</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='policyNo' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">保险金额</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='sumAmount' filter='true' format="##0.00" />
			</td>
			<td width="20%">预约协议号</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td width="20%">承保险别</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='damageKind' filter='true' />
			</td>
			<td width="20%">估损金额</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='currency' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">出险日期</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='damageStartDate' filter='true' />
				日
			</td>
			<td width="20%">出险地点</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='damageAddress' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">出险原因</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='damageName' filter='true' />
			</td>
			<td width="20%">承运工具</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td width="20%">承运人</td>
			<td width="30%"></td>
			<td width="20%">赔付金额</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='currency' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">追回金额</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='sumReplevy' filter='true' />
			</td>
			<td width="20%">结案日期</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='endCaseDate' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">分保比例</td>
			<td width="30%">
				<bean:write name='prpLclaimDto' property='indemnityDutyRate' filter='true' />
			</td>
		</tr>
		<tr>
			<td width="20%">共损理算人</td>
			<td colspan=3></td>
		</tr>
		<tr>
			<td width="20%">救助担保人</td>
			<td colspan=3></td>
		</tr>
		<!--include打印按钮-->
		<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
