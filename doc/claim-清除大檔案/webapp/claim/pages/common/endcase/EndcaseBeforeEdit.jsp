<!--
****************************************************************************
* DESC       ：录入结案前输入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.endcase.endCase" /></title>
<!-- 录入结案 -->
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@include file="/common/meta_js.jsp"%>
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx }/endcase/endcaseBeforeEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="endcase.inputClaimNumberEnd" />
				</td>
			</tr>
			<!-- 输入赔案号（结案登记） -->
			<tr>
				<td class='title2' align="center">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class='input2'>
					<input type=text name="ClaimNo" class="common">
				</td>
			</tr>
			<tr>
				<td class='button' colspan=2 align="center">
					<input type=submit class='button' class="button" value="<s:text name="button.next.value" />">
					<!-- 下一步 -->
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html>