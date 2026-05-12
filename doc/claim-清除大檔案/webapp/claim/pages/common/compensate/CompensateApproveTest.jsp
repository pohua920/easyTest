<%--
****************************************************************************
* DESC       ：理算查询条件输入页面
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
	<form name="fm" action="/claim/compensateApprove.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="compensate.adjustHepeiPass" />
				</td>
			</tr>
			<!-- 理算核赔通过 -->
			<tr>
				<td class='title2'>
					<s:text name="db.prpLcfee.compensateNo" />:
				</td>
				<!-- 赔款计算书号 -->
				<td class='input2'>
					<input type=text name="prpLcompensateCompensateNo" class="query">
					<input type="hidden" name="prpLcompensateUnderWriteFlag" value="1">
					<input type="submit" class="bigbutton" value="<s:text name="button.payHepeiPass.value"/>">
				</td>
				<!-- 赔核赔通过 -->
			</tr>
		</table>
		<input type="hidden" name="editType" value="EDIT">
	</form>
</body>
</html:html>