<%--
****************************************************************************
* DESC       ：机动车辆保险索赔申请书页面
* AUTHOR     ：hanliang
* CREATEDATE ：2005-12-14
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="FreightClaimApplyNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>索赔申请书列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<span><img src="/claim/images/LOGO.jpg" /></span>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<strong><B>索赔申请书<B> </b></b></strong>
			</td>
		</tr>
	</table>
	<table width="92%" border="1" align="center" cellspacing="0" cellpadding="2" bordercolor="#111111" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt">
		<tr align="center">
			<td width="10%" height="30">被保险人</td>
			<td colspan="5"><%=strInsuredName%></td>
			<td width="15%">要保人</td>
			<td colspan="3"><%=strAppliName%></td>
		</tr>
		<tr align="center">
			<td height="30">保险单号码</td>
			<td colspan="5"><%=strPolicyNo%></td>
			<td>批单号码</td>
			<td colspan="3"><%=strEndorseNo%></td>
		</tr>
		<tr align="center">
			<td height="30">险别</td>
			<td colspan="3"><%=strRiskCode%></td>
			<td colspan="2">货物名称</td>
			<td colspan="2"></td>
			<td width="10%">数量</td>
			<td width="20%"></td>
		</tr>
		<tr align="center">
			<td height="30">型号</td>
			<td colspan="3"><%=strBrandNamethird%></td>
			<td colspan="2">运单号码</td>
			<td colspan="2"><%=carryBillNo%></td>
			<td>保险金额</td>
			<td><%=strSumAmount%></td>
		</tr>
		<tr align="center">
			<td height="30">运输路线</td>
			<td colspan="9"><%=strTPath%></td>
		</tr>
		<tr align="center">
			<td height="30">运输工具</td>
			<td width="10%"><%=bLNo%></td>
			<td width="6%">牌号</td>
			<td colspan="2"><%=strbLName%></td>
			<td width="5%">航次</td>
			<td><%=strVoyageNo%></td>
			<td width="10%">出险地点</td>
			<td colspan="2"><%=strDamageAddress%></td>
		</tr>
		<tr align="center">
			<td height="30">起运日期</td>
			<td colspan="2"><%=strSailStartDate%></td>
			<td colspan="2">出险日期</td>
			<td colspan="2"><%=strdamageStartDate%></td>
			<td>损失原因</td>
			<td colspan="2"><%=strDamageName%></td>
		</tr>
		<tr align="center">
			<td height="30">损失金额</td>
			<td colspan="2"></td>
			<td colspan="2">事务处理机关</td>
			<td colspan="2"><%=strHandleUnitName%></td>
			<td>事故责任</td>
			<td colspan="2"><%=strIndemnityDutyRate%></td>
		</tr>
		<tr align="left" valign="top">
			<td height="400" colspan="10">出险主要原因及施救处理情况：</td>
		</tr>
		<tr align="left" valign="top">
			<td height="100" colspan="10">
				<p>兹声明本人所填上述资料为真实情形，否则，愿放弃保险单之一切权利並承担相应的法律责任。</p>
				<p>&nbsp;</p>
				<p>被保险人（报案人）签章： 联系电话： 年 月 日</p>
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			divButton.style.display = "none";
			window.print();
		}
	</script>
</body>
</html>
