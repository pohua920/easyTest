<!--
****************************************************************************
* DESC       ：录入特殊赔案前查询立案条件果面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-12-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<%--查询立案信息  --%>
<!-- 公用函数 -->
<script src="${ctx}/common/js/Common.js"></script>
<script language="javascript">
<!--案件状态标志处理-->
	function submitForm() {
		fm.submit();//提交
	}
//-->
</script>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/clamiQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="claim.queryClaim" />
				</td>
			</tr>
			<%--查询立案信息  --%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="specialCase.ClaimsNumbers" />
					：
				</td>
				<%--赔案号码  --%>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SpecialCase">
	</form>
</body>
</html>
