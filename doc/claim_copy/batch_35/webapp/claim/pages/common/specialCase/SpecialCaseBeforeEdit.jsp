<!--
****************************************************************************
* DESC       ：录入报案前输入保单号页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2004-02-25
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
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx }/registBeforeEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="title.registBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title2'>
					<s:text name="db.prpLregist.policyNo" />
					:
				</td>
				<td class='input2'>
					<input type=text name="prpCmainPolicyNo" class="common">
				</td>
			</tr>
			<tr>
				<td class='button'>
					<input type=submit class='button' value="<s:text name='button.next.value'/>">
					<%-- 下一步 --%>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html>