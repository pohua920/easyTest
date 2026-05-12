<%--
****************************************************************************
* DESC       ：机动车辆保险结案报告书打印页面
* AUTHOR     ：zhaozhuo
* CREATEDATE ：2005-04-13
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
<%@include file="DAAEndcaseNoneFormatPrintIni.jsp"%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns="http://www.w3.org/TR/REC-html40">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm"></form>
	<p height="40" align=top style="font-family: 宋体; font-size: 14pt;">
	<h2 align="center">机动车辆保险结案报告书</h2>
	</p>
	<br>
	<div align="left">
		<table border="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="92%" id="AutoNumber1">
			<tr>
				<td width="33%" id="tdComName"></td>
				<td width="33%"></td>
				<td width="33%" id="tdClaimNo"></td>
			</tr>
			<tr>
				<td width="33%" id="tdInsuredName"></td>
				<td width="33%" id="tdPolicyNo"></td>
				<td width="33%" id="tdCaseNo"></td>
			</tr>
		</table>
	</div>
	<hr>
	<br>
	<br> &nbsp;&nbsp;&nbsp;
	<ins>
		&nbsp;<font size=2.4 id="tdDamageStartDate" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">，驾驶人员（姓名）：</font>
	<ins>
		&nbsp;<font size=2.4 valign="middle" width="30%" height="23" id="tdDriverName">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">（驾驶证号码：</font>
	<ins>
		&nbsp;<font size=2.4 valign="middle" width="30%" height="23" id="tdDrivingLicenseNo">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">；驾龄：</font>
	<ins>&nbsp;</ins>
	<font size=2.4text-align:center">；准驾车型：</font>
	<ins>
		&nbsp;<font size=2.4 valign="middle" width="30%" height="23" id="tdDrivingCarType">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">）驾驶(号牌号码：</font>
	<ins>
		&nbsp;<font size=2.4 valign="middle" width="30%" height="23" id="tdLicenseNo">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">；厂牌型号：</font>
	<ins>
		&nbsp;<font size=2.4 valign="middle" width="30%" height="23" id="tdBrandName">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">)车辆，因</font>
	<ins>
		&nbsp;<font size=2.4 id="tdDamageTypeName" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">原因发生</font>
	<ins>
		&nbsp;<font size=2.4 id="tdDamageName" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">（出险原因）事故，造成保险损失。</font>
	<br>
	<br> &nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">接到报案後，由</font>
	<ins>
		&nbsp;<font size=2.4 id="tdChecker1" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">和</font>
	<ins>
		&nbsp;<font size=2.4 id="tdChecker2" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">同志於</font>
	<ins>
		&nbsp;<font size=2.4 id="tdCheckDate" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">到</font>
	<ins>
		&nbsp;<font size=2.4 id="tdDamageAddress" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">（地点）进行了查勘。根据查勘情况以及有关证明材料，认定该事故属</font>
	<ins>
		&nbsp;<font size=2.4 id="tdRiskCode" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">（险种）保险责任。此次事故经</font>
	<ins>
		&nbsp;<font size=2.4 id="tdCheckUnitName" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">认定保险人负</font>
	<ins>
		&nbsp;<font size=2.4 id="tdIndemnityDuty" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">责任，保险人应承担</font>
	<ins>
		&nbsp;<font size=2.4 id="tdIndemnityDutyRate" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">％的责任。</font>
	<br>
	<br> &nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">本车承保情况如下：</font>
	<br>
	<table border="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="92%">
		<tr>
			<td width="20%">
				<font size=2.4 />要保人：
			</td>
			<td width="30%">
				<font size=2.4 /><%=prpCmainDto.getAppliName()%></td>
			<td width="20%">
				<font size=2.4 />被保险人：
			</td>
			<td width="30%">
				<font size=2.4 /><%=prpCmainDto.getInsuredName()%></td>
		</tr>
		<tr>
			<td>
				<font size=2.4 />签单日期：
			</td>
			<td>
				<font size=2.4 /><%=prpCmainDto.getOperateDate()%></td>
			<td>
				<font size=2.4 />保险期限：
			</td>
			<td>
				<font size=2.4 /><%=prpCmainDto.getStartDate()%>'+'至'+'<%=prpCmainDto.getEndDate()%></td>
		</tr>
		<tr>
			<td>
				<font size=2.4 />总保险金额：
			</td>
			<td>
				<font size=2.4 /><%=new DecimalFormat("#,##0.00").format(policyDto.getPrpCmainDto().getSumAmount())%></td>
			<td>
				<font size=2.4 />总责任限额：
			</td>
			<td>
				<font size=2.4 /><%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%></td>
		</tr>
		<tr>
			<td>
				<font size=2.4 />约定分期交费次数：
			</td>
			<td>
				<font size=2.4 /><%=prpCmainDto.getPayTimes()%></td>
			<td>
				<font size=2.4 />交费情况：
			</td>
			<td>
				<font size=2.4 /><%=prpCmainDto.getSumPremium()%></td>
		</tr>
		<tr>
			<td>
				<font size=2.4 />承保险种
			</td>
			<td colspan=3></td>
		</tr>
		<tr>
			<td colspan=4>
				<font size=2.4 /><%=strItemKindInfo%></td>
		</tr>
	</table>
	<br> &nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">被保险人应交保费：</font>
	<ins>
		&nbsp;<font size=2.4 id="tdPlanFee" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">元，已缴付：</font>
	<ins>
		&nbsp;<font size=2.4 id="tdFinishFee" height="25" width="23%">&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">元。</font>
	<br>
	<br> &nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">经过（第一现场 第二现场）定损，本次事故核定损失如下：（按险种列明）</font>
	<br>
	<table border="0" cellspacing="0" style="border-collapse: collapse" width="92%">
		<tr valign=top>
			<td>
				<font size=2.4 /><%=strContext1%></td>
		</tr>
	</table>
	<br> &nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">经计算，赔款建议如下：（按险种列明）</font>
	<table border="0" cellspacing="0" style="border-collapse: collapse" width="92%">
		<tr valign=top>
			<td>
				<font size=2.4 /><%=strContext2%></td>
		</tr>
	</table>
	&nbsp;&nbsp;&nbsp;
	<font size=2.4text-align:center">&nbsp;&nbsp;&nbsp;&nbsp;赔款总计：</font>
	<ins>
		<font size=2.4 id=tdSumPaid>&nbsp;</font>
	</ins>
	<font size=2.4text-align:center">元。</font>
	<br>
	<hr>
	<p style="text-align: right">
		<font size=2.4text-align:center">报告人：<%=operatorName%></font>
	</p>
	<p style="text-align: right">
		<font size=2.4 align=left><%=operateTime%></font>
	</p>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
</b>
</html>
