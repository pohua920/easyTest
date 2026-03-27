<%--
***************************************************************************
* Description: 分保试算结果页面
* Author     : 国寿项目组
* CreateDate:  2005-6-4 14:37
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<!-- 滚动条样式定义 -->
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="CommonStyle.html"%>
<s:if test="#attr.prpUserGradeValue=='0'">
	<s:set var="disPlay" value="none" scope="page" />
</s:if>
<s:else>
	<s:set var="disPlay" value="null" scope="page" />
</s:else>
<s:if test="#parameters[0].CertiType=='E'">
<html>
<head>
<title><s:text name="title.undwrtBeforeEdit.ResultInformation" /></title>
<%-- 分保试算结果信息 --%>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
</head>
<body class="interface">
	<form>
		<table class="common" cellpadding="5" cellspacing="1" align="center" width="100%">
			<tr class=listtitle>
				<td>
					<s:text name="undwrt.CalculationResults" />
				</td>
				<%-- 分保试算结果 --%>
			</tr>
			<tr>
				<td class="title"></td>
			<tr>
		</table>
		&nbsp;
		<table width="847" height="223" border="0" class="sub">
			<c:forEach items="${ReinsTrialInfo}" var="reinsTrialDangerInfoDto" varStatus="reinsTrial_status">
				<tr class=common>
					<td colspan="5" class="formtitle1">
						<strong><s:text name="claim.dangeSerialNum" />${reinsTrialDangerInfoDto.dangerNo }</strong>
					</td>
					<%-- 危险单位序号 --%>
				</tr>
				<tr>
					<td class="centertitle">
						<s:text name="undwrt.Reinsurance" />
					</td>
					<%-- 分&nbsp;保&nbsp;方&nbsp;式 --%>
					<td class="centertitle">
						<s:text name="undwrt.Proportion" />
					</td>
					<%-- 比&nbsp;&nbsp;例 --%>
					<td class="centertitle">
						<s:text name="undwrt.SumInsured" />
					</td>
					<%-- 保&nbsp;&nbsp;额 --%>
					<td style="display:${disPlay }" class="centertitle">
						<s:text name="undwrt.Premium" />
					</td>
					<%-- 保&nbsp;&nbsp;费 --%>
					<%--<td class="centertitle">&nbsp;手续费</td>--%>
				</tr>
				<c:set value="0" var="tolPremium" scope="page" />
				<c:set value="0" var="tolAmount" scope="page" />
				<c:set value="0" var="tolShareRate" scope="page" />
				<c:forEach items="${reinsTrialDangerInfoDto.collection}" var="prpTreinstrialViewInfoDto">
					<c:set value="${tolPremium+prpTreinstrialViewInfoDto.premium}" var="tolPremium" scope="page" />
					<c:set value="${tolAmount+prpTreinstrialViewInfoDto.amount}" var="tolAmount" scope="page" />
					<c:set value="${tolShareRate+prpTreinstrialViewInfoDto.shareRate}" var="tolShareRate" scope="page" />
					<tr class=common align="center">
						<td class="formtitle1" align="center">
							<input class="formtitle1" name="shareRate" type="text" readonly="true" value="${prpTreinstrialViewInfoDto.refNo}">
						</td>
						<td align="center">
							<input class="formtitle1" name="shareRate" type="text" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.shareRate}' pattern='#'/>">
						</td>
						<td align="center">
							<input class="formtitle1" name="amount" type="text" id="amount" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.amount}' pattern='#'/>">
						</td>
						<td style="display:${disPlay }" align="center">
							<input class="formtitle1" name="premimu" type="text" id="premimu" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.premium}' pattern='#'/>">
						</td>
					</tr>
				</c:forEach>
				<tr class=common align="center">
					<td class="formtitle1">
						<s:text name="undwrt.Total" />
					</td>
					<%-- 合计 --%>
					<td align="center">
						<input class="formtitle1" name="tolShareRate" type="text" readonly="true" value="<fmt:formatNumber value='${tolShareRate}' pattern='#'/>">
					</td>
					<td align="center">
						<input class="formtitle1" name="tolAmount" type="text" readonly="true" value="<fmt:formatNumber value='${tolAmount}' pattern='#'/>">
					</td>
					<td style="display:${disPlay }" align="center">
						<input class="formtitle1" name="tolPremium" type="text" readonly="true" value="<fmt:formatNumber value='${tolPremium}' pattern='#'/>">
					</td>
				</tr>
			</c:forEach>
		</table>
		<table class=sub>
			<tr>
				<td class=button width="100%">
					<Input class="button" name="buttonClose" type="button" alt="关闭" value="<s:text name='button.close.value'/>" onclick="window.close()">
					<%-- 关 闭 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</s:if>
