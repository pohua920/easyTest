<%--
****************************************************************************
* DESC       ：国内货物运输保险赔款理算书打印页面
* AUTHOR     ：zhuly
* CREATEDATE ：2005-11-15
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
<%@include file="FreightNationalCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>国内货物运输保险赔款计书列印</title>
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
					<B>国内货物运输保险赔款计算书<B>
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
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;保险单号</td>
				<td id="tdPolicyNo" width="37%" colspan="3"></td>
				<td width="13%" align="left" colspan=3>&nbsp;被保险人</td>
				<td align=left id="tdInsuredName" width="37%" colspan="3"></td>
			</tr>
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;赔案编号</td>
				<td id="tdCaseNo" width="37%" colspan="3"></td>
				<td width="13%" align="left" colspan=3>&nbsp;保险日期</td>
				<td align=left id="tdInsuredDate" width="37%" colspan="3"></td>
			</tr>
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;计算书号</td>
				<td colspan="3" width="37%"><%=strCompensateNo%></td>
				<td width="13%" align="left" colspan=3>&nbsp;出险日期</td>
				<td align=left id="tdDamageStartDate" width="37%" colspan="3"></td>
			</tr>
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;出险地点</td>
				<td colspan="3" id="tdDamageAddress" width="37%"></td>
				<td width="13%" align="left" colspan=3>&nbsp;出险原因</td>
				<td align id="tdDamageName" colspan=3 width="37%"></td>
			</tr>
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;运输工具牌号</td>
				<td id="tdBLNo" colspan="3" width="37%"></td>
				<td width="13%" align="left" colspan=3>&nbsp;运单号码</td>
				<td align=left id="tdCarryBillNo" colspan="3"></td>
			</tr>
			<tr height="23">
				<td width="13%" align="left" colspan=3>&nbsp;货物名称</td>
				<td colspan="3" width="37%" id=""><%=strLossName%></td>
				<td width="13%" colspan=3 align="left">&nbsp;货物数量</td>
				<td align=left id="" colspan="3" width="37%"><%=intLossQuantity%></td>
			</tr>
			<tr height="23">
				<td width="13%" colspan=3 align="left">&nbsp;保险险别</td>
				<td colspan="3" id="tdRiskName"width"37%" ></td>
				<td width="13%" colspan=3 align="left">&nbsp;保险金额</td>
				<td align=left id="tdSumAmount" colspan="3" width="37%"></td>
			</tr>
			<tr height="23">
				<td width="12%" align="left" colspan=3>&nbsp;运输路线</td>
				<td id="tdSiteName" colspan="9"></td>
			</tr>
			<tr>
				<td id="tdContext" colspan="12" style='word-break: break-all' height="265" valign="top">
					赔款理算：<br>
				</td>
			</tr>
			<tr height="23" style="display: none">
				<td width="12%" align="center" colspan=3>&nbsp;赔款项目</td>
				<td width="31%" align="center" colspan="3">金&nbsp;&nbsp;额</td>
				<td width="14%" align="center" colspan="3">&nbsp;回收项目</td>
				<td width="31%" align="center" colspan="3">金&nbsp;&nbsp;额</td>
			</tr>
			<tr height="23" style="display: none">
				<td width="12%" align="left" colspan=3>&nbsp;标的赔款</td>
				<td id="tdSumLossPay" colspan="3"></td>
				<td width="14%" align="left" colspan="3">&nbsp;第三者责任追回</td>
				<td align=left colspan="3"></td>
			</tr>
			<tr height="23" style="display: none">
				<td width="13%" align="left" colspan=3>&nbsp;施救费</td>
				<td align=left colspan="3"><%=rfCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblRescueFee)%></td>
				<td width="14%" align="left" colspan="3">&nbsp;损余收回</td>
				<td align=left colspan="3"></td>
			</tr>
			<tr height="23" style="display: none">
				<td width="13%" align="left" colspan=3>&nbsp;小计</td>
				<td colspan="3"></td>
				<td width="14%" align="left" colspan="3">&nbsp;小计</td>
				<td align=left colspan="3"></td>
			</tr>
			<tr height="23" style="display: none">
				<td colspan="12" align="left" id="tdCSumLossPay"></td>
			</tr>
			<tr>
				<td colspan=12 align=center>赔款支出</td>
			</tr>
			<tr>
				<td width="12%" align="left" colspan=3>&nbsp;标的赔款</td>
				<td id="tdSSumLossPay" colspan="3"></td>
				<td width="14%" align="left" colspan="3">&nbsp;施救费</td>
				<td align=left colspan="3"><%=rfCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblRescueFee)%></td>
			</tr>
			<tr>
				<td width="12%" align="left" colspan=3>&nbsp;查勘费</td>
				<td colspan="3"><%=cfCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblCheckFee)%></td>
				<td width="14%" align="left" colspan="3">&nbsp;检验鉴定费</td>
				<td align=left colspan="3"><%=jfCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblJudgeFee)%></td>
			</tr>
			<tr>
				<td width="12%" align="left" colspan=3>&nbsp;法律费</td>
				<td colspan="3"><%=ffCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblFlFee)%></td>
				<td width="14%" align="left" colspan="3">&nbsp;公估费</td>
				<td align=left colspan="3" height="21"><%=afCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblAssessFee)%></td>
			</tr>
			<tr>
				<td width="12%" align="left" colspan=3>&nbsp;其它</td>
				<td colspan="3" id=""><%=efCurrency%>&nbsp;<%=new DecimalFormat("#,##0.00").format(dblElseFee)%></td>
				<td width="14%" align="left" colspan="3">&nbsp;预付赔款</td>
				<td align=left colspan="3" id="">
					CNY
					<%=strSumprepaid%></td>
			</tr>
			<tr>
				<td width="12%" align="left" colspan=3>&nbsp;总计</td>
				<td colspan="3" id="tdSSumPaid"></td>
				<td width="14%" align="left" colspan="3">&nbsp;</td>
				<td align=left colspan="3" id=""></td>
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
		<table border="0">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：赔款支出项目中赔款合计栏所列金额为支付被保险人的赔款金额。</td>
			</tr>
		</table>
	</form>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%-- <jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
