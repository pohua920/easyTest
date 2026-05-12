<%--
****************************************************************************
* DESC       ：进口货物运输保险赔款理算书打印页面
* AUTHOR     ：zhuly
* CREATEDATE ：22004-11-16
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
<%@include file="FreightInportCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>进口货物运输保险赔款计算书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="40">
				<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>进口货物运输保险赔款计算书<B>
				</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr height="25">
				<td width="13%" align="center" colspan="3">保险单号</td>
				<td id="tdPolicyNo" width="37%" align="center" colspan="3"></td>
				<td width="13%" colspan="3" align="center">被保险人</td>
				<td align=center width="37%" id="tdInsuredName" align="center" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">赔案编号</td>
				<td id="tdCaseNo" align="center" colspan="3"></td>
				<td width="22%" colspan="3" align="center">出险日期</td>
				<td id="tdDamageStartDate" align="center" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">计算书号</td>
				<td align="center" colspan="3"><%=strCompensateNo%></td>
				<td width="22%" align="center" colspan="3">保险金额</td>
				<td align="center" id="tdSumAmount" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">提单号</td>
				<td id="tdLadingNo" colspan="3" align="center"></td>
				<td width="22%" align="center" colspan="3">保险险别</td>
				<td align=center id="tdRiskName" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="12%" align="center" colspan="3">
					公司合約<br>或收據号
				</td>
				<td id="tdInvoiceNo" width="35%" align="center" colspan="3"></td>
				<td width="11%" align="center" rowspan="5" colspan=3>标的及件数</td>
				<td align="center" id="tdValue1" rowspan="5" width="35%" colspan="3" valign="top"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">运输工具</td>
				<td id="tdBLName" align="center" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">船只名称</td>
				<td align="center" colspan="3" id="tdCargoName"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">开航日期</td>
				<td align="center" id="tdSailStartDate" align="center" colspan="3"></td>
			</tr>
			<tr height="25">
				<td width="15%" align="center" colspan="3">检验代理</td>
				<td id="tdCheckAgentCode" align="center" colspan="3"></td>
			</tr>
			<tr height="25">
				<td colspan=3 align=center>运输路线</td>
				<td colspan=9 align=center id="tdSiteName"></td>
			</tr>
			<tr>
				<td id="tdContext" colspan="12" height="150" style='word-break: break-all' valign="top">
					<!--reason:没有显示理算过程 -->
					赔款理算：<br>
					<!--<input type=text rows=18 cols=95 class=readonlyWhite readonly style="overflow:hidden;FONT-SIZE: 10pt">-->
				</td>
			</tr>
			<tr height="25">
				<td colspan="3">
					<div align="center">险别</div>
				</td>
				<td colspan="3">
					<div align="center">金额</div>
				</td>
				<td colspan="3">
					<div align="center">险别
				</td>
				<td colspan="3">
					<div align="center">金额</div>
				</td>
			</tr>
			<%
				String strKindName2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				String strRealPay = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				String currency = "";
				boolean fg = false;
				for (int i = 0; i < prplLossList.size(); i++) {
					PrpLlossDto lossDto = (PrpLlossDto) prplLossList.get(i);
					if (lossDto.getKindName() != null && lossDto.getKindName().trim().length() > 0) {
						strKindName2 = lossDto.getKindName();
						if ("2".equals(prpCmainDto.getCoinsFlag()) || "3".equals(prpCmainDto.getCoinsFlag())) {
							BigDecimal bigCoinsRate = new BigDecimal(Double.toString(coinsRate));
							BigDecimal bigRealPay = new BigDecimal(Double.toString(lossDto.getSumRealPay()));
							strRealPay = new DecimalFormat("#,##0.00").format(bigRealPay.multiply(bigCoinsRate).doubleValue());
						} else {
							strRealPay = new DecimalFormat("#,##0.00").format(lossDto.getSumRealPay());
						}
						currency = lossDto.getCurrency();
						//lossDto.getSumRealPay()
					} else {
						strKindName2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						strRealPay = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						fg = true;
					}
			%>
			<tr height="25">
				<td colspan="3" height="11">
					<div align="center">
						<%=strKindName2%>
					</div>
				</td>
				<td height="11" colspan="3">
					<div align="center">
						<%
							if (!fg) {
						%><%=currency%>&nbsp;<%
							}
						%><%=strRealPay%></div>
				</td>
				<td height="11" colspan=3 />
				<td height="11" colspan=3 />
			</tr>
			<%
				}
			%>
			<tr height="25">
				<td colspan="3">
					<div align="center">--</div>
				</td>
				<td colspan=3>
					<div align="center"></div>
				</td>
				<td colspan=3>
					<div align="center">预付赔款</div>
				</td>
				<td colspan=3>
					<div align="center"><%=strCurrency%>
						<%=strSumprepaid%></div>
				</td>
			</tr>
			<tr height="25">
				<td rowspan="5" align="center" width="5%">费用</td>
				<td colspan=2>
					<div align="center">施救费</div>
				</td>
				<td id="tdRescueFee" align="center" colspan=3></td>
				<td colspan=3>
					<div align="center">公估费</div>
				</td>
				<td id="tdAssessFee" align="center" colspan=3></td>
			</tr>
			<tr height="25">
				<td colspan=2>
					<div align="center">诉讼费</div>
				</td>
				<td id="tdLawFee" align="center" colspan=3></td>
				<td colspan=3>
					<div align="center">代理费</div>
				</td>
				<td id="tbAgentFee" align="center" colspan=3></td>
			</tr>
			<tr height="25">
				<td colspan=2>
					<div align="center">查勘费</div>
				</td>
				<td id="tdCheckFee" align="center" colspan=3></td>
				<td colspan=3>
					<div align="center">核赔费</div>
				</td>
				<td id="tblCheckFee2" align="center" colspan=3></td>
			</tr>
			<tr height="25">
				<td colspan=2>
					<div align="center">检验鉴定费</div>
				</td>
				<td id="tdJudgeFee" align="center" colspan=3></td>
				<td colspan=3>
					<div align="center">法律费</div>
				</td>
				<td id="tdFlFee" align="center" colspan=3></td>
			</tr>
			<tr height="25">
				<td colspan=2>
					<div align="center">共损救助费</div>
				</td>
				<td colspan=3 id="tdGsjzFee" align="center"></td>
				<td colspan=3>
					<div align="center">其它</div>
				</td>
				<td id="tdElseFee" align="center" colspan=3></td>
			</tr>
			<tr height="25">
				<td colspan="3">
					<div align="center">赔款合计</div>
				</td>
				<td id="tdSSumPaid" align="center" colspan=9></td>
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
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;分公司总经理室签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;部门负责人签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;核赔人签字：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
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
				<td colspan="12" height="60" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
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
	</form>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>