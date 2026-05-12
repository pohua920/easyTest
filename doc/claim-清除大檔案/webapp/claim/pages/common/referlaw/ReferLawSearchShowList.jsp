<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.custom.TurnPageDto"%>
<html lang="zh">
<head>
<title><s:text name="title.referlawBeforeEdit.applyQuerieLawsuit" /></title>
<%--涉诉申请查询--%>
<script language="Javascript" src="/claim/common/js/Common.js"></script>
<script language="Javascript" src="/claim/common/js/MulLine.js"></script>
<script language="Javascript" src="/claim/common/js/ValidateData.js"></script>
<script language="Javascript" src="/claim/common/js/Validate.js"></script>
<script language="Javascript" src="/claim/common/js/InputCode.js"></script>
<script language="Javascript" src="/claim/common/js/Application.js"></script>
<script language="Javascript" src="/claim/common/js/ClaimPub.js"></script>
<script type="text/javascript">
		  function submitForm()
		  {
		    fm.action='/claim/referlaw.do?actionType=searchShow';
		    fm.submit();
		  }
		</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="">
	<form name="fm" action="/claim/referlaw.do?actionType=searchShow" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="referlaw.applyQuerieLawsuit" />
					<%--涉诉申请查询--%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prpLclaim.claimNo" />
					：
					<%--立案号--%>
				</td>
				<td class='input'>
					<select name="ClaimNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLReferLawClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_loan.policyNo" />
					：
					<%--保单号码--%>
				</td>
				<td class='input'>
					<select name="PolicyNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLReferLawPolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimApprov.registNo" />
					：
					<%--报案号--%>
				</td>
				<td class='input'>
					<select name="RegistNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLReferLawRegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLarrearageNew.riskCode" />
					：
					<%--险种--%>
				</td>
				<td class='input'>
					<select name="RiskCodeSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prplReferLawRiskcode" class="query">
				</td>
			</tr>
			<%--				<tr>--%>
			<%--					<td class='title' style="width:10%">--%>
			<%--						被保险人名称:--%>
			<%--					</td>--%>
			<%--					<td class='input' style="width:90%" colspan=3>--%>
			<%--						<select class=tag name="InsuredNameSign" style="width:45px">--%>
			<%--							<option value="=">--%>
			<%--								=--%>
			<%--							</option>--%>
			<%--						</select>--%>
			<%--						<input type=text name="prpLReferLawInsuredName" class="query">--%>
			<%--					</td>--%>
			<%--				</tr>--%>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="center" class="centertitle">
					<s:text name="db.prpLreplevynew.serialNo" />
					<%--序号--%>
				</td>
				<td align="center" class="centertitle">
					<s:text name="prpLclaim.claimNo" />
					<%--立案号--%>
				</td>
				<td align="center" class="centertitle">
					<s:text name="referlaw.litigationBatche" />
					<%--诉讼批次--%>
				</td>
				<td align="center" class="centertitle">
					<s:text name="db.view_larrearage.policyNo" />
					<%--保单号--%>
				</td>
				<td align="center" class="centertitle">
					<s:text name="db.prpLarrearageNew.riskCode" />
					<%--险种--%>
				</td>
				<td align="center" class="centertitle">
					<s:text name="referlaw.edit" />
					<%--编辑--%>
				</td>
			</tr>
			<%
				int index = 1;
			%>
			<logic:iterate id="prplreferlawDto" name="claimList">
				<tr class=listodd>
					<td align="center">
						<%=index++%>
					</td>
					</td>
					<td align="center">
						<a href="/claim/referlaw.do?actionType=show&prpLReferLawClaimNo=<bean:write name='prplreferlawDto' property="claimno"/>&serialno=<bean:write name='prplreferlawDto' property="serialno"/>"><bean:write
								name='prplreferlawDto' property="claimno" /> </a>
					</td>
					<td align="center">
						<bean:write name='prplreferlawDto' property="serialno" />
					</td>
					<td align="center">
						<bean:write name='prplreferlawDto' property="policyno" />
					</td>
					<td align="center">
						<bean:write name='prplreferlawDto' property="riskcode" />
					</td>
					<td align="center">
						<logic:equal value="true" name='prplreferlawDto' property='checkEndCase'>
							<a href="/claim/referlaw.do?actionType=edit&prpLReferLawClaimNo=<bean:write name='prplreferlawDto' property="claimno"/>&serialno=<bean:write name='prplreferlawDto' property="serialno"/>">编辑</a>
						</logic:equal>
						<logic:equal value="false" name='prplreferlawDto' property='checkEndCase'>
							<s:text name="claim.case" />
							<%--已结案--%>
						</logic:equal>
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