<html>
<head>
<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%> 
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
</head>
<script type="text/javascript">
function closePages(){
	if(parent.submitDlg!=undefined){
		parent.submitDlg.hide();
	}else {
		window.document.URL="about:blank";
	}
}
</script>
<body>
<br>
<br>

<table class=common align=center>
	<tr>
		<td width="567" height="38" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td width="567" height="81" align="center" valign="middle">
			<img src='${pageContext.request.contextPath}/common/images/misc/success.gif' />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">${message}</td>
	</tr>
	<tr>
		<td height="32" colspan="2" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" class="button_ty" value="<s:text name="button.close.value"/>" onClick="closePages();">
		</td><%--¹Ø ±Õ --%>
	</tr>
</table>
</body>
</html>

