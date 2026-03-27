<%--
****************************************************************************
* DESC       ：实赔查询条件输入页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/compensateApproveQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="compensate.realCostInformationQuery" />
				</td>
			</tr>
			<!-- 查询实赔信息(复核) -->
			<tr>
				<td class='title'>
					<s:text name="db.prpLcfee.compensateNo" />：
				</td>
				<!-- >赔款计算书号 -->
				<td class='input'>
					<select class=tag name="CompensateNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="CompensateNo" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
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
					<s:text name="prompt.queRegist.PolicyNo" />：
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class=title colspan="2"></td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button'>
					<input type="submit" class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="EDIT">
	</form>
</body>
</html:html>