<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔案件报告书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAACanceltransNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><s:text name="title.printBeforeEdit.vehicleReportPrint1" /></title>
<%-- 机动车辆保险拒赔案件报告书打印 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B><s:text name="print.vehicleReport" /><B> <%-- 机动车辆保险拒赔案件报告书 --%>
			</td>
		</tr>
		<tr>
			<td align=left id="tdCompany" width="50%" style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.completeUnit" />：
				<%-- 填报单位（签章） --%>
			</td>
			<td align=right id="tdClaimNo" width="50%" style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.caseNo" />：
				<%-- 立案编号 --%>
			</td>
		</tr>
	</table>
	<br>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td align="center" height="28" width="10%">
				<s:text name="db.prpLregist.insuredName" />
			</td>
			<%-- 被保险人--%>
			<td height="28" width="35%" id="tdInsuredName">&nbsp;</td>
			<td align="center" height="28" width="10%">
				<s:text name="print.policyNo" />
			</td>
			<%-- 保险单号 --%>
			<td height="28" width="45%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" height="25">
				<s:text name="db.prpLlawsuit.licenseNo" />
			</td>
			<%-- 号牌号码 --%>
			<td height="28" id="tdLicenseNo">&nbsp;</td>
			<td align="center" height="28">
				<s:text name="db.prpLCItemCar.brandName" />
			</td>
			<%-- 厂牌型号 --%>
			<td height="28" id="tdBrandName">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" height="28">
				<s:text name="regist.prpLregist.sumAmount" />
			</td>
			<%-- 保险金额 --%>
			<td height="28" id="tdSumAmount1">&nbsp;</td>
			<td align="center" height="28" rowSpan=2>
				<s:text name="regist.prpLregist.insuranceTime" />
			</td>
			<%-- 保险期间 --%>
			<td height="28" rowSpan=2 id="tdInsuredDate"></td>
		</tr>
		<tr>
			<td align="center" height="28">
				<s:text name="print.imitatLiability" />
			</td>
			<%-- 责任限额 --%>
			<td height="28" id="tdSumAmount2">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" height="28">
				<s:text name="print.dangerPlant" />
			</td>
			<%-- 出险险种 --%>
			<td height="28" id="tdRiskName">&nbsp;</td>
			<td align="center" height="28">
				<s:text name="db.prpLregist.estimateLoss" />
			</td>
			<%-- 估损金额 --%>
			<td height="28" colSpan=2 id="tdEstimateLoss">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" height="28">
				<s:text name="regist.prpLregist.damageTime" />
			</td>
			<%-- 出险时间 --%>
			<td height="28" id="tdDamageStartDate">&nbsp;</td>
			<td align="center" align="center" height="28">
				<s:text name="regist.prpLregist.damageAddress" />
			</td>
			<%-- 出险地点 --%>
			<td height="28" id="tdDamageAddress">&nbsp;</td>
		</tr>
		<tr>
			<td height="160" valign=top colSpan=4 id="tdContext">&nbsp;</td>
		</tr>
		<tr>
			<td height="160" valign=top colSpan=4>
				&nbsp;
				<s:text name="print.insurantOpinion" />：
			</td>
			<%-- 被保险人意见及反映 --%>
		</tr>
		<tr>
			<td height="160" valign="top" colSpan=4>
				<table width="100%" height="100%" border=0 style="font-family: 宋体; font-size: 10pt;">
					<tr height="80%">
						<td valign="top" colspan="4">
							&nbsp;
							<s:text name="print.rejectClaimsCompensat" />：
						</td>
						<%-- 注销、拒赔或赔偿意见 --%>
					</tr>
					<tr height="10%">
						<td>
							<s:text name="print.managerSign" />：
						</td>
						<%-- 经理签字 --%>
						<td>
							<s:text name="print.directorSign" />：
						</td>
						<%-- 主管签字 --%>
						<td>
							<s:text name="print.divisionSign" />：
						</td>
						<%-- 核赔师签字 --%>
						<td>
							<s:text name="certify.agentSignature" />：
						</td>
						<%-- 经办人签字 --%>
					</tr>
					<tr height="10%">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="160" valign=top colSpan=4>
				<table width="100%" height="100%" border=0 style="font-family: 宋体; font-size: 10pt;">
					<tr height="80%">
						<td valign="top" colspan="4">
							&nbsp;
							<s:text name="print.parentVompanyOpinion" />：
						</td>
						<%-- 上级公司审核意见 --%>
					</tr>
					<tr height="10%">
						<td>
							<s:text name="print.totaDirectManager" />：
						</td>
						<%-- 主管总（副）经理签字 --%>
						<td>
							<s:text name="print.legalDepartSignat" />：
						</td>
						<%-- 法律部门负责任签字 --%>
						<td>
							<s:text name="print.businessDirector" />：
						</td>
						<%-- 业务部门负责人签字 --%>
						<td>
							<s:text name="certify.agentSignature" />：
						</td>
						<%-- 经办人签字 --%>
					</tr>
					<tr height="10%">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:text name="print.year" />
							<%-- 年 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="print.month" />
							<%-- 月 --%>
							&nbsp;&nbsp;&nbsp;
							<s:text name="regist.prpLregist.date" />
							<%-- 日 --%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="50%" height="20" valign="bottom" colspan=2 id="tdUserName">
				<s:text name="print.fillPenson" />：
			</td>
			<%-- 填报人 --%>
			<td width="50%" height="20" valign="bottom" colspan=2 align="right" id="tdInputDate">
				<s:text name="print.fillTime" />：
			</td>
			<%-- 填报时间 --%>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
