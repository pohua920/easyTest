<!--
****************************************************************************
* DESC       ：已完成案件查询输入界面
* AUTHOR     ： weishixin
* CREATEDATE ： 2004-04-12
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
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/registFinishQueryEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.registBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
					:
				</td>
				<td class='input'>
					<input type=text name="prpLregistRegistNo" class="input">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					:
				</td>
				<td class='input'>
					<input type=text name="prpLregistPolicyNo" class="input">
				</td>
			</tr>
			<tr>
				<td class='button'>
					<input type=submit class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="EDIT">
	</form>
</body>
</html>