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
			<h2>选择服务</h2>
			</td>
		</tr>
		<c:set var="index" value="0" />
		<s:iterator value="svrCodeList" status="stuts">
			<tr>
				<td>
				<div style="display: none">${index+1 }</div>
				<input type="radio" name="checkboxSelect"
					value="<s:property value='%{svrCodeList[#stuts.index].svrCode}'/>"> <a
					href="${ctx }/utiIAccount/prepareQueryUser.do?svrCode=<s:property value='%{svrCodeList[#stuts.index].svrCode}'/>"
					target="accountTreeRight">
					<s:property
					value='%{svrCodeList[#stuts.index].svrName}' /></a></td>
			</tr>
			<c:set var="index" value="${index+1 }" />
		</s:iterator>
	</table>
	</div>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>