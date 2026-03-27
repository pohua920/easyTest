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
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/prepayApprove.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="prepay.preCompensateThrough" />
				</td>
			</tr>
			<%--预赔核赔通过--%>
			<tr>
				<td class='title2'>
					<s:text name="db.prpLprepay.preCompensateNo" />
					：
				</td>
				<%--预赔计算书号--%>
				<td class='input2'>
					<input type=text name="prpLprepayPreCompensateNo" class="input">
					<input type=submit class='button' value="<s:text name='button.through.value' />">
				</td>
				<%--核赔通过--%>
				<input type="hidden" name="prpLprepayUnderWriteFlag" value="1">
			</tr>
		</table>
		<input type="hidden" name="editType" value="Approve">
	</form>
</body>
</html>
