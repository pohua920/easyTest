<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title><s:text name="user.manage" /></title>
<link href="${ctx}/style/query.css" type="text/css" rel="stylesheet">
</head>
<body id="all_title"> 
<div id="container">

<h1>
<p align="center"><s:text name="user.manage" /></p>
</h1>
<s:form action="query" method="post" namespace="/user" validate="true"
	onsubmit="return submitQuery(this)">
	<table class="fix_table" border="0">
		<tr>
			<td class="bgc_tt short"><s:text name="user.username" /></td>
			<td class="long"><s:label name="user.userName"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.monicker" /></td>
			<td class="long"><s:label name="user.monicker"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.email" /></td>
			<td class="long"><s:label name="user.email"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.qq" /></td>
			<td class="long"><s:label name="user.qq" cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.question" /></td>
			<td class="long"><s:label name="user.question"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.answer" /></td>
			<td class="long"><s:label name="user.answer"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.regtime" /></td>
			<td class="long"><s:label name="user.regTime"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.logintime" /></td>
			<td class="long"><s:label name="user.loginTime"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.score" /></td>
			<td class="long"><s:label name="user.userName"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.isdeleted" /></td>
			<td class="long"><s:label name="user.monicker"
				cssClass='view_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.role" /></td>
			<td class="long"><s:label name="user.utiRole.id"
				cssClass='view_w w_15' theme="simple" />-<s:label name="user.utiRole.roleName"
				cssClass='view_w w_15' theme="simple"/></td>
		</tr>
	</table>
</s:form></div>
</body>
</html>
