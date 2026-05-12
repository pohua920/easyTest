<%--
****************************************************************************
* DESC       ：公估师新增保存成功界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<title><s:text name="query.publicAddSaveSuccess" /></title>
<%--公估师新增保存成功界面 --%>
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
				<span id="success"> <s:text name="query.publicSaveSuccess" /> <%--公估师保存成功 --%>： <input type=text name="ComCode" class="readonly" readonly style="width: 120px"
						value="${prpLInsuranceSurveyor.id.comCode}">
				</span><br>
			</td>
		</tr>
	</table>
</body>
</html>