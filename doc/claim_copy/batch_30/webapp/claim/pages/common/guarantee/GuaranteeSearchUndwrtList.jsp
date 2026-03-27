<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.custom.TurnPageDto"%>
<html lang="zh">
<head>
<title><s:text name="guarantee.sponsorshipCheckedQuery" /></title>
<!-- 担保审核查询 -->
<script language="Javascript" src="/claim/common/js/Common.js"></script>
<script language="Javascript" src="/claim/common/js/MulLine.js"></script>
<script language="Javascript" src="/claim/common/js/ValidateData.js"></script>
<script language="Javascript" src="/claim/common/js/Validate.js"></script>
<script language="Javascript" src="/claim/common/js/InputCode.js"></script>
<script language="Javascript" src="/claim/common/js/Application.js"></script>
<script language="Javascript" src="/claim/common/js/ClaimPub.js"></script>
<script type="text/javascript">
	function submitForm() {
		fm.action = '/claim/guarantee.do?actionType=searchUndwrt';
		fm.submit();
	}
</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="">
	<form name="fm" action="/claim/guarantee.do?actionType=searchUndwrt" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="guarantee.sponsorshipCheckedQuery" />
					<!-- 担保审核查询 -->
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />
					：
					<!-- 立案号 -->
				</td>
				<td class='input'>
					<select name="ClaimNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLGuaranteeClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />
					：
					<!-- 保单号码 -->
				</td>
				<td class='input'>
					<select name="PolicyNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLGuaranteePolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLrepairFee.registNo" />
					：
					<!-- 报案号 -->
				</td>
				<td class='input'>
					<select name="RegistNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLGuaranteeRegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLcomponent.riskCode" />
					：
					<!-- 险种 -->
				</td>
				<td class='input'>
					<select name="RiskCodeSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLGuaranteeRiskcode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 10%">
					<s:text name="db.prpLCMain.insuredName" />
					:
					<!-- 被保险人名称 -->
				</td>
				<td class='input' style="width: 90%" colspan=3>
					<select class=tag name="InsuredNameSign" style="width: 45px">
						<option value="=">=</option>
					</select>
					<input type=text name="prpLGuaranteeInsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name="button.query.value" />"
					<!-- 查 询 -->
					onClick="submitForm();">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<!-- "="符号，必须精确查询。 -->
					<br>
					<s:text name="prompt.schedule.query2" />
					<!-- "=*"符号，前匹配後模糊的查询。 -->
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="center" class="centertitle">
					<s:text name="db.prpDrate.serialNo" />
					<!-- 序号 -->
				</td>
				<td align="center" class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
					<!-- 立案号 -->
				</td>
				<td align="center" class="centertitle" style="width: 10%">
					<s:text name="db.prpLclaimpolicy.policyNo" />
					<!-- 保单号 -->
				</td>
				<td align="center" class="centertitle">
					<s:text name="db.prpLCMain.insuredName" />
					<!-- 被保险人名称 -->
				</td>
				<td align="center" class="centertitle">
					<s:text name="prpLclaim.claimDate" />
					<!-- 立案时间 -->
				</td>
			</tr>
			<%
				int index = 1;
			%>
			<logic:iterate id="prpLclaimDto" name="claimList">
				<tr class=listodd>
					<td align="center">
						<%=index++%>
					</td>
					</td>
					<td align="center">
						<a href="/claim/guarantee.do?actionType=guaranteeUndwrt&prpLGuaranteeClaimNo=<bean:write name='prpLclaimDto' property="claimNo"/>"><bean:write name='prpLclaimDto' property="claimNo" /> </a>
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property="policyNo" />
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property="insuredName" />
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property="claimDate" />
					</td>
				</tr>
			</logic:iterate>
			<%
				int curPage = ((TurnPageDto) request.getAttribute("pageview")).getPageNo();
			%>
		</table>
		<%@include file="/common/pub/TurnOverPage.jsp"%>
	</form>
</body>
</html>
