
<!--
****************************************************************************
* DESC       ： 追偿计算书打印
* AUTHOR     ： 曹志刚
* CREATEDATE ： 2009-12-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="replevy.claimPrintInputNumber" /></title>
<%--理赔打印前输入单证号 --%>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/common/replevy/js/replevyEdit.js"></script>
</head>
<body>
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle" id="TitleName"></td>
			</tr>
			<tr>
				<td class='title2'>
					<s:text name="compensate.computeBookNum" />
					<%--计算书号 --%>
				</td>
				<td class='input2'>
					<input class="common" type='text' name='compensateNo' maxlength='25'>
				</td>
			</tr>
			<tr>
				<td class="button" align="center" colspan="2">
					<input type=button value="<s:text name="button.next.value" />" class='button' onclick="print();">
				</td>
				<%--下一步 --%>
			</tr>
		</table>
	</form>
</body>
</html>
