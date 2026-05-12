<%@ page contentType="text/html; charset=GBK"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="com.sinosoft.claimciplatform.dto.custom.ReturnInfo"%>
<%@ page import="com.sinosoft.claimciplatform.dto.custom.Iconstants"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title><s:text name="title.comparisonBeforeEdit.comparisonDataResult" /></title>
<%-- 比对数据返回结果 --%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<%
		ReturnInfo returninfo = (ReturnInfo) request.getAttribute("returnInfo");
		if (returninfo != null && Iconstants.ReturnType.SUCCESS.equals(returninfo.getReturnType())) {
			if (returninfo.getCompareResult().equals("0")) {
				returninfo.setCompareResult("存在差异");
			} else {
				returninfo.setCompareResult("完全一致");
			}
	%>
	<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
		<tr>
			<td colspan=14 class="formtitle"><%=returninfo.getCompareDate()%><s:text name="comparison.insuranceDataResult" />
			</td>
		</tr>
		<%-- 交强险数据平台数据比对结果 --%>
		<tr>
			<td colspan=14 class="formtitle">
				<s:text name="comparison.platformConfirCases" />
			</td>
		</tr>
		<%-- 平台确认案件数 --%>
		<tr>
			<td class='title'>
				<s:text name="comparison.comparisonResult" />
			</td>
			<%-- 比对结果 --%>
			<td class='title'>
				<s:text name="comparison.insConfirmNum" />
			</td>
			<%-- 投保确认件数 --%>
			<td class='title'>
				<s:text name="comparison.cancelPolicyNum" />
			</td>
			<%-- 注销保单件数 --%>
			<td class='title'>
				<s:text name="comparison.removeNum" />
			</td>
			<%-- 退保件数 --%>
			<td class='title'>
				<s:text name="comparison.compreReportNum" />
			</td>
			<%-- 交强险报案数 --%>
			<td class='title'>
				<s:text name="comparison.insurRegisteNum" />
			</td>
			<%-- 交强险立案数 --%>
			<td class='title'>
				<s:text name="comparison.insurPrincArith" />
			</td>
			<%-- 交强险理算数 --%>
			<td class='title'>
				<s:text name="comparison.insuranceJieAnShu" />
			</td>
			<%-- 交强险结案数 --%>
			<td class='title'>
				<s:text name="comparison.cancellationNum" />
			</td>
			<%-- 交强险注销案件数 --%>
			<td class='title'>
				<s:text name="comparison.businessRiskNum" />
			</td>
			<%-- 商业险报案数 --%>
			<td class='title'>
				<s:text name="comparison.commerInsurRegist" />
			</td>
			<%-- 商业险立案数 --%>
			<td class='title'>
				<s:text name="comparison.countBusinessRisk" />
			</td>
			<%-- 商业险理算数 --%>
			<td class='title'>
				<s:text name="comparison.commerciaRisksJieAnShu" />
			</td>
			<%-- 商业险结案数 --%>
			<td class='title'>
				<s:text name="comparison.businessRiskNumber" />
			</td>
			<%-- 商业险注销案件数 --%>
		</tr>
		<tr>
			<td class='input' style="color: red"><%=returninfo.getCompareResult()%></td>
			<td class='input'><%=returninfo.getPtConfirm()%></td>
			<td class='input'><%=returninfo.getPtCancel()%></td>
			<td class='input'><%=returninfo.getPtSurrender()%></td>
			<td class='input'><%=returninfo.getPtReport()%></td>
			<td class='input'><%=returninfo.getPtRegistRation()%></td>
			<td class='input'><%=returninfo.getPtCalculation()%></td>
			<td class='input'><%=returninfo.getPtEndCase()%></td>
			<td class='input'><%=returninfo.getPtCancelClaim()%></td>
			<td class='input'><%=returninfo.getSyReport()%></td>
			<td class='input'><%=returninfo.getSyRegistRation()%></td>
			<td class='input'><%=returninfo.getSyCalculation()%></td>
			<td class='input'><%=returninfo.getSyEndCase()%></td>
			<td class='input'><%=returninfo.getSyCancelClaim()%></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan=14 class="formtitle">
				<s:text name="comparison.insCompanyCases" />
			</td>
		</tr>
		<%-- 保险公司确认案件数 --%>
		<tr>
			<td class='title'>
				<s:text name="comparison.comparisonResult" />
			</td>
			<%-- 比对结果 --%>
			<td class='title'>
				<s:text name="comparison.insConfirmNum" />
			</td>
			<%-- 投保确认件数 --%>
			<td class='title'>
				<s:text name="comparison.cancelPolicyNum" />
			</td>
			<%-- 注销保单件数 --%>
			<td class='title'>
				<s:text name="comparison.removeNum" />
			</td>
			<%-- 退保件数 --%>
			<td class='title'>
				<s:text name="comparison.compreReportNum" />
			</td>
			<%-- 交强险报案数 --%>
			<td class='title'>
				<s:text name="comparison.insurRegisteNum" />
			</td>
			<%-- 交强险立案数 --%>
			<td class='title'>
				<s:text name="comparison.insurPrincArith" />
			</td>
			<%-- 交强险理算数 --%>
			<td class='title'>
				<s:text name="comparison.insuranceJieAnShu" />
			</td>
			<%-- 交强险结案数 --%>
			<td class='title'>
				<s:text name="comparison.cancellationNum" />
			</td>
			<%-- 交强险注销案件数 --%>
			<td class='title'>
				<s:text name="comparison.businessRiskNum" />
			</td>
			<%-- 商业险报案数 --%>
			<td class='title'>
				<s:text name="comparison.commerInsurRegist" />
			</td>
			<%-- 商业险立案数 --%>
			<td class='title'>
				<s:text name="comparison.countBusinessRisk" />
			</td>
			<%-- 商业险理算数 --%>
			<td class='title'>
				<s:text name="comparison.commerciaRisksJieAnShu" />
			</td>
			<%-- 商业险结案数 --%>
			<td class='title'>
				<s:text name="comparison.businessRiskNumber" />
			</td>
			<%-- 商业险注销案件数 --%>
		</tr>
		<tr>
			<td class='input' style="color: red"><%=returninfo.getCompareResult()%></td>
			<td class='input'><%=returninfo.getGsConfirm()%></td>
			<td class='input'><%=returninfo.getGsCancel()%></td>
			<td class='input'><%=returninfo.getGsSurrender()%></td>
			<td class='input'><%=returninfo.getGsReport()%></td>
			<td class='input'><%=returninfo.getGsRegistRation()%></td>
			<td class='input'><%=returninfo.getGsCalculation()%></td>
			<td class='input'><%=returninfo.getGsEndCase()%></td>
			<td class='input'><%=returninfo.getGsCancelClaim()%></td>
			<td class='input'><%=returninfo.getBiReport()%></td>
			<td class='input'><%=returninfo.getBiRegistRation()%></td>
			<td class='input'><%=returninfo.getBiCalculation()%></td>
			<td class='input'><%=returninfo.getBiEndCase()%></td>
			<td class='input'><%=returninfo.getBiCancelClaim()%></td>
		</tr>
	</table>
	<%
		} else {
			String message = new String(returninfo.getErrorMessage().getBytes(), "GBK");
	%>
	<tr>
		<td class='input'><%=message%></td>
	</tr>
	<%
		}
	%>
</body>
</html>
