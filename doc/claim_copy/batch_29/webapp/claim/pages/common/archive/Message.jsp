<%--
****************************************************************************
* DESC       ：操作成功提示页面
* AUTHOR     ：liuwei
* CREATEDATE ：2011-01-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs_base.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<!-- 滚动条样式定义 -->
<html:html locale="true">
<head>
<app:css />
</head>
<body>
	<form name="fm">
		<table align="center" class=common>
			<tr class=common>
				<td align="center" height=70px>
					<%
						String message = (String) request.getAttribute("message");
							if (message != null && "faile".equals(message)) {
					%>
					<img src="/claim/images/bgClaimFailure.gif" align="absmiddle">
					<%=request.getAttribute("content")%>
					<%
						} else {
					%>
					<img src="/claim/images/bgClaimSuccess.gif" align="absmiddle">
					<%=request.getAttribute("content")%>
					<%
						}
					%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
