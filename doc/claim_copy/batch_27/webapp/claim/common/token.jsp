<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
					您的任務處理收到此訊息是因為：<br>
					1.您本次請求處理的任務之前已經提交處理過，本次屬於重複提交；<br>
					2.您打開了多個視窗進行任務處理。<br>
					若要繼續處理本次任務，請返回并更新頁面後再行處理。
				</p>
			</td>
		</tr>
	</table>
</body>
</html>
