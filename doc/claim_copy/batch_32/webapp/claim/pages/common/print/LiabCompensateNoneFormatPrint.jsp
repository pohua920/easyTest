<%--
****************************************************************************
* DESC       ：机动车辆保险赔款计算书打印页面
* AUTHOR     ：理赔组
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
<%@include file="LiabCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><%=strRiskName%>赔款计算书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
					<B><%=strRiskName%>赔款计算书<B><br>
				</td>
			</tr>
			<!--<tr>
          <td width="50%" align=left style="font-family:宋体; font-size:10pt;">
            承保公司（签章）：
          </td>
          <td width="50%" align=left id="tdCompensateNo" style="font-family:宋体; font-size:10pt;">
            赔款计算书号：
          </td>
        </tr>-->
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr align="center">
				<td width="13%" colspan=3 height="30">保险单号</td>
				<td colspan="3" width="37%" id="tdPolicyNo"></td>
				<td width="13%" colspan=3>被保险人</td>
				<td colspan="3" id="tdInsuredName" width="37%"></td>
			</tr>
			<tr align="center">
				<td width="12%" colspan=3 height="30">赔案编号</td>
				<td colspan="3"><%=strClaimNo%></td>
				<td width="12%" colspan=3>险别</td>
				<td colspan="3" id="tdRiskName"></td>
			</tr>
			<tr align="center">
				<td width="12%" height="30" colspan=3>计算书号</td>
				<td colspan="3"><%=strCompensateNo%></td>
				<td width="12%" colspan=3>出险时间</td>
				<td colspan="3" id="tdDamageStartDate"></td>
			</tr>
			<tr align="center">
				<td width="12%" height="30" colspan=3>批单号</td>
				<td colspan="3" id="tdEndorseNo"></td>
				<td width="12%" colspan=3>出险原因</td>
				<td colspan="3" id="tdDamageName"></td>
			</tr>
			<tr align="center" style="display: none">
				<td width="12%" height="30" colspan=3>赔偿限额</td>
				<td colspan="3" id="tdSumAmount2"></td>
				<td width="12%" colspan=3>保险责任</td>
				<td colspan="3" id=""></td>
			</tr>
			<tr align=center>
				<td colspan=3 height="30">保险期限</td>
				<td colspan=9 id="tdInsuredDate"></td>
			</tr>
			<tr align="center">
				<td height="250" colspan="12" style='word-break: break-all' id="tdContext" valign="top" align="left">理赔计算：</td>
			</tr>
			<tr align="center">
				<td width="12%" height="30" colspan=3>人身伤亡</td>
				<td colspan="3" id="tdPersonSumLossPay"></td>
				<td width="12%" colspan=3>财产损失</td>
				<td colspan="3" id="tdPropSumLossPay"></td>
			</tr>
			<tr align="center">
				<td width="12%" height="30" colspan=3>诉讼费用</td>
				<td colspan="3"><%=dblLawFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblLawFee > 0 ? new DecimalFormat("#,##0.00").format(dblLawFee) : DataUtils.zeroToEmpty(dblLawFee)%></td>
				<td width="12%" colspan=3>其它费用</td>
				<td colspan="3"></td>
			</tr>
			<tr align="center">
				<td colspan="12" height="30" id="tdCSumLossPay" align="left">责任赔款</td>
			</tr>
			<tr align="center">
				<td colspan=12 height=30 align="center">赔款支出</td>
			</tr>
			<tr align=center>
				<td width="12%" colspan=2 align="center" height="30">责任赔款</td>
				<td width="17%" colspan=2 align="center" id="tdSSumLossPay"></td>
				<td width="12%" colspan=2 align="center" height="30">施救费</td>
				<td width="17%" colspan=2 align="center"><%=dblRescueFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblRescueFee > 0 ? new DecimalFormat("#,##0.00").format(dblRescueFee) : DataUtils.zeroToEmpty(dblRescueFee)%></td>
				<td width="12%" colspan=2 align="center" height="30">查勘费</td>
				<td width="17%" colspan=2 align="center" align="center"><%=dblCheckFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblCheckFee > 0 ? new DecimalFormat("#,##0.00").format(dblCheckFee) : DataUtils.zeroToEmpty(dblCheckFee)%></td>
			</tr>
			<tr align=center>
				<td width="12%" colspan=2 align="center" height="30">检验鉴定费</td>
				<td width="17%" colspan=2 align="center"><%=dblJudgeFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblJudgeFee > 0 ? (new DecimalFormat("#,##0.00").format(dblJudgeFee)) : (DataUtils.zeroToEmpty(dblJudgeFee))%></td>
				<td width="12%" colspan=2 height="30">律师费</td>
				<td width="17%" colspan=2 align="center"><%=dblFlFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblFlFee > 0 ? new DecimalFormat("#,##0.00").format(dblFlFee) : DataUtils.zeroToEmpty(dblFlFee)%></td>
				<td width="12%" colspan=2 height="30">公估费</td>
				<td width="17%" colspan=2 align="center"><%=dblAssessFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblAssessFee > 0 ? new DecimalFormat("#,##0.00").format(dblAssessFee) : DataUtils.zeroToEmpty(dblAssessFee)%></td>
			</tr>
			<tr align=center>
				<td width="12%" colspan=2 height="30">其它</td>
				<td width="17%" colspan=2 align="center"><%=dblElseFee > 0 ? (strCurrency) : ("")%>&nbsp;<%=dblElseFee > 0 ? (new DecimalFormat("#,##0.00").format(dblElseFee)) : (DataUtils.zeroToEmpty(dblElseFee))%></td>
				<td width="12%" colspan=2 height="20">预付赔款</td>
				<td width="17%" colspan=2>
					&nbsp;CNY
					<%=strSumprepaid%></td>
				<td width="12%" colspan=2 height="20">总计</td>
				<td width="17%" colspan=2 id="tdSSumPaid"></td>
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
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%-- <jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>