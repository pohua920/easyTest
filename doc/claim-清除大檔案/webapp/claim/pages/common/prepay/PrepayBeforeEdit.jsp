<!--
****************************************************************************
* DESC       ：录入预赔前输入赔案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-05-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/prepayBeforeEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="archive.inputClaimNumber" />
				</td>
			</tr>
			<%--输入赔案号--%>
			<tr>
				<td class='title2'>
					<s:text name="certainLoss.claims" />
					:
				</td>
				<%--赔案号--%>
				<td class='input2'>
					<input type=text name="ClaimNo" class="common">
				</td>
			</tr>
			<tr>
				<td class='common' colspan=2>
					<input type=submit class='button' value="<s:text name='button.next.value' />">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html>