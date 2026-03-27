<%--
****************************************************************************
* DESC       查询立案信息
* AUTHOR     ： qinyongli
* CREATEDATE ： 2005-09-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>


<script>
function submitForm() {
	if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0) || (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0) || (fm.ClaimNoSign.value == "=" && fm.ClaimNo.value.length > 0)) {
		//输入了一个条件，可以查
	} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8) || (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8) || (fm.ClaimNoSign.value == "=*" && fm.ClaimNo.value.length > 8)) {
		if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.ClaimNo.value.substr(1, 2))) {
			alert("车险必须精确查询！");
			return false;
		} else {
			//非车险可以前9位模糊查询
		}
	} else {
		alert("車險必須輸入備案號碼、保單號、立案號、被保險人其中一項精確查詢！\n 非車險可以用備案號碼、立案號或者保單號的前9位進行模糊查詢！");
		return false;
	}
	fm.searchFlag.value = "true";
	fm.pageNo.value = "1"; //查询後页面设为1
	fm.submit(); //提交
}
</script>
<html:html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<!-- 查询立案信息 -->
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/modifySumClaim.do" method="post" onSubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="modifyBeforeQuery">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="title.claimBeforeEdit.titleName" />
				</td>
				<!-- 查询立案信息 -->
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />
					：
				</td>
				<!-- 被保险人名称 -->
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpCcargoDetail.riskCode" />
					:
				</td>
				<!-- 险种 -->
				<td class='input'>
					<select class=tag name="RiskCodeSign">
						<option value="=">=&nbsp;</option>
						<!--<option value="*">*</option>-->
					</select>
					<input type=text name="RiskCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
					:
				</td>
				<!-- 报案号 -->
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!-- "="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<br>
					<!-- "=*"符号，前匹配後模糊的查询。 -->
					<s:text name="modifySumClaim.query3" />
					<!-- 车险必须输入报案号、保单号、立案号、被保险人其中一项精确查询！ -->
					<br>
					<s:text name="modifySumClaim.query4" />
					<!-- 非车险可以用报案号、立案号或者保单号的前9位进行模糊查询！ -->
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="5" class="formtitle">
					<s:text name="modifySumClaim.registeredCases" />
				</td>
				<!-- 已立案案件(点击赔案号调整估损金额) -->
			</tr>
			<tr>
				<td class="formtitle">
					<s:text name="check.claimNum" />
					<!-- 赔案号 -->
				</td>
				<td class="formtitle">
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td class="formtitle">
					<s:text name="db.prpDdbs.riskCode" />
					<!-- 险种 -->
				</td>
				<td class="formtitle">
					<s:text name="db.prpLregist.insuredName" />
					<!-- 被保险人 -->
				</td>
				<td class="formtitle">
					<s:text name="prpLclaim.claimDate" />
					<!-- 立案时间 -->
				</td>
			</tr>
			<logic:iterate id="prpLclaimDto" name="claimList">
				<tr class=listodd>
					<td align="center">
						<a href="/claim/modifySumClaim.do?editType=modifyDetail&claimNo=<bean:write name='prpLclaimDto' property='claimNo' filter='true' /> "> <bean:write name='prpLclaimDto' property='claimNo'
								filter='true' />
						</a>
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property='policyNo' filter='true' />
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property='riskCode' filter='true' />
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property='insuredName' filter='true' />
					</td>
					<td align="center">
						<bean:write name='prpLclaimDto' property='claimDate' filter='true' />
					</td>
				</tr>
			</logic:iterate>
			<%
          int curPage = ((TurnPageDto)request.getAttribute("pageview")).getPageNo();
      %>
		</table>
		<%@include file="/common/pub/TurnOverPage.jsp"%>
	</form>
</body>
</html:html>
