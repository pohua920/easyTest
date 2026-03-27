<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="com.sinosoft.claimprint.ui.dto.DAAPrpLCheckAddPrintDto"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLthirdPartyDto"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLdriverDto"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<html>
<head>
<title><s:text name="title.printBeforeEdit.motorVehicleSurvey" /></title>
<%-- 机动车辆保险事故现场查勘记录附页 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body>
	<%
		DAAPrpLCheckAddPrintDto dAAPrpLCheckAddPrintDto = (DAAPrpLCheckAddPrintDto) request.getAttribute("dAAPrpLCheckAddPrintDto");
		if (dAAPrpLCheckAddPrintDto.getPrpLthirdPartyDtoList().size() < 3) {
			String strMessage = "抱歉!第三方车辆不多於一辆,不需列印附页,请您列印现场查勘记录。";
	%>
	<jsp:include page="/common/pub/UIErrorPage.jsp">
		<jsp:param name="Picture" value="F" />
		<jsp:param name="Content" value="<%=strMessage%>" />
	</jsp:include>
	<%
		return;
		} else if (dAAPrpLCheckAddPrintDto.getPrpLthirdPartyDtoList().size() > 4) {
			String strMessage = "抱歉!第三方车辆多於三辆,无法列印附页,请您原谅。";
	%>
	<jsp:include page="/common/pub/UIErrorPage.jsp">
		<jsp:param name="Picture" value="F" />
		<jsp:param name="Content" value="<%=strMessage%>" />
	</jsp:include>
	<%
		return;
		}
	%>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="85%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B><s:text name="print.motorVehicleSurvey" /><br> </B>
				<%-- 机动车辆保险事故现场查勘记录附表 --%>
			</td>
		</tr>
	</table>
	<br>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="96%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td width="10%" height="40" style="font-family: 宋体; font-size: 10pt;">
				<s:text name="prpLregist.registNo" />：
				<%-- 报案号 --%>
			</td>
			<td width="23%" style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getRegistNo()%></td>
			<td width="17%" style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.insPolicyNo" />：
				<%-- 交强险保单号 --%>
			</td>
			<td width="24%" style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getCompelPolicyNo()%></td>
			<td width="17%" style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.insuRegistratNo" />：
				<%-- 交强险立案号 --%>
			</td>
			<td width="9%" style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getCompelClaimNo()%></td>
		</tr>
		<tr>
			<td style="font-family: 宋体; font-size: 10pt;">
				<s:text name="db.prpLregist.insuredName" />：
				<%-- 被保险人 --%>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getInsuredName()%></td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.commerInsuNo" />：
				<%-- 商业保险保单号 --%>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getBusinessPolicyNo()%></td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<s:text name="print.commercialInsureRecord" />：
				<%-- 商业保险立案号 --%>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;"><%=dAAPrpLCheckAddPrintDto.getBusinessClaimNo()%></td>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<tr>
			<td width="3%" height="100%" style="font-family: 宋体; font-size: 10pt;" rowspan="12" align=center>
				<b><s:text name="print.threeVehicles" /></b>
				<%-- 三者车辆 --%>
			</td>
			<td width="2%" align=center rowspan="4">
				<b>1</b>
			</td>
			<%
				for (int i = 1; i < dAAPrpLCheckAddPrintDto.getPrpLthirdPartyDtoList().size(); i++) {
					PrpLthirdPartyDto prpLthirdPartyDto = (PrpLthirdPartyDto) dAAPrpLCheckAddPrintDto.getPrpLthirdPartyDtoList().get(i);
					PrpLdriverDto prpLdriverDto;
					if (dAAPrpLCheckAddPrintDto.getPrpLdriverDtoList().size() > i) {
						prpLdriverDto = (PrpLdriverDto) dAAPrpLCheckAddPrintDto.getPrpLdriverDtoList().get(i);
					} else {
						prpLdriverDto = new PrpLdriverDto();
					}

					if (i != 1) {
			%>
		
		<tr>
			<td align=center rowspan="4">
				<b><%=i%></b>
			</td>
			<%
				}
			%>
			<td width="28%">
				<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
				<%-- 厂牌型号： --%>
				<%=prpLthirdPartyDto.getBrandName()%></td width="24%">
			<td>
				<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
				<%-- 车牌号码： --%>
				<%=prpLthirdPartyDto.getLicenseNo()%></td>
			<td colspan="2" width="43%">
				<s:text name="print.insPolicyNo" />：
				<%-- 交强险保单号 --%>
				<%
					if (prpLthirdPartyDto.getRiskCode().equals(ConstantCodes.RISKCODE_DAZ)) {
				%>
				<%=prpLdriverDto.getPolicyNo()%>
				<%
					}
				%>
			</td>
		</tr>
		<tr>
			<td>
				<s:text name="db.prpLsalvation.driverName" />：
				<%-- 驾驶员姓名 --%>
				<%=prpLdriverDto.getDriverName()%></td>
			<td colspan="2">
				<s:text name="db.prpCCarDriver.drivinglicenseno" />：&nbsp;&nbsp;&nbsp;&nbsp;
				<%-- 驾驶证号 --%>
				<%
					if (prpLdriverDto.getDrivingLicenseNo() != null && "".equals(prpLdriverDto.getDrivingLicenseNo())) {
				%>
				<%=prpLdriverDto.getDrivingLicenseNo()%>
				<%
					} else {
				%>
				□□□□□□□□□□□□□□□□□□
				<%
					}
				%>
			</td>
			<td>
				<s:text name="db.prpLCMain.startDate" />：
				<%-- 起保日期 --%>
				<%=prpLdriverDto.getPolicyNo()%></td>
		</tr>
		<tr>
			<td>
				<s:text name="db.prpCCarDriver.acceptLicenseDate" />： &nbsp;&nbsp;&nbsp;&nbsp;
				<%-- 初次领证日期 --%>
				<%=prpLdriverDto.getReceiveLicenseDate()%></td>
			<td>
				<s:text name="db.prpCCarDriver.sex" />
				<%-- 性别 --%>：&nbsp;&nbsp;&nbsp;&nbsp;□
				<s:text name="certainLoss.male " />
				<%-- 男 --%>
				&nbsp;&nbsp; □
				<s:text name="certainLoss.female" />
				<%-- 女 --%>
			</td>
			<td width="24%">
				<s:text name="db.prpCCarDriver.drivingCarType" />：
				<%-- 准驾车型 --%>
				<%=prpLdriverDto.getDrivingCarType()%></td>
			<td width="19%">
				<s:text name="print.contactWay" />：
				<%-- 联系方式 --%>
				<%=prpLdriverDto.getUnitAddress()%></td>
		</tr>
		<tr>
			<td>
				<s:text name="print.estimateLoss" />：
				<%-- 估计损失 --%>
				<%=dAAPrpLCheckAddPrintDto.getEstimateLoss()%></td>
			<td>
				<s:text name="print.labourApproved" />：
				<%-- 核定施救费 --%>
			</td>
			<td colspan="2">
				<s:text name="print.otherSituat" />：
				<%-- 其他情况 --%>
			</td>
		</tr>
		<%
			}
			if (dAAPrpLCheckAddPrintDto.getPrpLthirdPartyDtoList().size() == 3) {
		%>
		<tr>
			<td align=center rowspan="4">
				<b>3</b>
			</td>
			<td>
				<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
				<%-- 厂牌型号： --%>
			</td>
			<td>
				<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
				<%-- 车牌号码：--%>
			</td>
			<td colspan="2">
				<s:text name="print.insPolicyNo" />：
				<%-- 交强险保单号 --%>
			</td>
		</tr>
		<tr>
			<td>
				<s:text name="db.prpLsalvation.driverName" />：
				<%-- 驾驶员姓名 --%>
			</td>
			<td colspan="2">
				<s:text name="db.prpCCarDriver.drivinglicenseno" />
				：&nbsp;&nbsp;&nbsp;&nbsp;□□□□□□□□□□□□□□□□□□
				<%-- 驾驶证号 --%>
			</td>
			<td>
				<s:text name="db.prpLCMain.startDate" />：
				<%-- 起保日期 --%>
			</td>
		</tr>
		<tr>
			<td>
				<s:text name="db.prpCCarDriver.acceptLicenseDate" />：
				<%-- 初次领证日期 --%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.year" />
				<%-- 年 --%>
				&nbsp;&nbsp;
				<s:text name="print.month" />
				<%-- 月 --%>
				&nbsp;&nbsp;
				<s:text name="regist.prpLregist.date" />
				<%-- 日 --%>
			</td>
			<td>
				<s:text name="db.prpCCarDriver.sex" />：
				<%-- 性别 --%>
				&nbsp;&nbsp;&nbsp;&nbsp;□
				<s:text name="certainLoss.male" />
				<%-- 男 --%>
				&nbsp;&nbsp; □
				<s:text name="certainLoss.female" />
				<%-- 女 --%>
			</td>
			<td>
				<s:text name="db.prpCCarDriver.drivingCarType" />：
				<%-- 准驾车型 --%>
			</td>
			<td>
				<s:text name="print.contactWay" />：
				<%-- 联系方式 --%>
			</td>
		</tr>
		<tr>
			<td>
				<s:text name="print.estimateLoss" />：
				<%-- 估计损失 --%>
			</td>
			<td>
				<s:text name="print.labourApproved" />：
				<%-- 核定施救费 --%>
			</td>
			<td colspan="2">
				<s:text name="print.otherSituat" />：
				<%-- 其他情况 --%>
			</td>
		</tr>
		<%
			}
		%>
		<tr height="120">
			<td align=center colspan="2">
				<s:text name="print.threeCarProperty" />
				<%--三者车上财产 --%>
			</td>
			<td valign=top colspan="4">
				<br>
				<s:text name="print.lossConditRescue" />
				<%--损失状况及施救过程描述 --%>
			</td>
		</tr>
		<tr height="120">
			<td align=center colspan="2">
				<s:text name="print.LocalProperty" />
				<%-- 本车车上财产 --%>
			</td>
			<td valign=top colspan="4">
				<br>
				<s:text name="print.lossRescueDescription" />
				<%-- 损失状况及施救过程描述 --%>
			</td>
		</tr>
		<tr height="120">
			<td align=center colspan="2">
				<s:text name="print.allOtherProperty" />
				<%-- 三者其他财产 --%>
			</td>
			<td valign=top colspan="4">
				<br>
				<s:text name="print.lossRescueDescription" />
				<%-- 损失状况及施救过程描述 --%>
			</td>
		</tr>
		<tr height="120">
			<td align=center colspan="2">
				<s:text name="db.prpLcomponent.remark" />
				<%-- 备注 --%>
			</td>
			<td colspan="4">&nbsp;</td>
		</tr>
	</table>
	<!-- 按钮部分 -->
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
