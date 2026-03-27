<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title></title>

</head>
<body>
<s:fielderror />
<div id="container">
<div id="calContainer"
	style="position:absolute;display:none;z-index:999;left:33%;"></div>
<div id="case_detail" class="query">
<h1>
<p align="center"><s:text name="user.manage" /></p>
</h1>
<s:form action="updateUser" method="post" namespace="/user" validate="true">
	<s:hidden name="user.id" value="%{user.id}" />
	<table class="fix_table" border="0">
		<tr>
			<td class="bgc_tt short"><s:text name="user.username" /></td>
			<td class="long"><s:textfield name="user.userName"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.monicker" /></td>
			<td class="long"><s:textfield name="user.monicker"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.password" /></td>
			<td class="long"><s:password showPassword="true"
				name="user.password" cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.email" /></td>
			<td class="long"><s:textfield name="user.email"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.question" /></td>
			<td class="long"><s:textfield name="user.question"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.answer" /></td>
			<td class="long"><s:textfield name="user.answer"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.qq" /></td>
			<td class="long"><s:textfield name="user.qq"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.score" /></td>
			<td class="long"><s:textfield name="user.score"
				cssClass='input_w w_15' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><s:text name="user.regDate" /></td>
			<td class="long"><s:textfield name="user.regDate"
				cssClass='input_w w_15' /></td>
		</tr>

		<tr>
			<td class="bgc_tt short"><s:text name="user.logintime" /></td>
			<td class="long"><s:textfield name="user.loginTime"
				cssClass='input_w w_15' /></td>
		</tr>

		<tr>
			<td class="bgc_tt short"><s:text name="user.isdeleted" /></td>
			<td class="long"><s:textfield name="user.isdeleted"
				cssClass='input_w w_15' /></td>
		</tr>

		<tr>
			<td class="bgc_tt short"><s:text name="user.role" /></td>
			<td class="long"><s:textfield name="role"
				cssClass='input_w w_15' /></td>
		</tr>

	</table>
</s:form></div>
<div id="roleContainer" style="position:absolute;"></div>
</div>
</body>
</html>
