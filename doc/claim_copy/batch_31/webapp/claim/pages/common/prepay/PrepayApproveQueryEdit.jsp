<!--
****************************************************************************
* DESC       ：预赔查询条件输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<!-- 公用函数 -->
<script src="${ctx}/common/js/Common.js"></script>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx }/prepayApproveQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="claimstatus.infoQueryReview" />
				</td>
			</tr>
			<%--查询预赔信息(复核)--%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLprepay.preCompensateNo" />
					：
				</td>
				<%--预赔计算书号--%>
				<td class='input'>
					<select class=tag name="PrepayNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PrepayNo" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />
					：
				</td>
				<%--赔案号--%>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
				<td class='input' colspan="3">
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=submit class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="Approve">
	</form>
</body>
</html>