<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<body>
	<div id="div"
		style="MARGIN: 2pt; width: 100%; height: 100%;">
	<table class="fix_table">
		<tr>
			<td align="center">
			<h2>选择用户类型</h2>
			</td>
		</tr>
		
		<c:set var="index" value="0" />
		<c:forEach var="userType" items="${userTypeList}">
			<tr>
				<td>
				<div style="display: none">${index+1 }</div>
				<a href="${ctx}/utiIUser/preQueryUserStaticByUserType.do?userType=${userType.id.codeCode}" target="userTypeRight">${userType.codeCName}</a></td>
			</tr>
			<c:set var="index" value="${index+1 }" />
		</c:forEach>
	</table>
	</div>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>