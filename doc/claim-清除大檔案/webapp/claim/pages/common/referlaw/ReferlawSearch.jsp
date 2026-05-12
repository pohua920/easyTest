<html lang="zh">
<head>
<title><s:text name="title.referlawBeforeEdit.involvingClaimQuery" /></title>
<%--涉诉赔案查询--%>
<script language="Javascript" src="/claim/common/js/Common.js"></script>
<script language="Javascript" src="/claim/common/js/MulLine.js"></script>
<script language="Javascript" src="/claim/common/js/ValidateData.js"></script>
<script language="Javascript" src="/claim/common/js/Validate.js"></script>
<script language="Javascript" src="/claim/common/js/InputCode.js"></script>
<script language="Javascript" src="/claim/common/js/Application.js"></script>
<script language="Javascript" src="/claim/common/js/ClaimPub.js"></script>
<script language="Javascript" src="/claim/dwr/engine.js"></script>
<script language="Javascript" src="/claim/dwr/util.js"></script>
<script language="Javascript" src="/claim/dwr/interface/dwrInvokeDataAction.js"></script>
<script src="/claim/workflow/flow/js/WorkFlowFlowBeforeQuery.js"></script>
<script type="text/javascript">
	function submitForm() {
		if (fm.searchType.value == "search") {
			fm.action = '/claim/referlaw.do?actionType=search';
		} else if (fm.searchType.value == "searchShow") {
			fm.action = '/claim/referlaw.do?actionType=searchShow';
		}
		fm.submit();
	}
</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="">
	<form name="fm" action="/referlaw.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="referlaw.involvingClaimQuery" />
					<%--涉诉赔案查询--%>
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
					<input type=text name="prpLReferLawRiskcode" class="query">
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
		<%
			String searchType = request.getParameter("editType");
		%>
		<input type="hidden" name="searchType" value="<%=searchType%>">
	</form>
</body>
</html>
