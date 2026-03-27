<%--
 *************************************************************************
 * 程序名称: MessagePage.jsp
 * 程序功能: 信息反馈页面
 * 最近更新人: 周咸立
 * 备注: 参数Picture:"S"表示成功,"F"表示失败,"C"表示一般信息;
 *       参数Content:内容。
 * 最近更新日期: 2001-11-29
 ****************************************************************************
--%>
<html>
<head>
<title><s:text name="title.pubBeforeEdit.infoFeedback" /></title>
<%--信息反馈--%>
<%--页面样式--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%
	String SUCCESS = "S"; //成功
	String FAILURE = "F"; //失败
	String COMMON = "C"; //一般信息

	String Picture = request.getParameter("Picture");
	String Content = request.getParameter("Content");
	String strPicture = "";
%>
</head>
<body class="interface">
	<table class=common align=center>
		<tr>
			<td class=formtitle colspan="2">
				<s:text name="prompt.system.title" />
			</td>
			<%--系统提示:--%>
		</tr>
		<%
			if (Picture == null)
				Picture = COMMON;

			if (Picture.equalsIgnoreCase(SUCCESS))
				strPicture = "bgClaimSuccess.gif";
			else if (Picture.equalsIgnoreCase(FAILURE))
				strPicture = "bgClaimFailure.gif";
			else
				strPicture = "common.gif";
		%>
		<tr>
			<td align=right>
				<img src='/claim/images/<%=strPicture%>'>
			</td>
			<td class="common">
				<%=Content%>
			</td>
		</tr>
	</table>
</body>
</html>