<%--
****************************************************************************
* DESC       ：机动车辆出险(盗抢)证明打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
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
<%
	//System.out.println("--------1---");
%>
<%@include file="DAAPilferNoneFormatPrintIni.jsp"%>
<%
	//System.out.println("--------2---");
%>
<html>
<head>
<title>机动车辆出险（盗抢）证明列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<B>机动车辆出险（盗抢）证明<B>
				</td>
			</tr>
			<tr>
				<td align=left id="tdRegistNo" style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
				<td align=right id="tdClaimNo" style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr>
				<td height="25" rowspan="8" align="center" width="4%">车辆保险信息</td>
				<td id="tdInsuredName" height="25" width="34%" align="left">被保险人：</td>
				<td id="tdLinkerName" height="25" colspan="2" align="left">联系人：</td>
				<td id="tdPhoneNumber" height="25" colspan="2" align="left">联系电话：</td>
			</tr>
			<tr>
				<td id="tdComName" height="25" width="34%" align="left">承保公司：</td>
				<td id="tdLinkerName1" height="25" colspan="2" align="left">联系人：</td>
				<td id="tdPhoneNumber1" height="25" colspan="2" align="left">联系电话：</td>
			</tr>
			<tr>
				<td id="tdBrandName" height="25" width="34%" align="left">厂牌型号：</td>
				<td id="tdLicenseNo" height="25" colspan="2" align="left">号牌号码：</td>
				<td id="tdColorCode" height="25" colspan="2" align="left">车身颜色：</td>
			</tr>
			<tr>
				<td id="tdEngineNo" height="25" width="34%" align="left">发动机号：</td>
				<td id="tdFrameNo" height="25" colspan="4" align="left">车架号（VIN）：</td>
			</tr>
			<tr>
				<td id="tdPurchasePrice" height="25" width="34%" align="left">新车购置价：</td>
				<td id="tdSeatCount" height="25" colspan="2" align="left">座位/吨位：</td>
				<td id="tdEnrollDate" height="25" colspan="2" align="left">车辆初次登记日期：</td>
			</tr>
			<tr>
				<td id="tdDriverName" height="25" width="34%" align="left">驾驶员姓名：</td>
				<td id="tdDrivingLicenseNo" height="25" colspan="4" align="left">驾驶证号码：</td>
			</tr>
			<tr>
				<td id="tdReceiveLicenseDate" height="25" colspan="2" align="left">初次领证日期：</td>
				<td id="tdDrivingCarType" height="25" colspan="3" align="left">准驾车型：</td>
			</tr>
			<tr>
				<td id="tdDamageStartDate" height="25" colspan="2" align="left">出险时间：</td>
				<td id="tdDamageAddress" height="25" colspan="2" align="left">出险地点：</td>
				<td id="tdLinkerPostCode" height="25" width="20%" align="left">邮政编码：</td>
			</tr>
			<tr>
				<td width="4%" align="center" height="156">车辆出险及报案经过</td>
				<td colspan="5" align="left" height="156">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="70%">
							<td id="tdRegistText" colspan="12" height="200" valign="top">
								<input type=text rows=18 cols=95 class=readonlyWhite readonly style="overflow: hidden; FONT-SIZE: 10pt">
							</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">被保险人签章（签字）：</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">签字人身份證字號：</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="4%" align="center" height="154">公安部门审核意见</td>
				<td colspan="5" align="left" height="154">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="70%">
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">证明机关签章：</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">经办人：</td>
						</tr>
						<tr height="25">
							<td width="70%">&nbsp;</td>
							<td align="left" width="30%">时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%-- include打印按钮 --%>
		<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
</html>
