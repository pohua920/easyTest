<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>管理员权限范围</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
</head>
<body>
<s:form name="fm">
	<div id="crash_menu"><h2 align="center">管理员权限范围</h2></div>
	<table class="fix_table" width="100%">
		<tr>
			<td class="bgc_tt short">员工代码</td>
			<td class="long"><s:textfield name="saaUser.userCode" readonly="true" cssClass="input_w w_30"/></td>
			<td class="bgc_tt short">员工姓名</td>
			<td class="long"><s:textfield name="saaUser.userName" readonly="true" cssClass="input_w w_30"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">归属机构</td>
			<td class="long"><s:textfield name="saaUser.comCode" readonly="true" cssClass="input_w w_30"/></td>
			<td class="bgc_tt short">有效标志</td>
			<td class="long"><s:select name="saaUser.validStatus" list="#@java.util.HashMap@{'1':'有效','0':'无效'}" cssClass="input_w w_30"/></td>
			
		</tr>
	</table>
</s:form>
</body>
</html>

