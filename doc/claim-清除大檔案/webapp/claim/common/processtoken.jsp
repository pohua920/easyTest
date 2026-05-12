<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
	<title>系統提示</title>
	<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
</head>
<body>
<br>
<br>
	<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
		<tr>
			<td class=formtitle colspan="2">
				系統提示
			</td>
		</tr>
		<tr>
			<td class="common">
				<img src='<%=basePath%>/images/bgClaimFailure.gif'>
			</td>
			<td class="common">
				<p style="color: red;line-height: 20px;">
					<s:property value="exception.detailMessage"/>
				</p>
			</td>
		</tr>
	</table>
</body>
</html>
