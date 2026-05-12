<%--

****************************************************************************

* DESC       ：机动车辆保险赔款计算书打印页面

* AUTHOR     ：luqin

* CREATEDATE ：2005-6-8

* MODIFYLIST ：   id       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************/

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="ClaimDisposeReportNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>赔案处理报告</title>
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<h1 align="center">赔&nbsp;案&nbsp;处&nbsp;理&nbsp;报&nbsp;告</h1>
	<table width="80%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" border=1 bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td valign="middle" height="23">
				<b>赔案编号:</b>
			</td>
			<td id="tdCaseNo" height="25" colspan="7">&nbsp;1</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>保&nbsp;单&nbsp;号:</b>
			</td>
			<td id="tdPolicyNo" height="25" colspan="7">&nbsp;2</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>险&nbsp;&nbsp;别:</b>
			</td>
			<td id="tdRiskCode" height="25" colspan="7">&nbsp;3</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>保&nbsp;&nbsp;额:</b>
			</td>
			<td id="tdSumAmount" height="25" colspan="7">&nbsp;4</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>保险期间</b>
			</td>
			<td id="tdInsuredDate" height="25" colspan="7">&nbsp;5</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>我方联共保比例</b>
			</td>
			<td id="tdCoins" height="25" colspan="7">&nbsp;5</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>被保险人的名称和地址:</b>
			</td>
			<td id="tdInsuredName" height="25" colspan="7">&nbsp;6</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>出险地点:</b></b>
			</td>
			<td id="tdDamageAddress" height="25" colspan="7">&nbsp;7</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>出险日期和时间:</b>
			</td>
			<td id="tdDamageStartDate" height="25" colspan="7">&nbsp;8</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>免赔额/免赔</b>
			</td>
			<td id="tdIndemnityDuty" height="25" colspan="7">&nbsp;9</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>索赔金额:</b>
			</td>
			<td id="tdSumClaim" height="25" colspan="7">&nbsp;10</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>赔付金额:</b>
			</td>
			<td id="tdSumDefLoss" height="25" colspan="7">&nbsp;11</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>结案金额</b>:</b>
			</td>
			<td id="tdSumPaid" height="25" colspan="7">&nbsp;12</td>
		</tr>
	</table>
	<table width="80%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td valign="middle" height="23">
				<b>一.赔保险人概况</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>二.事故经过及原因调查</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>三.保单责任</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>四.足额投保</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>五.是否存在重复投保及向第三者追偿前景</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>六.索赔及定损</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">
				<b>七.总结和赔付</b>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" height="23">缮制人：</td>
			<td>覆核人：</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>