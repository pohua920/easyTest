<%--
****************************************************************************
* DESC       ：查勘处理页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-08-03
* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%><head>
<script language="JavaScript">
<!--
	javascript: window.history.forward(1);
//-->
</script>
<html:html locale="true">
<!--对title处理-->
<title><s:text name="title.checkBeforeEdit.surveyDeal" /></title>
<%-- 查勘处理 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/check/js/DAACheckEdit.js"></script>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</head>
<body class=interface onload="initPage();initSet1();">
	<form name=fm action="/claim/checkScheduleSave.do" method="post" onsubmit="return validateForm(this);">
		<%-- 1.查勘主信息头信息 --%>
		<%@include file="/DAA/check/DAACheckScheduleMainEdit.jsp"%>
		<%-- 2.查勘标的内容 --%>
		<%@include file="/DAA/check/DAACheckScheduleItemEdit.jsp"%>
		<%-- 3.查勘提示信息 --%>
		<%-- // @include file="/DAA/claim/DAAClaimFeeEdit.jsp" --%>
		<%-- 查勘提交通用按钮 --%>
		<table cellpadding="0" cellspacing="10" width="80%" style="display:">
			<%--
      --%>
			<tr>
				<td class="button" style="width: 50%" align="center">
					<!--查勘保存按钮-->
					<input type=submit name=buttonSave class='button' value="<s:text name='button.save.value' />">
				</td>
				<td class="button" style="width: 50%" align="center">
					<!--取消按钮-->
					<input type="button" name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
