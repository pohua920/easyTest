<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
</head>
<script type="text/javascript">
//parent.window.opener.location.reload();
</script>
<body>
<table class=common align=center>
	<br/>
	<tr>
		<td align="center"><img
			src='${pageContext.request.contextPath}/pages/image/success.gif' /></td>
		<td class="common"> <s:property value="%{businessNo}"/>服务添加错误<br></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><input type="button" class="button_ty" value="确定" onclick="javascript:window.location.href='${ctx}/utiISvr/selectPost.do'"></td>
	</tr>
</table>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
</script>