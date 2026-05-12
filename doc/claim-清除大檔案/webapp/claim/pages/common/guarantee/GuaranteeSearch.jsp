
<%
String editType = request.getParameter("editType");
%>
<html lang="zh">
<head>
<title>
	<%
			if ("apply".equals(editType)) {
			%> <s:text name="guarantee.sponsorshipApplyQuery" />
	<!-- 担保申请查询 --> <%
			} else if ("undwrt".equals(editType)) {
			%> <s:text name="guarantee.sponsorshipCheckedQuery" />
	<!-- 担保审核查询 --> <%
			} else if ("recover".equals(editType)) {
			%> <s:text name="guarantee.sponsorshipHandbackQuery" />
	<!-- 担保收回查询 --> <%
			} else if ("search".equals(editType)) {
			%> <s:text name="guarantee.sponsorshipQuery" />
	<!-- 担保查询 --> <%
			} else if ("search".equals(editType)) {
			%><s:text name="guarantee.sponsorshipPrintQuery" /> <!-- 担保打印查询 --> <%
			}
			%>
</title>
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
		  function submitForm()
		  {
		    if(fm.searchType.value=="apply")
		    {
  		      fm.action='/claim/guarantee.do?actionType=search';
		    }else if(fm.searchType.value=="undwrt")
		    {
		      fm.action='/claim/guarantee.do?actionType=searchUndwrt';
		    }else if(fm.searchType.value=="search")
		    {
		      fm.action='/claim/guarantee.do?actionType=searchShow';
		    }else if(fm.searchType.value=="print")
		    {
		      fm.action='/claim/guarantee.do?actionType=searchPrint';
		    }else if(fm.searchType.value=="recover")
		    {
		      fm.action='/claim/guarantee.do?actionType=recover';
		    }
		    fm.submit();
		  }
		</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="">
	<form name="fm" action="/guarantee.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<%
						if ("apply".equals(editType)) {
						%>
					<s:text name="guarantee.sponsorshipApplyQuery" />
					<!-- 担保申请查询 -->
					<%
						} else if ("undwrt".equals(editType)) {
						%>
					<s:text name="guarantee.sponsorshipCheckedQuery" />
					<!-- 担保审核查询 -->
					<%
						} else if ("recover".equals(editType)) {
						%>
					<s:text name="guarantee.sponsorshipHandbackQuery" />
					<!-- 担保收回查询 -->
					<%
						} else if ("search".equals(editType)) {
						%>
					<s:text name="guarantee.sponsorshipQuery" />
					<!-- 担保查询 -->
					<%
						} else if ("print".equals(editType)) {
						%>
					<s:text name="guarantee.sponsorshipPrintQuery" />
					<!-- 担保打印查询 -->
					<%
						}
						%>
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
					<s:text name="db.prpLcheck.policyNo" />
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
		<%
			String searchType = request.getParameter("editType");
			%>
		<input type="hidden" name="searchType" value="<%=searchType%>">
	</form>
</body>
</html>
