<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔 /註銷报告页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-08
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
<%@include file="DAARefuseCancelReportNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>机动车辆保险拒赔 /註銷报告</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body bgcolor="#FFFFFF">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<span><img src="/claim/images/LOGO.jpg" width="405" height="65" /></span>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<strong><B>机动车辆保险拒赔 /註銷报告<B> </b></b></strong>
			</td>
		</tr>
	</table>
	<table width="92%" height="522" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: '宋体'; font-size: 10pt">
		<tr>
			<td height="25" colspan="2" align="center">被保险人</td>
			<td height="25" width="228">
				<div align="center"><%=strInsuredName%></div>
			</td>
			<td height="25">
				<div align="center">赔案号码</div>
			</td>
			<td height="25">
				<div align="center"><%=strClaimNo%></div>
			</td>
		</tr>
		<tr>
			<td height="25" colspan="2" align="center">保险期限</td>
			<td height="25">
				<div align="center"><%=strInsuredDate%></div>
			</td>
			<td height="25">
				<div align="center">厂牌车型</div>
			</td>
			<td height="25">
				<div align="center"><%=strBrandName%></div>
			</td>
		</tr>
		<tr>
			<td height="25" colspan="2" align="center" valign="middle">出险时间</td>
			<td height="25">
				<div align="center"><%=strDamageStartDate%></div>
			</td>
			<td height="25">
				<div align="center">车牌号码</div>
			</td>
			<td height="25">
				<div align="center"><%=strLicenseNo%></div>
			</td>
		</tr>
		<tr>
			<td height="25" colspan="2" align="center">出险地点</td>
			<td height="25" colspan="3">
				&nbsp;&nbsp;<%=strDamageAddress%></td>
		</tr>
		<tr>
			<td height="25" colspan="2" align="center">所负责任</td>
			<td height="25" colspan="3">
				<!--<p>□全部责任 □主要责任 □同等责任 □次要责任 □无责 </p>-->
				&nbsp;&nbsp;<%=strIndemnityDuty%></td>
		</tr>
		<tr>
			<td height="180" colspan="5" valign="top">
				&nbsp;案情介绍：<br>
				<br> &nbsp;&nbsp;&nbsp;<%=strContext%></td>
		</tr>
		<tr>
			<td height="180" colspan="5" valign="top">
				&nbsp;拒赔 /註銷理由：<br>
				<br> &nbsp;&nbsp;&nbsp;<%=strCancelReason%></td>
		</tr>
		<tr>
			<td height="25" colspan="2" valign="middle">
				<div align="center">拒赔 /註銷金额</div>
			</td>
			<td height="25" valign="top">&nbsp;</td>
			<td width="96" height="25" valign="middle">
				<div align="center">索赔金额</div>
			</td>
			<td width="239" height="25" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td height="25" colspan="2" valign="middle">
				<div align="center">初级核赔人</div>
			</td>
			<td height="25" valign="top">&nbsp;</td>
			<td height="25" valign="middle">
				<div align="center">中级核赔人</div>
			</td>
			<td height="25" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td width="49" height="41" align="center" valign="middle">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
					<tr>
						<td>
							<div align="center">分</div>
						</td>
					</tr>
					<tr>
						<td>
							<div align="center">公</div>
						</td>
					</tr>
					<tr>
						<td>
							<div align="center">司</div>
						</td>
					</tr>
				</table>
			</td>
			<td width="80" height="40" align="center" valign="middle">初级核赔人</td>
			<td height="80" align="center" valign="middle">
				□同意&nbsp;&nbsp;&nbsp;&nbsp;<br> □部分同意<br> □不同意&nbsp;&nbsp;
			</td>
			<td height="40" colspan="2" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td height="40" rowspan="2" align="center" valign="middle">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div align="center">总</div>
						</td>
					</tr>
					<tr>
						<td>
							<div align="center">公</div>
						</td>
					</tr>
					<tr>
						<td>
							<div align="center">司</div>
						</td>
					</tr>
				</table>
			</td>
			<td height="41" align="center" valign="middle">中级核赔人</td>
			<td height="80" align="center" valign="middle">
				□同意&nbsp;&nbsp;&nbsp;&nbsp;<br> □部分同意<br> □不同意&nbsp;&nbsp;
			</td>
			<td height="41" colspan="2" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td height="41" align="center" valign="middle">首席核赔人</td>
			<td height="80" align="center" valign="middle">
				□同意&nbsp;&nbsp;&nbsp;&nbsp;<br> □部分同意<br> □不同意&nbsp;&nbsp;
			</td>
			<td height="41" colspan="2" valign="top">&nbsp;</td>
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
