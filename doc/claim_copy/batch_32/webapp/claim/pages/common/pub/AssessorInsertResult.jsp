<%--
****************************************************************************
* DESC       ：公估师评估保存成功界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-22
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/meta_js.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<html:html locale="true">
<head>
<title><s:text name="title.pubBeforeEdit.assessSuccessfullyInterface" /></title>
<%--公估师评估保存成功界面--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
		<tr>
			<td class=formtitle colspan="2">
				<s:text name="prompt.system.title" />
			</td>
		</tr>
		<tr>
			<td class="common">
				<img src='/claim/images/bgClaimSuccess.gif'>
			</td>
			<td class="common">
				<span id="success"> <s:text name="pub.divisionSuccessfullySaved" />
					<%--公估师评估保存成功！ --%>
				</span><br>
			</td>
		</tr>
	</table>
</body>
</html:html>