<s:else>
<html>
<head>
<title><s:text name="title.undwrtBeforeEdit.CalculationResults" /></title>
<%-- 分批试算结果信息 --%>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
</head>
<body class="interface">
	<form>
		<table class="common" cellpadding="5" cellspacing="1" align="center" width="100%">
			<tr class=listtitle>
				<td>
					<s:text name="undwrt.BatchResults" />
				</td>
				<%-- 分批试算结果 --%>
			</tr>
			<tr>
				<td class="title"></td>
			<tr>
		</table>
		&nbsp;
		<table width="847" height="223" border="0" class="sub">
			<c:forEach items="${ReinsTrialInfo}" var="reinsTrialDangerInfoDto">
				<tr class=common>
					<td colspan="7" class="formtitle1">
						<strong><s:text name="claim.dangeSerialNum" />${reinsTrialDangerInfoDto.dangerNo }</strong>
					</td>
					<%-- 危险单位序号 --%>
				</tr>
				<tr>
					<td class="centertitle">
						<s:text name="undwrt.Reinsurance" />
					</td>
					<%-- 分&nbsp;保&nbsp;方&nbsp;式 --%>
					<td class="centertitle">
						<s:text name="undwrt.AfterCorrecting" />
					</td>
					<%-- 批改後比例 --%>
					<td class="centertitle">
						<s:text name="undwrt.InsuredAmount" />
					</td>
					<%-- 批改後保额 --%>
					<td class="centertitle">
						<s:text name="undwrt.ChangeAmount" />
					</td>
					<%-- 变化保额 --%>
					<td class="centertitle">
						<s:text name="undwrt.AfterPremium" />
					</td>
					<%-- 批改後保费 --%>
					<td class="centertitle">
						<s:text name="undwrt.ChangePremium" />
					</td>
					<%-- 变化保费 --%>
					<%--<td class="centertitle">&nbsp;手续费</td>--%>
				</tr>
				<c:set var="endorsePremium" value="0" scope="page" />
				<c:set var="endorseAmount" value="0" scope="page" />
				<c:set var="endorseShareRate" value="0" scope="page" />
				<c:set var="chgPremium" value="0" scope="page" />
				<c:set var="chgAmount" value="0" scope="page" />
				<c:forEach items="${reinsTrialDangerInfoDto.collection}" var="prpTreinstrialViewInfoDto">
					<c:set var="endorsePremium" value="${endorsePremium+prpTreinstrialViewInfoDto.premium}" scope="page" />
					<c:set var="endorseAmount" value="${endorseAmount+prpTreinstrialViewInfoDto.amount}" scope="page" />
					<c:set var="endorseShareRate" value="${endorseShareRate+prpTreinstrialViewInfoDto.shareRate}" scope="page" />
					<c:set var="chgPremium" value="${chgPremium+prpTreinstrialViewInfoDto.chgPremium}" scope="page" />
					<c:set var="chgAmount" value="${chgAmount+prpTreinstrialViewInfoDto.chgAmount}" scope="page" />
					<tr class=common>
						<td width="168" class="formtitle1" align="center">
							<input class="formtitle1" name="shareRate" type="text" readonly="true" value="${prpTreinstrialViewInfoDto.refNo}">
						</td>
						<td width="168">
							<input class="formtitle1" name="shareRate" type="text" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.shareRate}' pattern='#'/>">
						</td>
						<td width="168">
							<input class="formtitle1" name="amount" type="text" id="amount" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.amount}' pattern='#'/>">
						</td>
						<td width="168">
							<input class="formtitle1" name="premimu" type="text" id="premimu" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.chgAmount}' pattern='#'/>">
						</td>
						<td width="168">
							<input class="formtitle1" name="amount" type="text" id="amount" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.premium}' pattern='#'/>">
						</td>
						<td width="168">
							<input class="formtitle1" name="premimu" type="text" id="premimu" readonly="true" value="<fmt:formatNumber value='${prpTreinstrialViewInfoDto.chgPremium}' pattern='#'/>">
						</td>
						<%--    
    <td width="168"><input class="formtitle1" name="commission" type="text" id="commission" readonly="true" 
        value="<%=idecimalFormat.format(prpTreinstrialViewInfoDto.getCommission())%>"></td> --%>
					</tr>
				</c:forEach>
				<tr>
					<td class="centertitle">
						<s:text name="undwrt.Total" />
					</td>
					<%-- 合计 --%>
					<td class="centertitle">
						<input class="formtitle1" name="endorseShareRate" type="text" readonly="true" value="<fmt:formatNumber value='${endorseShareRate}' pattern='#'/>">
					</td>
					<td class="centertitle">
						<input class="formtitle1" name="endorseAmount" type="text" readonly="true" value="<fmt:formatNumber value='${endorseAmount}' pattern='#'/>">
					</td>
					<td class="centertitle">
						<input class="formtitle1" name="chgPremium" type="text" readonly="true" value="<fmt:formatNumber value='${chgAmount}' pattern='#'/>">
					</td>
					<td class="centertitle">
						<input class="formtitle1" name="endorsePremium" type="text" readonly="true" value="<fmt:formatNumber value='${endorsePremiu}' pattern='#'/>">
					</td>
					<td class="centertitle">
						<input class="formtitle1" name="chgPremium" type="text" readonly="true" value="<fmt:formatNumber value='${chgPremium}' pattern='#'/>">
					</td>
				</tr>
			</c:forEach>
		</table>
		<table class=sub>
			<tr>
				<td class=button width="100%">
					<Input class="button" name="buttonClose" type="button" alt="关闭" value="<s:text name='button.close.value'/>" onclick="window.close()">
					<%-- 关 闭 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</s:else>