<!--
****************************************************************************
* DESC       ：追偿计算书打印页面
* AUTHOR     ：曹志刚
* CREATEDATE ：2009-12-29
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>${strRiskName }赔款计算书</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="80" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>${strRiskName }赔款计算书<B>
				</td>
			</tr>
		</table>
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr align="center">
				<td colspan="3" height="30" width="13%">保险单号</td>
				<td colspan="3" width="37%" id="tdPolicyNo" width="30%">${prpLclaim.policyNo }</td>
				<td width="13%" colspan=3>被保险人</td>
				<td colspan="3" width="37%" id="tdInsuredName">${prpLclaim.insuredName }</td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">赔案编号</td>
				<td colspan="3" id="tdClaimNo">${prpLclaim.claimNo }</td>
				<td colspan="3" height="30">计算书号</td>
				<td colspan="3">${compensateDto.prpLcompensate.compensateNo }</td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">批单号</td>
				<td colspan="3" id="tdEndorseNo">${strEndorseNo }</td>
				<td colspan=3>保险金额</td>
				<td colspan="3" id="sumAmount">
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value='${prpCmainDto.sumAmount}' pattern='#' />
				</td>
			</tr>
			<tr align="center">
				<td colspan="3" height="30">出险地点</td>
				<td colspan="3" id="tdDamageAddress">${prpLclaim.damageAddress }</td>
				<td colspan=3>出险日期</td>
				<td colspan="3" id="tdDamageStartDate">${prpLclaimDto.damageStartDate }</td>
			</tr>
			<tr align=center>
				<td colspan=3 height=30>保险期限</td>
				<td colspan=9 id="tdInsuredDate">${strInsuredDate }</td>
			</tr>
			<tr align="left">
				<td colspan="12" height="30">
					&nbsp;&nbsp;追偿收入合计（大写）：&nbsp;${cSumThisPaid }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）：${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${sumThisPaid}" pattern="#" />
				</td>
			</tr>
			<tr align="left">
				<td colspan="12" height="30">
					&nbsp;&nbsp;追偿费用合计（大写）：&nbsp;${cSumNoDutyFee }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）：${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${sumNoDutyFee}" pattern="#" />
				</td>
			</tr>
			<tr align="center">
				<td width="12%" colspan="2" height="30">
					<B>赔款核定</B>
				</td>
				<td width="17%" colspan=2>
					<B>金额</B>
				</td>
				<td width="12%" colspan=2>
					<B>币种</B>
				</td>
				<td width="12%" colspan="2" height="30">
					<B>赔款核定</B>
				</td>
				<td width="17%" colspan=2>
					<B>金额</B>
				</td>
				<td width="12%" colspan=2>
					<B>币种</B>
				</td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">追偿收入</td>
				<td id="tdSumLoss" colspan=2>
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${sumThisPaid}" pattern="#" />
				</td>
				<td id="tdCurrency2" colspan=2>&nbsp;${currencyName }</td>
				<td colspan="2" height="30">查勘费</td>
				<td width="10%" colspan=2>
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${checkFee}" pattern="#" />
				</td>
				<td colspan=2 width="10%">&nbsp;${currencyName }</td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">诉讼费</td>
				<td colspan=2>
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${lawFee}" pattern="#" />
				</td>
				<td colspan=2>${currencyName }</td>
				<td colspan="2" height="30">奖励费</td>
				<td colspan=2 width="10%">
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${rewardFee}" pattern="#" />
				</td>
				<td colspan=2 width="10%">&nbsp;${currencyName }</td>
			</tr>
			<tr align="center">
				<td colspan="2" height="30">代查勘费</td>
				<td colspan=2>
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${dCheckFee}" pattern="#" />
				</td>
				<td colspan=2>${currencyName }</td>
				<td colspan="2" height="30">其他</td>
				<td colspan=2 width="10%">
					${prpCmainDto.currency }&nbsp;
					<fmt:formatNumber value="${elseFee}" pattern="#" />
				</td>
				<td colspan=2 width="10%">${currencyName }</td>
			</tr>
			<tr>
				<td colspan="3">
					<table width="100%" height="45%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;分管总经理室签字：</td>
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
							<td width="33%" align="left" valign="top">&nbsp;经办人签字：</td>
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
		<!-- include打印按钮 -->
		<jsp:include page="/pages/common/print/PrintButton.jsp" />
</body>
</html>