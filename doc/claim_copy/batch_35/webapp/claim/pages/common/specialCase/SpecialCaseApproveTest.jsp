<!--
****************************************************************************
* DESC       ：特殊赔案通过功能
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<!-- 公用函数 -->
<script src="${ctx}/common/js/Common.js"></script>
<script>
	function onSub1() {
		fm.action = "${ctx}/compensateApprove.do";
		fm.submit();
	}

	function onSub2() {
		fm.action = "${ctx}/prepayApprove.do";
		fm.submit();
	}
</script>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/prepayApprove.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="specialCase.CompensationThrough" />
				</td>
			</tr>
			<%--预赔核赔通过--%>
			<tr>
				<td class='title'>
					<s:text name="specialCase.Prepaid" />
					:
				</td>
				<%--预付/通融--%>
				<td class='input'>
					<input type=text name="prpLcompensateCompensateNo" class="query">
					<input type=button class='button' value="<s:text name='button.NuclearThrough.value'/>" onClick="onSub1();">
				</td>
				<%-- 核赔通过 --%>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="check.advance" />
					:
				</td>
				<%-- 预赔 --%>
				<td class='input'>
					<input type=text name="prpLprepayPreCompensateNo" class="query">
					<input type=button class='button' value="<s:text name='button.NuclearThrough.value'/>" onClick="onSub2();">
				</td>
				<%-- 核赔通过 --%>
			</tr>
		</table>
		<input type="hidden" name="prpLcompensateUnderWriteFlag" value="1">
		<input type="hidden" name="prpLprepayUnderWriteFlag" value="1">
		<input type="hidden" name="editType" value="Approve">
	</form>
</body>
</html>
