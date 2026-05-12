<%--
****************************************************************************
* DESC       ：机动车辆保险结案催告、注销通知书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAAPressnoticeNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险结案催告通知书<B>
			</td>
		</tr>
		<tr height="30">
			<td align=left id="tdCompany" width="50%" style="font-family: 宋体; font-size: 10pt;">填报单位（签章）：</td>
			<td align=right id="tdClaimNo" width="50%" style="font-family: 宋体; font-size: 10pt;">立案编号：</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td colspan=2 align="center" height="30" width="10%">被保险人</td>
			<td height="30" width="35%" id="tdInsuredName">&nbsp;</td>
			<td align="center" height="30" width="10%">保险单号</td>
			<td height="30" width="45%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="25">号牌号码</td>
			<td height="30" id="tdLicenseNo">&nbsp;</td>
			<td align="center" height="30">厂牌型号</td>
			<td height="30" id="tdBrandName">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="30">保险金额</td>
			<td height="30" id="tdSumAmount1">&nbsp;</td>
			<td align="center" height="30" rowSpan=2>保险期间</td>
			<td height="30" rowSpan=2 id="tdInsuredDate"></td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="30">责任限额</td>
			<td height="30" id="tdSumAmount2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="30">出险险种</td>
			<td height="30" id="tdRiskName">&nbsp;</td>
			<td align="center" height="30">估损金额</td>
			<td height="30" id="tdEstimateLoss"></td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="30">出险时间</td>
			<td height="30" id="tdDamageStartDate">&nbsp;</td>
			<td align="center" height="30">出险地点</td>
			<td height="30" id="tdDamageAddress">&nbsp;</td>
		</tr>
		<tr height="360">
			<td width="5%">保险公司结案材料催告通知</td>
			<td colspan=4>
				<table border=0 width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr>
						<td colspan=2 id="tdInsuredName1">（被保险人名称）：</td>
					</tr>
					<tr>
						<td colspan=2>
							<br>&nbsp;&nbsp;&nbsp;&nbsp; 根据《保险法》和有关法律法规的规定，您以上所发生的保险事故即将超过索赔期限。 请您在接到本通知後10日内，尽快将本公司《机动车辆保险索赔须知》中要求的有关材料 送交本公司，否则，本公司将因无法履行赔偿责任而註銷该案件，您也会因此而丧失索赔 的权利。我们希望您在规定的期限内，尽快到本公司办理索赔手续。
						</td>
					</tr>
					<tr>
						<td colspan=2>
							&nbsp;&nbsp;&nbsp;&nbsp;谢谢您的理解与合作。<br> &nbsp;&nbsp;&nbsp;&nbsp;此致
						</td>
					</tr>
					<tr>
						<td width="70%">&nbsp;</td>
						<td width="30%">经办人：</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr height="350">
			<td width="5%">被保险人接到结案催告通知书签署的意见</td>
			<td colspan=4>
				<table border=0 width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr height="80%">
						<td colspan=2>&nbsp;</td>
					</tr>
					<tr height="28%">
						<td width="70%">&nbsp;</td>
						<td width="30%">被保险人签章：</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
