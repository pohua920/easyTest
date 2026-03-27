<%@ page contentType="text/html; charset=GBK"%>
<%@ page isErrorPage="true"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td height="20" align="center" class="menu2">
				<img src="${ctx }/images/bgClaimFailure.gif" border="0" align="absmiddle">&nbsp;&nbsp;<font size=4><b><s:text name="prompt.undwrt.Sorry" /></b></font>
			</td>
			<%-- 抱歉,您无权审核通过！ --%>
		</tr>
		<tr>
			<td align="left" class="menu">
				<font size=3><b><s:text name="undwrt.Reasons" />：</b></font>
			</td>
			<%-- 原因如下 --%>
		</tr>
		<tr id="trContent">
			<td align=left colspan="2">
				<br> &nbsp;&nbsp;&nbsp;&nbsp;<font size=3>${userException.errorModule }</font><br> &nbsp;&nbsp;&nbsp;&nbsp;${userException.errorMessage}
				<s:text name="prompt.undwrt.message" />
				<%-- 您可以点击返回後，下发修改或者提交上级。 --%>
			</td>
		</tr>
		<tr>
			<td align=center colspan="2">
				<br>
				<br>
				<br>
				<br>
				<input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="history.back(0)">
				<%-- 返回 --%>
			</td>
		</tr>
	</table>
	<c:remove var="userException" scope="session" />
</body>
</html>