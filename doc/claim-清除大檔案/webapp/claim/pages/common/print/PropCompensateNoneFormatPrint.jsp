<%--
****************************************************************************
* DESC       ：财产赔款计算书打印页面
* AUTHOR     ：hanliang
* CREATEDATE ：2005-12-12
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
<%@include file="PropCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><%=strRiskName%>赔款计算书</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="80" align=center style="font-family: 宋体; font-size: 14pt;">
					<B><%=strRiskName%>赔款计算书<B>
				</td>
			</tr>
		</table>
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr align="center">
				<td colspan="3" height="30" width="13%">保险单号</td>
				<td colspan="3" width="37%" id="tdPolicyNo" width="30%"></td>
				<td width="13%" colspan=3>被保险人</td>
				<td colspan="3" width="37%" id="tdInsuredName"></td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">赔案编号</td>
				<td colspan="3" id="tdClaimNo"></td>
				<td colspan=3>保险财产地址</td>
				<td colspan="3" id="tdInsuredAddress"></td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">计算书号</td>
				<td colspan="3"><%=strCompensateNo%></td>
				<td colspan=3>保险标的</td>
				<td colspan="3" id="tdLossName"></td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">批单号</td>
				<td colspan="3" id="tdEndorseNo"></td>
				<td colspan=3>保险金额</td>
				<td colspan="3" id="tdSumAmount1"></td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">出险地点</td>
				<td colspan="3" id="tdDamageAddress"></td>
				<td colspan=3>出险日期</td>
				<td colspan="3" id="tdDamageStartDate"></td>
			</tr>
			<tr align=center>
				<td colspan=3 height=30>保险期限</td>
				<td colspan=9 id="tdInsuredDate"></td>
			</tr>
			<tr align="center">
				<td width="3%" height="280">赔款计算方式</td>
				<td colspan="11" id="tdContext" style='word-break: break-all' align="left" valign="top"></td>
			</tr>
			<tr align="left">
				<td colspan="12" height="30">
					&nbsp;&nbsp;赔款金额合计（大写）人民币：&nbsp;<%=strCSumPaid%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）：<%=strCurrency1%>&nbsp;<%=strSumPaid%></td>
			</tr>
			<tr align="center">
				<td width="12%" colspan="2" height="30">赔款核定</td>
				<td width="17%" colspan=2>金额</td>
				<td width="12%" colspan=2>币种</td>
				<td width="17%" colspan="2" height="30">赔款核定</td>
				<td width="10%" colspan=2>金额</td>
				<td width="25%" colspan=2>币种</td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">标的赔款</td>
				<td id="tdSumLoss" colspan=2></td>
				<td id="tdCurrency2" colspan=2></td>
				<td colspan="2" height="30">公估费</td>
				<td width="10%" colspan=2><%=MAssessFee%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblAssessFee)%></td>
				<td colspan=2 width="10%">
					&nbsp;<%=uiCodeAction.translateCurrencyCode(MAssessFee, true)%></td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">预付赔款</td>
				<td colspan=2>
					CNY
					<%=strSumprepaid%></td>
				<td colspan=2><%=uiCodeAction.translateCurrencyCode(MAssessFee, true)%></td>
				<td colspan="2" height="30">诉讼费</td>
				<td colspan=2 width="10%"><%=MFlFee%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblLawFee)%></td>
				<td colspan=2 width="10%">
					&nbsp;<%=uiCodeAction.translateCurrencyCode(MLawFee, true)%></td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">查勘费</td>
				<td colspan=2><%=MCheckFee%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblCheckFee)%></td>
				<td colspan=2><%=uiCodeAction.translateCurrencyCode(MCheckFee, true)%></td>
				<td colspan="2" height="30">其它</td>
				<td colspan=2 width="10%"><%=MElseFee%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblElseFee)%></td>
				<td colspan=2 width="10%"><%=uiCodeAction.translateCurrencyCode(MElseFee, true)%></td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">施救费</td>
				<td colspan=2><%=MRescueFee%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblRescueFee)%></td>
				<td colspan=2><%=uiCodeAction.translateCurrencyCode(MRescueFee, true)%></td>
				<td colspan="2" height="30">合计</td>
				<td colspan=2 width="10%"><%=currency%>&nbsp;<%=strSumPaid%></td>
				<td colspan=2 id="tdCurrency22" width="10%">
					&nbsp;<%=uiCodeAction.translateCurrencyCode(currency, true)%></td>
			</tr>
			<tr height="25">
				<td colspan="3">
					<div align="center">本公司应支付赔款金额：</div>
				</td>
				<td id="tdMySumPaid" align="center" colspan=9></td>
			</tr>
			<tr height="25">
				<td colspan="3">
					<div align="center">本公司代付赔款金额：</div>
				</td>
				<td id="tdOtherSumPaid" align="center" colspan=9></td>
			</tr>
			<tr>
				<td colspan="3">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;分公司总经理室签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;部门负责人签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;核赔人签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3" width="15%">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;经办人签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="12" height="55" align="left" valign="top">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" height="42" align="left" valign="top">&nbsp;高階审批意见：</td>
						</tr>
						<tr height="20">
							<td width="33%" height="2" align="left"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</from>
		<%-- include打印按钮 --%>
		<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>