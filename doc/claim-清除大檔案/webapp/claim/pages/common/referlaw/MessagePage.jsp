<%--
 *************************************************************************
 * 程序名称: MessagePage.jsp
 * 程序功能: 信息反馈页面
 * 最近更新人: 
 * 最近更新日期: 
 ****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="jstl/c" prefix="c"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction"%>
<%
	String message = (String) request.getAttribute("message");
%>
<html>
<head>
<title><s:text name="title.guarantee.operateSuccess" /></title>
<%--操作成功--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="interface">
	<form name="fm" action="" method="post">
		<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
			<tr>
				<td class=formtitle colspan="2">
					<s:text name="prompt.system.title" />
				</td>
			</tr>
			<tr>
				<td class="common">
					<img src='/claim/images/bgClaimSuccess.gif'>
				</td>
				<td class="common">
					<span id="success"><c:out value="${message}" /> </span><br>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
