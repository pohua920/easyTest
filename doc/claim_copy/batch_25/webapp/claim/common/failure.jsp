<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	</head>
<body>
<br>
<br>
		<form method="post" name="fm" id="fmAction">
			<input type="hidden" id="operate" value="${operate}" />
			<input type="hidden" id="oldUrl" value="${oldUrl}" />
			<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
				<tr>
					<td class=formtitle colspan="2">
						<s:text name="prompt.system.title"/><%--系统提示 --%>
					</td>
				</tr>
				<tr>
					<td class="common">
						<img src="/claim/images/bgClaimFailure.gif" />
					</td>
					<td class="common" style="color:red;">
						<span ><s:property value="exception.errorModule"/></span><br>
						<span ><s:property escape="false" value="exception.errorMessage"/><!-- escape="false" 支持<br>换行 --></span><br>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
